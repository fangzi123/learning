package com.wangff.learning.common.cache.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * "最近"的HashMap：只保留最近一段时间的key在Set中，历史过期的数据会自动清理掉；
 * <p>
 * 【注意，这个Map不能保证全局唯一性】
 * <p>
 * 有两个关键时间字段：1. 时间粒度 timeUnitSeconds； 2. 有效期 validPeriodSeconds
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/7/2
 */
public class RecentHashMap<K, V> implements RecentCollection {

  // timeKey -> HashMap<K, V>
  private final Map<Long, HashMap<K, V>> localMap = new ConcurrentHashMap<>();
  private final long timeUnitSeconds;
  private final long validPeriodSeconds;

  /**
   * @param timeUnitSeconds    时间粒度，比如粒度为600秒，即每隔600秒，生成一个HashSet，放入localMap
   * @param validPeriodSeconds 有效期，保留最近多少秒的数据
   */
  public RecentHashMap(long timeUnitSeconds, long validPeriodSeconds) {
    this.timeUnitSeconds = timeUnitSeconds < 1 ? 1 : timeUnitSeconds;
    this.validPeriodSeconds = validPeriodSeconds < 1 ? 1 : validPeriodSeconds;
  }


  /**
   * 查询
   */
  public V get(K k) {
    long maxTimeKey = 0;
    V result = null;
    for (Map.Entry<Long, HashMap<K, V>> entry : localMap.entrySet()) {
      // 不设定复杂的清理策略，查询的时候，损耗点效率，取最新的一个
      V v = entry.getValue().get(k);
      if (v != null && entry.getKey() > maxTimeKey) {
        result = v;
        maxTimeKey = entry.getKey();
      }
    }
    return result;
  }

  /**
   * 添加一个 k->v
   */
  public void put(K k, V v) {
    long timeKey = System.currentTimeMillis() / (timeUnitSeconds * 1000L);
    HashMap<K, V> hashMap = computeIfAbsent(localMap, timeKey);
    if (hashMap.isEmpty()) {
      tryToClearLocalMap(timeKey);
    }
    hashMap.put(k, v);
  }

  /**
   * 是否包含key k
   */
  public boolean containsKey(K k) {
    for (Map.Entry<Long, HashMap<K, V>> entry : localMap.entrySet()) {
      if (entry.getValue().containsKey(k)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 大小
   */
  public int size() {
    int size = 0;
    for (Map.Entry<Long, HashMap<K, V>> entry : localMap.entrySet()) {
      size += entry.getValue().size();
    }
    return size;
  }

  /**
   * 删除key k
   */
  public void remove(K k) {
    for (Map.Entry<Long, HashMap<K, V>> entry : localMap.entrySet()) {
      entry.getValue().remove(k);
    }
  }

  /**
   * 手工清理过期数据（默认由add操作触发清理，但是一些场景下，不会频繁调用add，可以使用这个方法手工清理过期数据）
   */
  public void clearExpiredData() {
    long timeKey = System.currentTimeMillis() / (timeUnitSeconds * 1000L);
    if (localMap.get(timeKey) == null) {
      tryToClearLocalMap(timeKey);
    }
  }

  /**
   * 清理localMap，删除过期数据
   */
  private void tryToClearLocalMap(long timeKey) {
    Iterator<Map.Entry<Long, HashMap<K, V>>> iterator = localMap.entrySet().iterator();
    long validTimeKeyBase = timeKey - validPeriodSeconds / timeUnitSeconds;
    while (iterator.hasNext()) {
      Map.Entry<Long, HashMap<K, V>> entry = iterator.next();
      if (entry.getKey() < validTimeKeyBase) {
        iterator.remove();
      }
    }
  }

  /**
   * 线程安全的computeIfAbsent，得到当前key对应的HashSet
   */
  private HashMap<K, V> computeIfAbsent(Map<Long, HashMap<K, V>> map, Long key) {
    HashMap<K, V> innerHashSet = map.get(key);
    if (innerHashSet == null) {
      synchronized (this) {
        innerHashSet = map.computeIfAbsent(key, k -> new HashMap<>());
      }
    }
    return innerHashSet;
  }

}
