import java.util.concurrent.TimeUnit;
/*
    1. 通过在num前面是否加volatile，验证其是否可以让内存可见。

    2. volatile 是否可以保证原子性，及一个线程在执行一个业务时，是否会被打断
*/

class MyData{
    volatile int num = 0;

    public void addTo60(){
        this.num = 60;
    }

    public void addPlusPlus(){
        this.num++;
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        seeOkByVolatile();
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            },"thread " + i).start();

        }

        while(Thread.activeCount() > 2){ //一个主线程 一个GC线程
            Thread.yield();
        }

        System.out.println(myData.num);

    }

    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");

            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t value update to " + myData.num);

        },"AAA").start();

        while(myData.num == 0){
            // 这里的主内存中的num在没有volatile修饰的情况下，将会在这里卡住，因为 “数值的变化发生在线程各自的缓存中”
            // “主存中的值还没有变化”
        }

        System.out.println(Thread.currentThread().getName()+"\tmission is over,value is "+myData.num);
    }
}

