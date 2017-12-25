package com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer;

import android.os.SystemClock;

/**
 * Created by Evan on 2017/12/25.
 */
public class ConsumerRunnable implements Runnable {
    private Resource mResource;
    private boolean mIsStop = false;

    public ConsumerRunnable(Resource resource) {
        this.mResource = resource;
    }

    public boolean isStop() {
        return mIsStop;
    }

    public void setStop(boolean isStop) {
        this.mIsStop = isStop;
    }

    @Override
    public void run() {
        while (!mIsStop) {
            mResource.LOCK.lock();

            if (!mResource.isProduced()) {
                try {
                    mResource.CONSUMER_CONDITION.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            mResource.setProduced(false);
            mResource.PRODUCER_CONDITION.signal();

            mResource.LOCK.unlock();
            SystemClock.sleep(500);
        }
    }
}
