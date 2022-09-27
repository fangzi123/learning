package com.wangff.learning.common.cache.local;

/**
 * 本地缓存
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
public interface LocalCache<K, T> {

  T get(K key);

  void set(K key, T value);

  int del(K key);

}
