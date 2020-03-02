package com.java.interview.thread;

/**
 * 查看死锁的命令 jps -l + jstack + 进程号
 *
 * */
import java.util.concurrent.TimeUnit;

class DeadLockHolder implements Runnable{
    private String lockA;
    private String lockB;

    public DeadLockHolder(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t自己持有\t" + lockA + "\t尝试获得\t" + lockB);
            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t自己持有\t" + lockB + "\t尝试获得\t" + lockA);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new DeadLockHolder(lockA,lockB),"AAA").start();
        new Thread(new DeadLockHolder(lockA,lockB),"BBB").start();
    }
}
