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
 * 6. 查看默认的垃圾回收器
 *      java -XX:+PrintCommandLineFlags -version
 *
 * 7. 默认的垃圾回收器种类？
 *      -XX:+UseSerialGC
 *          新生代：def new generation --> 串行垃圾回收器 + 整理算法
 *          老年代：tenured generation --> 串行垃圾回收器 + 标记-整理
 *
 *      -XX:+UseParNewGC
 *          新生代：par new generation --> 并行垃圾回收器 + 整理算法
 *          老年代：tenured generation --> 串行垃圾回收器 + 标记-整理
 *
 *      -XX:+UseParallelGC 或 UseParallelOldGC （可互相激活） 吞吐量优先
 *          新生代：parallel scavenge（young generation） --> 并行垃圾回收器 + 整理算法
 *          老年代：parallel old generation --> 并行垃圾回收器 + 标记-整理
 *
 *      -XX:UseConcMarkSweepGC （以获取最短回收停顿时间为目标的回收器）在效果不理想的情况下可能会退化为Serial Old
 *          大型互联网公司用的多，要求低延迟
 *           新生代：par new generation --> 并行垃圾回收器 + 整理算法
 *           老年代：concurrent mark-sweep generation --> 并发垃圾回收器 + 标记-清除
 *
 *      -XX:UseG1GC
 *           新生代：garbage-first --> 标记-整理
 *           老年代：garbage-first --> 标记-整理
 *
 *      老年代和年轻代之间都有默认的关联关系
 *
 * 8. 垃圾收集器的选择
 *
 * 9. G1相对于CMS的优势
 *      G1没有内存碎片
 *      可以控制停顿时间
 *
 * 10. JVM+微服务
 *      java -server jvm参数 -jar .jar
 *
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
