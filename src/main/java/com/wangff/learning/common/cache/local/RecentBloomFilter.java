package com.wangff.learning.common.cache.local;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 一个可滚动使用的布隆过滤器，内部维护两个布隆过滤器，当其中一个满了的时候自动切换到另一个,内存占用约是简单布隆过滤器的两倍,根据两个过滤器的内容共同去重
 *
 * @author zongxuqin@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/08/09 15:25
 */
@SuppressWarnings("ALL")
@Slf4j
public class RecentBloomFilter<T> {

  private final Funnel<? super T> funnel;
  private final long expectedInsertions;
  private final double fpp;
  private volatile FilterInstance instance;


  private class FilterInstance {
    private volatile BloomFilter<T> previous;
    public final BloomFilter<T> current;
    public final AtomicLong insertCnt;
    private final long switchCnt;

    FilterInstance(BloomFilter previous) {
      this.previous = previous;
      this.current = BloomFilter.create(funnel, expectedInsertions, fpp);
      this.insertCnt = new AtomicLong();
      this.switchCnt = expectedInsertions / 2;
    }

    public void put(T object) {
      if (previous != null && insertCnt.get() >= switchCnt) {
        previous = null;
      }
      current.put(object);
    }

    public boolean mightContain(T object) {
      BloomFilter innerPrevious = previous;
      return current.mightContain(object) || (innerPrevious != null && innerPrevious.mightContain(object));
    }
  }

  public RecentBloomFilter(Funnel<? super T> funnel, long expectedInsertions, double fpp) {
    this.funnel = funnel;
    this.expectedInsertions = expectedInsertions;
    this.fpp = fpp;
    this.instance = new FilterInstance(null);
  }


  // 如果进来的元素超过初始设置了，则新建一个FilterInstance,并将原来instance的current当做新instance的previous
  public void put(T object) {
    instance.put(object);
    if (instance.insertCnt.incrementAndGet() >= expectedInsertions) {
      synchronized (this) {
        if (instance.insertCnt.get() >= expectedInsertions) {
          instance = new FilterInstance(instance.current);
        }
      }
    }
  }

  public boolean mightContain(T object) {
    return instance.mightContain(object);
  }
}
