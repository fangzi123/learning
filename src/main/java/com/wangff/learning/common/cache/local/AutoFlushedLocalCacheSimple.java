package com.wangff.learning.common.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个cache会定期全部清理，所以真实的使用场景中，要注意对后端存储访问的波峰问题。
 * <p>
 * 自动定时清空的本地缓存，一个简单实现。<br/>
 *
 * <strong>注意: <br/>
 * 1. 请一定在充分了解这个缓存实现的情况下来使用这个缓存；<br/>
 * 2. 非线程安全，使用时一定要注意多线程下cache的填充问题，最好不要增量往list中添加，很容易造成重复添加；<br/>
 * 3. 默认使用ConcurrentHashMap实现，请一定设置好map的key上限；<br/>
 * 4. 可以通过重写mapType()选择使用WeakHashMap，此时缓存数据随时可能会被清空，适合作为高访问量下的临时缓存；<br/>
 * 5. 定时清空缓存，而不是重新加载；<br/>
 * 6. 适合putIfAbsent的使用场景，没有初始化操作。<br/>
 * </strong> <br/>
 * <br/>
 * 提示：如果你不想单独定义一个类来实现自己的缓存，完全可以这么用： AutoFlushedLocalCacheSimple myCache = new AutoFlushedLocalCacheSimple<K,V>(){...}
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
public abstract class AutoFlushedLocalCacheSimple<K, V>
    extends AbstractAutoRefreshedLocalCache<K, V>
    implements LocalCache<K, V> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void refreshLocalCache() {
    clearAllLocal();
  }

  /**
   * 清除本地所有缓存的数据
   */
  protected final void clearAllLocal() {
    synchronized (this) {
      getMap().clear();
      logger.info(getName() + ": clearAllLocal.");
    }
  }

}
