package com.java.interview.gc;

public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        // Exception in thread "main" java.lang.StackOverflowError
        stackOverflow();
    }

    public static void stackOverflow(){
        stackOverflow();
    }
}
