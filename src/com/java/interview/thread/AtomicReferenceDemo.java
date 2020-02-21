package com.java.interview.thread;


import java.util.concurrent.atomic.AtomicReference;

class User{
    String name;
    int age;

    public User(String name,int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<Object> objectAtomicReference = new AtomicReference<>();

        User zhang3 = new User("zhang3", 24);
        User li4 = new User("li4", 25);

        objectAtomicReference.set(zhang3);

        System.out.println(objectAtomicReference.compareAndSet(zhang3, li4) + "\t current obj : " + objectAtomicReference.get().toString());
        System.out.println(objectAtomicReference.compareAndSet(zhang3, li4) + "\t current obj : " + objectAtomicReference.get().toString());
    }
}
