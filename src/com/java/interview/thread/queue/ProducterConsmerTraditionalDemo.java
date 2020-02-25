package com.java.interview.thread.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 lock / await / sinalAll 写一个 生产者-消费者模型
 *
 * 关键 ： 在多线程环境中，对于值的判断一定要用 while 而不是 if
 * */

class ShareData{

    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition =  lock.newCondition();

    public void increase(){
        lock.lock();
        try {
            while(num != 0 ){
                // 1. 在不等于0的情况下 不工作 阻塞在此
                condition.await(); // await只会阻塞当前的线程
            }
            // 2. 工作
            num++;
            // 3. 通知唤醒
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll(); // signalAll会唤醒所有的线程，包括当前线程
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decrease(){
        lock.lock();
        try {
            while(num == 0 ){
                // 1. 在等于0的情况下 不工作 阻塞在此
                condition.await(); // ？？？
            }
            // 2. 工作
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            // 3. 通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

public class ProducterConsmerTraditionalDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.increase();
            }
        },"t1").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.decrease();
            }
        },"t2").start();
    }
}
