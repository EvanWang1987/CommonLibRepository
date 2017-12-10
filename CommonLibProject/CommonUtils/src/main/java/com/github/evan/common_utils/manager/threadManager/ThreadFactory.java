package com.github.evan.common_utils.manager.threadManager;

import android.support.annotation.NonNull;


/**
 * Created by Evan on 2017/12/10.
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {
    private String mPoolName;

    public ThreadFactory(String poolName) {
        this.mPoolName = poolName;
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(thread.getName() + " in thread pool: " + mPoolName);
        return thread;
    }
}
