package com.wangff.learning.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangff
 * @date 2019/7/25 10:06
 * 实现类似计数器功能
 * 当调用   countDownLatch.await();时就会阻塞当前线程
 * 其他线程执行  countDownLatch.countDown(); 直到计数器为0后，当前线程才回复执行
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(1111111111);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(222222222);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        countDownLatch.await();
        System.out.println("主线程------------");
    }
}
