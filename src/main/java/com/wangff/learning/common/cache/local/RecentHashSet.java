package com.wangff.learning.common.cache.local;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * "最近"的HashSet：只保留最近一段时间的key在Set中，历史过期的数据会自动清理掉【注意：清理的时机由add操作触发，不能自动触发，这个一定要注意】；
 * <p>
 * 【注意，这个Set不能保证全局唯一性】
 * <p>
 * 有两个关键时间字段：1. 时间粒度 timeUnitSeconds； 2. 有效期 validPeriodSeconds
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/7/2
 */
public class RecentHashSet<E> implements RecentCollection {

  // timeKey -> HashSet<E>
  private final Map<Long, HashSet<E>> localMap = new ConcurrentHashMap<>();
  private final long timeUnitSeconds;
  private final long validPeriodSeconds;

  /**
   * @param timeUnitSeconds    时间粒度，比如粒度为600秒，即每隔600秒，生成一个HashSet，放入localMap
   * @param validPeriodSeconds 有效期，保留最近多少秒的数据
   */
  public RecentHashSet(long timeUnitSeconds, long validPeriodSeconds) {
    this.timeUnitSeconds = timeUnitSeconds < 1 ? 1 : timeUnitSeconds;
    this.validPeriodSeconds = validPeriodSeconds < 1 ? 1 : validPeriodSeconds;
  }


  /**
   * 添加一个元素
   */
  public void add(E e) {
    long timeKey = System.currentTimeMillis() / (timeUnitSeconds * 1000L);
    HashSet<E> hashSet = computeIfAbsent(localMap, timeKey);
    if (hashSet.isEmpty()) {
      tryToClearLocalMap(timeKey);
    }
    hashSet.add(e);
  }

  /**
   * 是否包含元素e
   */
  public boolean contains(E e) {
    for (Map.Entry<Long, HashSet<E>> entry : localMap.entrySet()) {
      if (entry.getValue().contains(e)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 删除元素e
   */
  public void remove(E e) {
    for (Map.Entry<Long, HashSet<E>> entry : localMap.entrySet()) {
      entry.getValue().remove(e);
    }
  }

  /**
   * 大小
   */
  public int size() {
    int size = 0;
    for (Map.Entry<Long, HashSet<E>> entry : localMap.entrySet()) {
      size += entry.getValue().size();
    }
    return size;
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
    Iterator<Map.Entry<Long, HashSet<E>>> iterator = localMap.entrySet().iterator();
    long validTimeKeyBase = timeKey - validPeriodSeconds / timeUnitSeconds;
    while (iterator.hasNext()) {
      Map.Entry<Long, HashSet<E>> entry = iterator.next();
      if (entry.getKey() < validTimeKeyBase) {
        iterator.remove();
      }
    }
  }

  /**
   * 线程安全的computeIfAbsent，得到当前key对应的HashSet
   */
  private HashSet<E> computeIfAbsent(Map<Long, HashSet<E>> map, Long key) {
    HashSet<E> innerHashSet = map.get(key);
    if (innerHashSet == null) {
      synchronized (this) {
        innerHashSet = map.computeIfAbsent(key, k -> new HashSet<>());
      }
    }
    return innerHashSet;
  }

}
