package com.java.interview.thread.volatile1;

import java.util.concurrent.atomic.AtomicInteger;


/*
    1. 什么是CAS?
        CAS --> compare and swap
        私有线程中的拷贝变量与主内存中的变量进行比较，如果相等，就将update的值set到主内存，如果不相等则不set (真实值、期望值)

    2. CAS是怎么保证原子性的？
        （1）CAS是一条CPU并发原语
        （2）getAndIncreatment  -> unsafe.getAndAddInt  -> c++ do while CAS 直至拷贝变量与主内存中的变量一致，退出循环

    3. CAS的优缺点？

    4. ABA问题？

    5. 怎么解决ABA问题？

* */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 13) + "\t current data: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 17) + "\t current data: " + atomicInteger.get());

    }
}
