package com.java.interview.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Bruce_J
 * @Date: 2020-2-24 11:27
 *
 * 可重入锁（也叫做递归锁）
 *
 * 指的是统一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法或自动获取
 *
 * 也就是说，线程可以进入任何一个他已经拥有的锁所同步着的代码块
 */
class Man implements Runnable{
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName() + "\t invoke sendSMS");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName() + "\t invoke sendEmail");
    }

    Lock lock = new ReentrantLock(false);

    @Override
    public void run() {
        get();
    }

    public synchronized void get(){
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoke get");
            set();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            lock.unlock();  // 加锁-解锁 必须是一一对应的
        }
    }

    public synchronized void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoke set");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Man man = new Man();
        new Thread(() -> {
            man.sendSMS();
        },"t1").start();

        new Thread(() -> {
            man.sendSMS();
        },"t2").start();

        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("==============");

        Thread t3 = new Thread(man,"t3");
        Thread t4 = new Thread(man,"t4");

        t3.start();
        t4.start();


    }
}
