package com.java.interview.gc;

public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflow(); // Exception in thread "main" java.lang.StackOverflowError
    }

    public static void stackOverflow(){
        stackOverflow();
    }
}
