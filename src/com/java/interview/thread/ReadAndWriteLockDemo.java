package com.java.interview.thread;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用 ReentrantReadWriteLock 类，自己实现一个cache缓存，可以线程可以同时读，但是线程写的时候是独占的
 *
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取资源应该是可以同时进行的|
 * 但是
 * 如果一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行“读或写”
 *
 *      读--读 共存
 *      读--写 不能共存
 *      写--写 不能共存
 * */
class MyCache{
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    HashMap<String,Object> hm =new HashMap<>();

    public void read(String key){
        rwLock.readLock().lock(); // 在写共享资源的过程中，线程必须独占资源，不能够被打断
        try {
            System.out.println("Reading " + key);
            hm.get(key);
            try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println("Finish reading " + key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }

    public void write(String key, Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println("Writing " + key);
            hm.put(key, value);
            try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println("Finish Writing " + key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }
}
public class ReadAndWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for(int i = 0; i < 5; i++){
            final int tempI = i;
            new Thread(() -> {
                myCache.write(String.valueOf(tempI),String.valueOf(tempI));
            },String.valueOf(i)).start();
        }

        for(int i = 0; i < 5; i++){
            final int tempI = i;
            new Thread(() -> {
                myCache.read(String.valueOf(tempI));
            },String.valueOf(i)).start();
        }
    }
}
