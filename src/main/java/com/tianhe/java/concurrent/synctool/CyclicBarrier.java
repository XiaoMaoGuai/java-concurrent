package com.tianhe.java.concurrent.synctool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * How to implement a CyclicBarrier
 * @author: he.tian 公众号天河聊架构
 * @time: 2019-07-08 16:28
 */
public class CyclicBarrier {

    private int count;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public CyclicBarrier(int count){
        if(count < 0){
            throw new IllegalArgumentException();
        }
        this.count = count;
    }

    public void await(){
        try {
            lock.lock();
            if(--count == 0){
                condition.signalAll();
            }
            try {
                if(count != 0){
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }
}
