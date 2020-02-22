package com.java.interview.thread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/*
    1. 出现的异常
        java.util.ConcurrentModificationException

    2. 异常出现的原因
        多线程对于同一资源的争抢

    3. 解决方案
        (1) new vector();
        (2) List<String> list = Collections.synchronizedList(new ArrayList<>());
        (3) CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
            原理  -->  源码解析
                 public boolean add(E e) {
                     final ReentrantLock lock = this.lock;
                     lock.lock();  加锁
                     try{
                        Object[] elements = getArray();  获取容器
                        int len = elements.length;
                        Object[] newElements = Arrays.copyOf(elements, len + 1);  拷贝容器
                        newElements[len] = e;  在拷贝容器的最后添加元素
                        setArray(newElements);  将拷贝容器设置为新的容器
                        return true;  返回 true
                     } finally {
                            lock.unlock();  释放锁
                       }
                  }

    4. 优化方案

    5. 拓展
        set -> 同上面的解决方案 2,3
        HashMap -> 方案二任然可行 还可以用 concurrentHashMap


* */

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
//        ArrayListNotSafe();
    }

    private static void ArrayListNotSafe() {
        ArrayList<String> list = new ArrayList<>();

        // 1. 方案 1
//        Vector<String> list = new Vector<>();

        // 2. 方案 2
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        // 3. 方案 3
//        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 3000; i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
