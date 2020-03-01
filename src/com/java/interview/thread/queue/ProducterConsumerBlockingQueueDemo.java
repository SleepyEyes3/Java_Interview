package com.java.interview.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用 BlockingQueue AtomicInteger volatile 写一个生产者-消费者模型
 *
 * 在停止的同时，为什么总是先“生产停止” 后 “消费停止”呢？
 *      因为 ret = blockingQueue.offer(data,2L, TimeUnit.SECONDS); 这边使消费线程停止了2秒
 *      要注意offer/poll设置的是超时的时间，并不是操作的间隔时间。
 * */

class Market{
    private boolean FLAG = true;
    AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public Market(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println("传入的BlockingQueue的类型是： " + blockingQueue.getClass().getName());
    }

    public void Product(){
        String data = null;
        boolean ret;
        while(FLAG){
            data = atomicInteger.incrementAndGet() + ""; // getAndIncrement在这里的效果是不是一样的；开始值不一样
            try {
                ret = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
                if(ret == true){
                    System.out.println("生产 " + data + " 成功！");
                }else{
                    System.out.println("生产 " + data + " 失败！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
        }
        System.out.println("生产停止！");
    }

    public void Consume(){
        String ret;
        while(FLAG){
            try {
                ret = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if(ret == null || ret.equalsIgnoreCase("")){
                    System.out.println("超过2秒没取到值，消费停止!");
                    FLAG = false;
                }else{
                    System.out.println("消费 " + ret + " 成功！");
                    System.out.println();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void Stop(){
        try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("5秒钟后停止生产！");
        this.FLAG = false;
    }


}

public class ProducterConsumerBlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<>(10);
        Market market = new Market(strings);

        new Thread(() -> {
            market.Product();
        },"Product").start();

        new Thread(() -> {
            market.Consume();
        },"Consume").start();

        new Thread(() -> {
            market.Stop();
        },"Stop").start();
    }
}

