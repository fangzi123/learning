package com.wangff.learning.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Semaphore
 * 可以控制同时访问的线程个数
 * 通过 acquire获取一个许可 如果没有就等待
 * 而 release释放一个许可
 */
@Slf4j
public class ConcurrentTest2 {
    private static int threadCount = 200;
    private static int clientTotal = 5000;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
           log.info("i=={}",i);
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count = {}", count.get());

    }

    private static void add() {
        log.info("count == {}", count.get());
        count.incrementAndGet();
    }

}