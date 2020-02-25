package com.java.interview.thread.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 这个 cyclicBarrier 和 countDownLatch 是反着的
 * */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6,() -> {
            System.out.println("骑兵连，冲锋！");
        });

        for(int i = 0; i < 6; i++){
            new Thread(() -> {
                System.out.println("骑兵 " + Thread.currentThread().getName() + " 号，到！");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
