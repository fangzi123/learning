package com.wangff.learning.common.cache.local;

import com.sensorsdata.channel.common.util.ExecutorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * “自动刷新的本地缓存“实现的公共方法和逻辑；实现基本的get/set缓存逻辑；
 * <br/>
 * “自动刷新”可以是： 定时自定清除、定时自动加载、定时自动淘汰等实现
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
public abstract class AbstractAutoRefreshedLocalCache<K, V> implements LocalCache<K, V> {

  private static final int DEFAULT_POOL_SIZE = 1;
  private static final long DEFAULT_MAX_KEY_NUM = 1000000L;// 默认最多缓存1000000个key（近似认为没有限制）
  private static final int MAP_DEFAULT_CAPACITY = 1 << 15;
  private static final long DEFAULT_AUTO_RELOAD_TIME = 60 * 30 * 1000L;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 定时刷新缓存的定时器是否启动
   */
  private volatile boolean isTimerStarted = false;


  public enum MapType {
    WEAK_HASH_MAP,
    CONCURRENT_HASH_MAP
  }


  /**
   * 定时器
   */
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(DEFAULT_POOL_SIZE);

  /**
   * 缓存记录的数量，记录的是map的size，仅供参考
   */
  public int size() {
    return getMap().size();
  }

  // ---------------------------缓存数据存储结构-------------------------------------//

  private Map<K, V> map = newCachedMap();

  @Override
  public V get(final K key) {
    if (key == null) {
      return null;
    }
    checkAndStartTimer();
    return getMap().get(key);
  }

  @Override
  public void set(final K key, final V value) {
    if (key == null || value == null) {
      return;
    }
    checkAndStartTimer();
    if (getMap().size() < maxKeyNum()) {
      getMap().put(key, value);
    } else {
      throw new RuntimeException("local cache " + getName() + " reach maxKeyNum " + maxKeyNum());
    }
  }

  @Override
  public int del(final K key) {
    if (key == null) {
      return 0;
    }
    V v = getMap().remove(key);
    if (v == null) {
      return 0;
    }
    return 1;
  }

  /**
   * 福利：暴露出内部缓存数据的Map，但是不可更改
   */
  public Map<K, V> getUnmodifiableStatMap() {
    checkAndStartTimer();
    return Collections.unmodifiableMap(getMap());
  }

  /**
   * 刷新本地缓存，具体刷新操作由子类定义，例如可以全部清除、可以重新加载指定数据，等等
   */
  public abstract void refreshLocalCache();

  /**
   * 继承者的名字，用来打log
   */
  protected String getName() {
    return this.getClass().getSimpleName();
  }

  /**
   * 定时刷新本地缓存的间隔(单位:毫秒),子类可以修改
   */
  protected long getPeriodsForRefreshing() {
    return DEFAULT_AUTO_RELOAD_TIME;
  }

  /**
   * 返回内部缓存数据的Map，根据mapType()返回的不同，缓存使用不同类型的Map来作为缓存的数据结构；<br/>
   * 当然，子类可以通过重写这个方法来自己定制缓存用的Map(默认配置了ConcurrentHashMap和WeakHashMap两种Map使用)
   */
  protected Map<K, V> getMap() {
    return map;
  }

  /**
   * 允许子类自己设置缓存用的Map，这主要用在CopyOnWrite方式重新load数据时使用
   */
  protected void setMap(Map<K, V> map) {
    this.map = map;
  }

  /**
   * 根据mapType()的配置，新建一个缓存用的Map
   */
  protected final Map<K, V> newCachedMap() {
    if (MapType.WEAK_HASH_MAP.equals(mapType())) {
      return new WeakHashMap<>(MAP_DEFAULT_CAPACITY);
    } else if (MapType.CONCURRENT_HASH_MAP.equals(mapType())) {
      return new ConcurrentHashMap<>(MAP_DEFAULT_CAPACITY);
    } else {
      throw new RuntimeException("Error MapType: " + mapType());
    }
  }

  /**
   * 缓存map的大小超过这个值，就不能再往里放东西了，会直接抛异常！！<br/>
   * <br/>
   * 因为这个本地内存可能支持不自动回收的方式(如ConcurrentHashMap)，所以强制子类声明缓存map中的最大的key的数量，一定要估计好啊！
   */
  protected long maxKeyNum() {
    return DEFAULT_MAX_KEY_NUM;
  }

  /**
   * 缓存数据的数据结构（map）的类型，默认是ConcurrentHashMap
   */
  protected MapType mapType() {
    return MapType.CONCURRENT_HASH_MAP;
  }

  /**
   * 检查并开启定时刷新本地缓存的定时器
   */
  protected void checkAndStartTimer() {
    if (!isTimerStarted) {
      synchronized (this) {
        if (!isTimerStarted) {
          // 定时器开启之前的工作
          beforeCheckAndStartTimer();

          final long periodMS = getPeriodsForRefreshing();
          scheduler.scheduleWithFixedDelay(() -> {

            try {
              refreshLocalCache();
            } catch (Exception e) {
              logger.error("refreshLocalCache fail: ", e);
            }
          }, periodMS, periodMS, TimeUnit.MILLISECONDS);

          isTimerStarted = true;
        }
      }
    }
  }

  /**
   * 开启自动刷新定时器之前要做的工作，比如可能需要初始化什么的；默认什么也不做；不用对它做锁限制，它本身受checkAndStartTimer的双检锁的限制
   */
  protected void beforeCheckAndStartTimer() {

  }

  /**
   * 关闭当前自动刷新的定时任务，并置isTimerStarted为false（这样定时任务还会再次启动）；这个方法很少使用，例如，销毁当前实例的情况下调用
   */
  protected void stopRefreshLocalCacheScheduler() {
    try {
      ExecutorUtil.stopExecutorService(scheduler, this.getClass().getName());
    } catch (Exception e) {
      logger.error("stopFlushDbScheduler fail: ", e);
    }
  }

}
