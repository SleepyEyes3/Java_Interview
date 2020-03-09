package com.java.interview.gc;

/**
 * 1. 查看某个VM线程的VM配置
 *      jps -l + jinfo -flag <name> pid 查看VM参数的配置情况 ,包括 布尔型和键值对类型
 *
 *      jinfo -flags  pid 查看所有配置的参数
 *
 * 2. 查看VM的默认配置
 *      java -XX:+PrintFlagsInitial 查看VM的初始配置 ，其中 “=”表示VM默认的篇日志，“：=”表示经过修改的篇配置
 *      java -XX:+PrintFlagsFinal -version
 *
 * 3. 在命令行给指定的class文件添加VM参数
 *      java -XX:+PrintGCDetails .class文件
 *
 * 4. jinfo -flag ThreadStackSize 17452的大小为0
 *      为0的情况下，是系统默认值，可视为Linux 64bit的值，也就是 1024k
 *
 * 5. 典型VM参数设置案例
 *      -Xms128m    物理内存的 1/16
 *      -Xmx4096m   物理内存的 1/4
 *      -Xss1024k   512k ~ 1024k
 *      -XX:MetaspaceSize=512m
 *      -XX:+PrintCommandFlags
 *      -XX:+PrintGCDetails
 *      -XX:+UseSerialGC
 *      -XX:SurvivorRatio=4 默认是8 暂时需要配合 UseSerialGC 一起使用
 *      -XX:NewRatio=4 默认是2
 *      -XX:MaxTenuringThreshold=15 值需要在 0~15 之间
 *
 * 6.
 * */
public class GCDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
//        Thread.sleep(Integer.MAX_VALUE);

//        GetRuntimeMemory();

        byte[] bytes = new byte[20 * 1024 *1024];
    }

    private static void GetRuntimeMemory() {
        long total = Runtime.getRuntime().totalMemory();
        long max = Runtime.getRuntime().maxMemory();

        System.out.println("total memory: " + total + "字节、"+(total/(double)1024/1024) + "MB");
        System.out.println("max memory: " + max + "字节、"+(total/(double)1024/1024) + "MB");
    }


}
