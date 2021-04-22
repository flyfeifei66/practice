package com.zhaojufei.practice.bingfa;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static ThreadLocal data = new ThreadLocal<String>();

    public static void main(String[] args) {

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10000);

        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

        // 只能并发一个
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 30, TimeUnit.SECONDS, workQueue, handler);

        MyThread thread = new MyThread();

        threadPool.execute(thread);

        for (int i = 0; i < 2; i++) {
            MyThread2 thread2 = new MyThread2();
            threadPool.execute(thread2);
        }
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
        ThreadPoolTest.data.set("hello");
        System.out.println(Thread.currentThread().getId() + "已设置值");
    }
}

class MyThread2 implements Runnable {
    @Override
    public void run() {
        Object name = ThreadPoolTest.data.get();
        System.out.println(Thread.currentThread().getId() + "获取到的值==" + name);
    }
}