package com.java.interview;

/**
 * 用于测试记载一些额外的发现：
 *      1. GetVarType() 简单类型变量怎么获取变量类型
 *                      强制类型转换会略去小数部分
 *
 *      2.
 *
 * */
public class Test {
    public static void main(String[] args) {
        GetVarType();

        CeilAndFloor();
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

}
