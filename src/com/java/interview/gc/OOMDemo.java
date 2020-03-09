package com.java.interview.gc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class OOMDemo {
    public static void main(String[] args) {

        // 1. Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//        JavaHeapSpace();

        // 2. Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
        GCOverHeadDemo();

        // 3. Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
//        DirectBufferMemoryDemo();

        // 4. Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
//        UnableCreateNewThreadDemo();
    }


    // -Xms5m -Xmx5m
    private static void JavaHeapSpace(){
        byte[] bytes = new byte[10 * 1024 * 1024];
    }

    // 百分之九十八的时间用来垃圾回收，回收不到百分之二的堆内存
    // -Xms10m -Xmx10m
    private static void GCOverHeadDemo(){
        List<String> list = new ArrayList<>();
        int i = 0;
        try {
            while(true){
                list.add(String.valueOf(i++));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    // 物理内存（直接内存）爆了
    // -Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m
    private static void DirectBufferMemoryDemo(){
        System.out.println("配置的maxDirectBuffermemory： " + sun.misc.VM.maxDirectMemory()/1024/1024 + "MB");
        ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);
    }

    // 不能创建更多的本地线程
    private static void UnableCreateNewThreadDemo(){
        for(int i = 1; ; i++){
            System.out.println(i);
            new Thread(() -> {
                try { Thread.sleep(Integer.MAX_VALUE); }catch (InterruptedException e){ e.printStackTrace(); }
            },String.valueOf(i)).start();
        }
    }

    // 元空间中存储的内容：虚拟机加载的类信息 常量池 静态变量 及时编译后的代码
    // -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
    private static void MetaSpace(){
        int i = 0;
        try {
            i++;
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    static class OOMTest{}
}
