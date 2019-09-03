package com.tianhe.java.concurrent.synctool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Extended countDownLatch count reset
 * @author: he.tian 公号天河聊架构
 * @time: 2018-08-22 15:26
 */
public class CyclicCountDownLatch {

    private final Sync sync;

    public CyclicCountDownLatch(int count){
        if(count < 0){
            throw new RuntimeException("The initial value of the counter cannot be less than 0");
        }
        this.sync = new Sync(count);
    }

    public void await() throws InterruptedException{
        sync.acquireSharedInterruptibly(1);
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException{
        return sync.tryAcquireSharedNanos(1,unit.toNanos(timeout));
    }

    public void countDown(){
        sync.releaseShared(1);
    }

    public long getCount(){
        return sync.getCount();
    }

    public void reset(){
        sync.reset();
    }

    public String toString() {
        return "[count = "+sync.getCount()+"]";
    }


    private static final class Sync extends AbstractQueuedSynchronizer{

        private final int startCount;

        Sync(int count){
            this.startCount = count;
            setState(count);
        }

        int getCount(){
            return getState();
        }

        protected int tryAcquireShared(int acquires){
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases){
            for (;;){
                int count = getState();
                if(count == 0){
                    return false;
                }
                int nextCount = count-1;
                if(compareAndSetState(count,nextCount)){
                    return nextCount == 0;
                }
            }
        }

        protected void reset(){
            setState(startCount);
        }
    }
}
