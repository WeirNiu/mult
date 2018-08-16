package com.mult.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author Weirdo
 * @date 2018/8/16 9:39
 */
public class HighConcurrencyTest {
    public static void main(String[] args) {
        int count=3000;
        //CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。
        final CountDownLatch cdl=new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cdl.countDown();
                    try {
                        cdl.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        ConnectionUtil.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}
