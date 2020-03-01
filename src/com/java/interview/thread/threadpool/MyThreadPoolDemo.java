package com.java.interview.thread.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池是实现多线程的第四种方式
 *
 * 三种线程池的简单使用：分为运行一下三种线程池 看看啥效果
 *
 * */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        // 1. 线程池中的线程数固定    执行长期的任务，性能好很多
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        // 1. 线程池中的线程数为 1    一个任务一个任务执行的场景
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        // 1. 线程池中的线程数we N个  执行很多短期异步的小程序或者负载较轻的服务器
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                final int temp = i;
                threadPool2.execute(() -> {
                    try { TimeUnit.MILLISECONDS.sleep(200); }catch (InterruptedException e){ e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool2.shutdown();
        }


    }
}
