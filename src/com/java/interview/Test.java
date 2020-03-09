package com.java.interview;

/**
 * 用于测试记载一些课程之外额外的发现：
 *      1. GetVarType() 简单类型变量怎么获取变量类型
 *                      强制类型转换会略去小数部分
 *
 *      2. 看一下不同的变量对于同一个内存的引用情况
 *          通过实验发现：当将一个引用指向null时，相当于断开了引用内存之间的联系，并不是将引用锁所指的内存置为了null
 *
 *
 * */
public class Test {
    public static void main(String[] args) {
        // 1.
//        GetVarType();

        // 2.
//        CeilAndFloor();

        // 3.
        difRefToSameMemory();
    }

    private static void CeilAndFloor() {
        double a = 30;
        double b = 20;
        double c = a / b;
        System.out.println("c: " + c);
        System.out.println("c向上取整: " + Math.ceil(c));
        System.out.println("c向下取整: " + Math.floor(c));
    }

    private static void GetVarType() {
        double d = 12.22;
        float f = 2.22f;

        int i = 2;

        float j = 2.5f;

        double a = d / i;
        float b = f / i;

        float c = (int)j;

        System.out.println(getType(a));
        System.out.println(getType(b));
        System.out.println(c);
    }

    public static String getType(Object c){
        return c.getClass().getName();
    }

    public static void difRefToSameMemory(){
        Integer i1 = new Integer(1);
        Integer i2 = i1;
        i1 = null;
        System.gc();
        System.out.println("i1: " + i1);
        System.out.println("i2: " + i2);

    }

}
