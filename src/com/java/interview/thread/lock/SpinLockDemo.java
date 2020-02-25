package com.java.interview.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义一个自旋锁
 * 思路： CAS + 原子引用
 * 自旋锁的优点：循环比较获取直到成功为止，没有类似wait的阻塞
 *
 * */

public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mylock(){
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null,thread)){ // 自旋

        }
        System.out.println("Thread " + thread.getName() + " come in!");
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println("Thread " + thread.getName() + " come out!");
    }
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.mylock();
            try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
            spinLockDemo.myUnLock();
        },"t1").start();

        new Thread(() -> {
            spinLockDemo.mylock();
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            spinLockDemo.myUnLock();
        },"t2").start();
    }
}
