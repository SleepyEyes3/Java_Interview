package com.java.interview.thread;

/**
 * 查看死锁的命令 jps -l + jstack + 进程号, 记得要运行起来哦，傻屌！
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
            // 情况是这样的：
            //      AAA 拿到 lockA 的同时 ，BBB 拿到 lockB，两秒钟后，AAA 想去拿 lockB ， BBB 想去拿 lockA，但是,
            // 我们发现，这边的synchronized是一个嵌套的情况，也就是这时 AAA还没有释放掉lockA，BBB还没有释放掉lockB，
            // 最终导致两个线程都卡住了；
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
        new Thread(new DeadLockHolder(lockB,lockA),"BBB").start();
    }
}
