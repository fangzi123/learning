package com.wangff.learning.common.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每条数据自动过期的缓存设计，过期最小单位是缓存泛型中的K。<br/>
 * 注意：这里的“过期”是指在set数据时设定的过期时间，而不是“数据一段时间不使用就过期”的意思； <br/>
 * <br/>
 * <strong>注意: <br/>
 * 1. 请一定在充分了解这个缓存实现的情况下来使用这个缓存；<br/>
 * 2. 非线程安全，使用时一定要注意多线程下cache的填充问题，最好不要增量往list中添加，很容易造成重复添加；<br/>
 * 3. 默认使用ConcurrentHashMap实现，请一定设置好map的key上限；<br/>
 * 4. 可以通过重写mapType()选择使用WeakHashMap，此时缓存数据随时可能会被清空，适合作为高访问量下的临时缓存；<br/>
 * 5. 适合putIfAbsent的使用场景，没有初始化操作。<br/>
 * </strong> <br/>
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
public abstract class AutoExpiredLocalCache<K, V>
    extends AbstractAutoRefreshedLocalCache<K, V>
    implements LocalCache<K, V> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  // 过期时间管理Map，需要定期清理
  private final Map<K, Date> expiredDateMap = new ConcurrentHashMap<>();

  @Override
  public void refreshLocalCache() {
    // 这里就简单了，清理所有过期数据即可
    for (Entry<K, Date> entry : expiredDateMap.entrySet()) {
      if (entry.getValue().getTime() < System.currentTimeMillis()) {
        expiredDateMap.remove(entry.getKey());
        this.getMap().remove(entry.getKey());
      }
    }
    logger.info("AutoExpiredLocalCache auto refresh cache success, left cache size is: " + this.getMap().size());
  }

  @Override
  public V get(final K key) {
    Date expiredDate = expiredDateMap.get(key);
    if (expiredDate == null) {
      return null;
    }
    // 过期之后在这里删除
    if (expiredDate.getTime() < System.currentTimeMillis()) {
      expiredDateMap.remove(key);
      this.getMap().remove(key);
      return null;
    }

    return super.get(key);
  }

  @Override
  public void set(final K key, final V value) {
    long now = System.currentTimeMillis();
    long expiredTimePoint = now + expiredTime();
    expiredDateMap.put(key, new Date(expiredTimePoint));

    super.set(key, value);
  }

  /**
   * 每条数据的过期时间的，单位是毫秒；注意：这里的“过期”是指在set数据时设定的过期时间，而不是“数据一段时间不使用就过期”的意思
   */
  protected abstract long expiredTime();

}
