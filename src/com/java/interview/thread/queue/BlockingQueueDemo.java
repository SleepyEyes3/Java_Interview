package com.java.interview.thread.queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue 中的 ArrayBlockingQueue LinkedBlockingQueue SynchronousQueue的基本用法
 *
 * add / remove / element 报错
 * offer / poll / peek 显示布尔值
 * put / take
 * offer(time) 超出指定时间后会显示布尔值并退出
 *
 * SynchronousQueue 生产一个 -> 消费一个 ，Queue中的元素不消耗掉，就会将线程阻塞
 * */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        SychronousQueueTest();
    }

    private static void SychronousQueueTest() {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronousQueue.put("j");
                System.out.println("put j");

                synchronousQueue.put("x");
                System.out.println("put x");

                synchronousQueue.put("y");
                System.out.println("put y");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            try {
//                try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronousQueue.take();
                System.out.println("take j");

                try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronousQueue.take();
                System.out.println("take x");

                try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronousQueue.take();
                System.out.println("take y");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
