package com.java.interview.thread;

import com.java.interview.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchDemo 线程倒计数
 *
 * 调用countDownLatch的countDown方法的线程 会使 countDownLatch 减一，直到为0时，才会继续下面的线程，否则一直会await
 *
 * */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i = 0; i < 6; i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 国灭亡！");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await(); // 除非 countDownLatch 的值减到 0 ，不然 当前线程会在这里阻塞
        System.out.println(Thread.currentThread().getName() + " 秦国统一天下！");
    }
}
