package com.java.interview.thread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


/**
 * 注意：
 *       1. get() 方法的使用，最后放在调用线程的最后，因为调用线程会阻塞在 get() 处，等待值的返回
 *
 *       2. .isDone()用于判断FutureTask的状态
 *
 *       3. FutureTask 用于不同的线程的时候，FutureTask的只会进入一次，想要进多次，只能创建多个futureTask实例，输入不同的线程中。
 *
 * */
class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable");
        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
        return 1314;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(new MyThread()); // FutureTask的实现了Runable接口 构造函数传参是 Callable接口类型
        FutureTask<Integer> futureTask1 = new FutureTask(new MyThread()); // FutureTask的实现了Runable接口 构造函数传参是 Callable接口类型

        new Thread(futureTask).start();
        new Thread(futureTask1).start();

        System.out.println(Thread.currentThread().getName());

        int ret = futureTask.get();

        System.out.println(100 + ret);

    }
}
