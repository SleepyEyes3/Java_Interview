package com.java.interview.thread.volatile1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/*
    1. 通过在num前面是否加volatile，验证其是否可以让内存可见。
        (1)第一个例子seeOkByVolatile()中可以发现，程序一直卡住，是因为线程所改变的值，发生在它自己的缓存中，并没有发生在主内存中
        (2)当我尝试使用另一个线程获取num的值时，发现可以获取到另一个线程的更新后的值，这是为什么？


    2. volatile 是否可以保证原子性，及一个线程在执行一个业务时，是否会被打断？
        2.1 为什么volatile不能保证原子性的？
            因为num++在字节码层面是有三个操作： （1）getfield （2）iadd （3）putfield ，多线程在进行putfield是，可能将
            其他线程putfield的值覆盖；
            但是在操作的过程中发现，数值不够大的话，结果就是同步的。

        2.2 怎么解决原子性问题呢？
            （1）sychronized
            （2）AtomicInteger + getAndIncrease 的使用，其原理是CAS
                public final int getAndAddInt(Object var1, long var2, int var4) {
                    int var5;
                    do {
                        var5 = this.getIntVolatile(var1, var2);
                    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

                    return var5;
                }

    3. 阻止指令重排；
        阻止指令重排只需要在多线程时考虑，不需要在单线程是考虑；
        在指定的变量前面加volatile就可以防止指令重排；
*/

class MyData{
    volatile int num = 0;

    public void addTo60(){
        this.num = 60;
    }

    public void addPlusPlus(){
        num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addByAtomic(){
        atomicInteger.getAndIncrement();
    }

}

public class VolatileDemo {
    public static void main(String[] args) {
//        seeOkByVolatile();
        atomicClass();
    }

    private static void atomicClass() {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 100000; j++) {
                    myData.addPlusPlus();
                    myData.addByAtomic();
                }
            },String.valueOf(i)).start();
        }

        // 等待其他线程完成的一种方法
        while(Thread.activeCount() > 2){ //一个主线程 一个GC线程
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+"\t finally number value :" +myData.num);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger, finally number value :" +myData.atomicInteger);
    }

    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");

            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t value update to " + myData.num);

        },"AAA").start();

        while(myData.num == 0){
            // 这里的主内存中的num在没有volatile修饰的情况下，将会在这里卡住，因为 “数值的变化发生在线程各自的缓存中”
            // “主存中的值还没有变化”
        }

//        new Thread(() -> {
//            try{
//                TimeUnit.SECONDS.sleep(4);
//            }catch (InterruptedException e ){
//                e.printStackTrace();
//            }
//
//            System.out.println(Thread.currentThread().getName() + "线程在4s后获得数据为：" + myData.num);
//        },"BBB").start();

        System.out.println(Thread.currentThread().getName()+"\tmission is over,value is "+myData.num);
    }
}

