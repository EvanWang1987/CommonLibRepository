package com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer;

import android.os.Handler;
import android.os.Looper;

import com.github.evan.common_utils.utils.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Evan on 2017/12/24.
 */
public class Resource {
    public static ReentrantLock LOCK = new ReentrantLock();
    public static Condition PRODUCER_CONDITION = LOCK.newCondition();
    public static Condition CONSUMER_CONDITION = LOCK.newCondition();
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private Observer mObserver;

    private int count;
    private String mGoodsName;
    private boolean mIsProduced;

    public Resource(String goods) {
        this.mGoodsName = goods;
    }

    public boolean isProduced() {
        return mIsProduced;
    }

    public void setProduced(boolean isProduced) {
        this.mIsProduced = isProduced;
        if (isProduced) {
            count++;
            final String log = Thread.currentThread().getName() + ", 生产: " + mGoodsName + count;
            Logger.d(log);
            if (null != mObserver) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mObserver.onSingleProducerConsumerPrintLog(log);
                    }
                });
            }
        } else {
            final String log = "-" + Thread.currentThread().getName() + ", 消费: " + mGoodsName + count;
            Logger.d(log);
            if (null != mObserver) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mObserver.onSingleProducerConsumerPrintLog(log);
                    }
                });
            }
        }
    }

    public int getCount() {
        return count;
    }

    public String getGoodsName() {
        return mGoodsName;
    }

    public void setmObserver(Observer observer) {
        this.mObserver = observer;
    }
}
