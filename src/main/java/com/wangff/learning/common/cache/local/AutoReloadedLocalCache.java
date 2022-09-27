package com.wangff.learning.common.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <strong>注意: <br/>
 * 0. 应该尽量减少不必要的缓存刷新（比如加入判断，如果下层数据没有变化，就不要刷新），因为缓存内容时间长了之后会进入年老代，缓存较大的情况，刷新多了之后，容易触发full GC;
 * 1. 请一定在充分了解这个缓存实现的情况下来使用这个缓存；<br/>
 * 2. 非线程安全，使用时一定要注意多线程下cache的填充问题，最好不要增量往list中添加，很容易造成重复添加；<br/>
 * 3. 默认使用ConcurrentHashMap实现，请一定设置好map的key上限；<br/>
 * 4. 可以通过重写mapType()选择使用WeakHashMap，此时缓存数据随时可能会被清空，适合作为高访问量下的临时缓存；<br/>
 * 5. 定时重新加载，而不是直接清空缓存；<br/>
 * 6. 适合一次load所有数据到内存的场景，有初始化操作；<br/>
 * 7. 一个优化：尽量保持旧对象不被替换，需要V或者V内包含的对象对象自己实现 equals && hashcode方法；使用时，需要重写reuseOldInstance方法。<br/>
 * </strong> <br/>
 * 自动定时重载数据的长效的本地缓存，一个简单实现。<br/>
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
public abstract class AutoReloadedLocalCache<K, V>
    extends AbstractAutoRefreshedLocalCache<K, V>
    implements LocalCache<K, V> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 缓存支持初始化，这个变量标示缓存是否已经初始化
   */
  private volatile boolean isCacheInit = false;

  /**
   * 异步执行器
   */
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Override
  protected void beforeCheckAndStartTimer() {
    // 第一次初始化缓存
    if (!isCacheInit) {
      synchronized (this) {
        if (!isCacheInit) {
          initCacheDefault(this.getMap());
          isCacheInit = true;
        }
      }
    }
  }

  @Override
  public void refreshLocalCache() {
    reloadDataToCache();
  }

  /**
   * 初始化cache，即将要缓存的内容填充到参数所指的cacheMap中，注意，这里不要自己获取缓存用的Map;<br/>
   * <br/>
   * 这个初始化动作默认是同步的，可以通过重写initCacheUnSync()来指定是否异步操作，异步操作注意，刚开始使用(初始化完成之前)可能数据不完整
   * <br/>
   * 应该尽量减少不必要的缓存刷新，以减少触发full GC的概率
   */
  protected abstract boolean initCache(Map<K, V> cacheMap);

  /**
   * 是否异步初始化cache；默认是同步的，因为很多使用者强依赖缓存，而不是putIfAbsent，异步初始化，在初始化完成前查询，会造成查询失败
   */
  protected boolean initCacheUnSync() {
    return false;
  }

  /**
   * 重新加载cache中的数据，默认的，会根据initCache来重新加载数据；<br/>
   * 1. 如果想要自定义重载时加载的数据(比如重设数据范围，或者加入一些诸如没有新数据就不重载的限制条件)，则重写此方法即可；<br/>
   * 2. 重新载入数据，默认使用initCache，即将要缓存的内容重新填充到参数所指的map中；<br/>
   * <strong>3. 注意：如果数据不是特别大，这里最好使用copyOnWrite的方式，防止出现不一致状态</strong>
   */
  protected void reloadDataToCache() {
    Map<K, V> reloadMap = newCachedMap();
    if (!this.initCache(reloadMap)) {
      logger.info(getName() + ": reloadDataToCache.fail: initCache return false!");
      return;
    }

    this.setMap(reloadMap);

    // !!!注意，这里不要再做画蛇添足的事了，做copyOnWrite替换时，旧的map就自动让gc收拾就可以了，这里再多此一举的clear一下，首先clear时，旧map肯定有很多线程在用，
    // 首先在读的过程中clear本身会造成错误，其次ConcurrentHashMap的clear会加锁，自然就block住很多线程了，导致并发性能超差!!!
  }

  /**
   * 初始化cache的默认入口，以同步方式或异步方式调用initCache
   */
  private void initCacheDefault(final Map<K, V> cacheMap) {
    if (initCacheUnSync()) {
      executor.execute(() -> {
        initCache(cacheMap);
        logger.info(getName() + ": initCache.unSync.finished.cacheMap size:" + cacheMap.size());
      });
    } else {
      initCache(cacheMap);
      logger.info(getName() + ": initCache.sync.finished.cacheMap size:" + cacheMap.size());
    }
  }

}
