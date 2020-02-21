package com.java.interview.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> reference = new AtomicReference<>(100);

    static  AtomicStampedReference<Integer> reference2 = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        // ABA问题demo
//        ABA();

        // ABA问题的解决
        AtomaticStampRef();
    }

    private static void AtomaticStampRef() {
        new Thread(() -> {
            int stamp = reference2.getStamp();
            System.out.println("t3线程开始时的stamp: " + stamp);

            // 确保t4线程能够获得最开始时的stamp
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            reference2.compareAndSet(100,101,reference2.getStamp(),reference2.getStamp()+1);
            reference2.compareAndSet(101,100,reference2.getStamp(),reference2.getStamp()+1);

            System.out.println("t3线程完成ABA后的stamp: " + reference2.getStamp());
            System.out.println("t3线程完成ABA后的reference: " + reference2.getReference());

            System.out.println("======================");

        },"t3").start();

        new Thread(() -> {
            int stamp = reference2.getStamp();
            System.out.println("t4线程开始时的stamp: " + stamp);

            // 确保t3线程能够完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            boolean done = reference2.compareAndSet(100,2020,stamp,stamp + 1);

            System.out.println("t4线程时候完成update： " + done);
            System.out.println("t4当前的stamp: " + stamp);
            System.out.println("当前的stamp: " + reference2.getStamp());
            System.out.println("当前的真实值： " + reference2.getReference());

        },"t4").start();
    }

    private static void ABA() {
        new Thread(() -> {
            reference.compareAndSet(100,101);
            reference.compareAndSet(101,100); // ABA
        },"t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(reference.compareAndSet(100, 102) + "\tcurrent val: " + reference.get());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"t1").start();
    }
}
