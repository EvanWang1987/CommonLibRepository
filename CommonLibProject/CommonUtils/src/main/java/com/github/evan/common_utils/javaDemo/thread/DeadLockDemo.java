package com.github.evan.common_utils.javaDemo.thread;

import android.os.SystemClock;


/**
 * Created by Evan on 2017/12/24.
 */

public class DeadLockDemo implements Runnable{

    public interface Observer{

        void onPrintLog(String log);

    }

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();
    private Observer mObserver;

    public void setObserver(Observer observer) {
        this.mObserver = observer;
    }

    @Override
    public void run() {
        while (true){
            synchronized (LOCK_A){
                String lockAString = Thread.currentThread().getName() + " 进入LockA";
                if(null != mObserver){
                    mObserver.onPrintLog(lockAString);
                }
                SystemClock.sleep(500);
                synchronized (LOCK_B){
                    String lockBString = Thread.currentThread().getName() + " 进入LockA";
                    if(null != mObserver){
                        mObserver.onPrintLog(lockBString);
                    }
                    SystemClock.sleep(500);
                }
            }
        }
    }
}
