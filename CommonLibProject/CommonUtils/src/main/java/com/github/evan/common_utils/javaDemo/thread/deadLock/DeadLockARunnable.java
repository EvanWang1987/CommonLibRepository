package com.github.evan.common_utils.javaDemo.thread.deadLock;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;


/**
 * Created by Evan on 2017/12/24.
 */
public class DeadLockARunnable implements Runnable{
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsStop = false;

    private Observer mObserver;

    public DeadLockARunnable() {
    }

    public boolean isStop() {
        return mIsStop;
    }

    public void setStop(boolean isStop) {
        this.mIsStop = isStop;
    }

    public void setObserver(Observer observer) {
        this.mObserver = observer;
    }

    @Override
    public void run() {
        while (!mIsStop){
            synchronized (Lock.LOCK_A){
                final String lockAString = Thread.currentThread().getName() + " 进入LockA";
                if(null != mObserver){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mObserver.onPrintDeadLockDemoLog(lockAString);
                        }
                    });
                }
                synchronized (Lock.LOCK_B){
                    final String lockBString = Thread.currentThread().getName() + " 进入LockA";
                    if(null != mObserver){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mObserver.onPrintDeadLockDemoLog(lockBString);
                            }
                        });
                    }
                }
            }
        }
    }
}
