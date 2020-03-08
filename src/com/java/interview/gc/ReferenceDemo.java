package com.java.interview.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 1. 几种不同类型的引用：
 *      强引用：即使出现OOM也不回收，即使该对象以后永远都不会被用到JVM也不会回收
 *      软引用：内存足够时不回收，内存不充足时回收
 *      弱引用：不管内存够不够用，只要有gc就会回收
 *      虚引用：并不会决定对象的生命周期，且必须和引用队列一起使用，设置虚引用关联的唯一目的，
 *             就是在这个对象被收集器回收的时候收到一个系统通知或者后续添加进一步的处理。
 *
 *  *
 * 2. 使用到软引用/弱引用的场景：
 *      存在问题的场景：
 *          如果每次从硬盘中读取图片会严重影响性能
 *          如果一次性全都加载到内存中又可能造成内存溢出
 *      解决方案：
 *          用一个HashMap来保存图片的路径和相应图片对象关联的软引用之间的映射关系，在内存不足时，JVM会自动回收这些
 *      缓存图片对象所占用的空间，从而有效地避免了OOM的问题。
 *      Map<String,SoftReference<Bitmap>> imageCache = new HashMap<String,SoftReference<Bitmap>>();
 *
 *  3. WeakHashMap的使用
 *
 *  4. 四种GC Roots对象
 *      （1）虚拟机栈(栈帧中的局部变量区,也叫做局部变量表
 *      （2）方法区中的类静态属性引用的对象。
 *      （3）方法区中常量引用的对象
 *      （4）本地方法栈中N(Native方法)引用的对象
 *
 *  5.
 *
 *
 * */
public class ReferenceDemo {
    public static void main(String[] args) {
//        StrongReference();

//        SoftReferenceEnough();

//        SoftReferenceNotEnough();

//        WeakReference();

//        myHashMap();
//        MyWeakHashMap();

//        ReferenceQueue();
        PhantomReferenceQueue();


    }



    private static void StrongReference() {
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);
    }

    private static void SoftReferenceEnough(){
        Object o1 = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(objectSoftReference.get());

        o1 = null;
        System.gc();
        System.out.println("进行了垃圾回收！");

        System.out.println(o1);
        System.out.println(objectSoftReference.get());
    }

    // 创建大对象，并设置小内存 -Xms5m -Xmx5m -XX:+PrintGCDetails
    // 必须要有 o1 = null; ，不然，还存在一个强引用
    private static void SoftReferenceNotEnough(){
        Object o1 = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(objectSoftReference.get());
        o1 = null;
        try{
            byte[] bytes = new byte[10 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(o1);
            System.out.println(objectSoftReference.get());
        }
    }

    private static void WeakReference(){
        Object o1 = new Object();
        WeakReference<Object> objectWeakReference = new WeakReference<>(o1);

        System.out.println(o1);
        System.out.println(objectWeakReference.get());

        o1 = null;
        System.gc();
        System.out.println("进行了垃圾回收！");

        System.out.println(o1);
        System.out.println(objectWeakReference.get());
    }

    // put进去的K,V，是一个NODE节点对象，个人觉得应该是 NODE节点对 key仍然存在一个引用关系，
    // 所以没有被回收
    private static void myHashMap(){
        HashMap<Integer, String> hashMap = new HashMap<>();

        Integer key = new Integer(1);
        String value = "HashMap";

        hashMap.put(key,value);

        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);

    }

    private static void MyWeakHashMap(){
        WeakHashMap<Integer, String> hashMap = new WeakHashMap<>();

        Integer key = new Integer(2);
        String value = "HashMap";

        hashMap.put(key,value);

        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);

    }

    // 对象在真正被清理之前，会先放进ReferenceQueue中
    private static void ReferenceQueue(){
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1,referenceQueue);

        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        o1 = null;
        System.gc();
        System.out.println("进行了GC！");

        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll()); // 在GC后，ReferenceQueue中由无值变有值

    }

    private static void PhantomReferenceQueue(){
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1,referenceQueue);

        System.out.println(phantomReference.get()); // 总是为null的
        System.out.println(referenceQueue.poll());

        o1 = null;
        System.gc();
        System.out.println("进行了GC！");

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
