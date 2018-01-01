package com.github.evan.common_utils.javaDemo.thread.deadLock;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;


/**
 * Created by Evan on 2017/12/24.
 */
public class DeadLockBRunnable implements Runnable {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsStop = false;
    private Object mLockA, mLockB;
    private Observer mObserver;

    public DeadLockBRunnable() {
    }

    public Object getLockA() {
        return mLockA;
    }

    public void setLockA(Object lockA) {
        this.mLockA = lockA;
    }

    public Object getLockB() {
        return mLockB;
    }

    public void setLockB(Object lockB) {
        this.mLockB = lockB;
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
        while (!mIsStop) {
            synchronized (mLockB) {
                final String lockAString = Thread.currentThread().getName() + " 进入LockB";
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mObserver) {
                            mObserver.onPrintDeadLockDemoLog(lockAString);
                        }
                    }
                });
                SystemClock.sleep(500);
                synchronized (mLockA) {
                    final String lockBString = Thread.currentThread().getName() + " 进入LockB";
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (null != mObserver) {
                                mObserver.onPrintDeadLockDemoLog(lockBString);
                            }
                        }
                    });
                    SystemClock.sleep(500);
                }
            }
        }
    }
}
