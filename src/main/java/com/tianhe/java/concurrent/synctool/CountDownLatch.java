package com.tianhe.java.concurrent.synctool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * How do you get a CountDownLatch
 * @author: he.tian 公众号天河聊架构
 * @time: 2019-07-08 17:27
 */
public class CountDownLatch {

    private int count;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public CountDownLatch(int count){
        if(count < 0){
            throw new IllegalArgumentException();
        }
        this.count = count;
    }

    public void countDown(){
        try {
            lock.lock();
            --count;
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public void await(){
        try {
            lock.lock();
            while (count != 0){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            lock.unlock();
        }
    }
}
