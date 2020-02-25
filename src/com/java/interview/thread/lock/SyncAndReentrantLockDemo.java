package com.java.interview.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized和Lock有什么区别？用Lock有什么好处？你举例说说。
 * 1. 原始构成
 *    synchronized是关键字，是属于JVM层面的
 *      monitorenter（底层是通过monitor对象来完成，其实wait/notify等方法也依赖于monitor对象，只有在同步块或方法中才能调wait/notify等方法）
 *      monitorexit
 *    Lock是具体类（java.util.concurrent.locks.Lock）是api层面的锁
 *
 * 2. 使用方法
 *    synchronized 不需要用户去手动释放锁，当synchronized代码执行完成后系统会自动让线程释放对锁的占用
 *    ReentantLock则需要用户手动释放锁，并没有自动释放，就有可能出现死锁的情况
 *    需要lock()和unlock()方法配合try/finally语句块来完成
 *
 * 3. 等待是否可中断
 *    synchronized不可中断，之后抛出异常和正常运行两种情况
 *    ReentrantLock可中断，1. 设置超时方法trylock(long timeout,TimeUnit unit)
 *                        2. lockInterruptibly()放代码块中，调用interrupt()方法可中断
 *
 * 4. 加锁是否公平
 *    synchronized 是非公平锁
 *    ReentrantLock 两者都可以，默认非公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁
 *
 * 5. 锁绑定多个条件Condition
 *    synchronized没有
 *    ReentrantLock用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程
 *
 *  题目：多线程之间按顺序调用，实现A->B->C三个线程的启动，要求如下：
 *
 *  AA打印5次，BB打印10次，CC打印15次
 *  ....
 *  循环10次
 *
 * */

class Tool{
    private int num = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void PrintA(){
        lock.lock();
        try {
            while(num != 1){
                // wait
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("A");
            }
            num++;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void PrintB(){
        lock.lock();
        try {
            while(num != 2){
                // wait
                condition2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("B");
            }
            num++;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void PrintC(){
        lock.lock();
        try {
            while(num != 3){
                // wait
                condition3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("C");
            }
            num = 1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        Tool tool = new Tool();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tool.PrintA();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tool.PrintB();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                tool.PrintC();
            }
        },"C").start();
    }
}
