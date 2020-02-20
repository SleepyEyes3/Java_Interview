package com.java.interview.thread;

public class SingletonDemo {
    private SingletonDemo(){
        System.out.println("currentThreadName:" + Thread.currentThread().getName());
    }

    private static volatile SingletonDemo instance = null;

    public  static SingletonDemo getInstance(){
        // （DCL）双端检索机制
        synchronized (SingletonDemo.class){
            if(instance == null){
                instance = new SingletonDemo();
            }
            return instance;
        }
    }

    public static void main(String[] args) {
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());  // true
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());  // true
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());  // true

        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
