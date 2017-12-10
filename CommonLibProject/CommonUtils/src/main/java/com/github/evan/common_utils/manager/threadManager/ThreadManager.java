package com.github.evan.common_utils.manager.threadManager;

import com.github.evan.common_utils.utils.DeviceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/10.
 */

public class ThreadManager {
    private static ThreadManager mInstance = null;
    public static ThreadManager getInstance(){
        if(null == mInstance){
            synchronized (ThreadManager.class){
                if(null == mInstance){
                    mInstance = new ThreadManager();
                }
            }
        }
        return mInstance;
    }

    private static final String OTHER_POOL = "OTHER_POOL";
    private static final String NET_WORK_POOL = "NET_WORK_POOL";
    private static final String IO_POOL = "IO_POOL";
    private static final String SINGLE_POOL = "SINGLE_POOL";

    private Map<String, ThreadPoolExecutor> mPoolExecutors = new HashMap<>();

    private ThreadManager(){
        int numberOfCPUCores = DeviceUtil.getNumberOfCPUCores();
        ThreadPoolExecutor otherPool = new ThreadPoolExecutor(3, 6, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ThreadFactory("Other Thread Pool"), new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor netWorkPool = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MINUTES, new SynchronousQueue<Runnable>(), new ThreadFactory("Network Thread Pool"), new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor ioPool = new ThreadPoolExecutor(numberOfCPUCores * 2, numberOfCPUCores * 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ThreadFactory("IO Thread Pool"), new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor singlePool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ThreadFactory("Single Thread Pool"), new ThreadPoolExecutor.AbortPolicy());

        otherPool.prestartCoreThread();         //预创建1个线程
        netWorkPool.prestartAllCoreThreads();   //预创建所有线程
        ioPool.prestartAllCoreThreads();
        singlePool.prestartCoreThread();

        mPoolExecutors.put(OTHER_POOL, otherPool);
        mPoolExecutors.put(NET_WORK_POOL, netWorkPool);
        mPoolExecutors.put(IO_POOL, ioPool);
        mPoolExecutors.put(SINGLE_POOL, singlePool);
    }

    public ThreadPoolExecutor getIOThreadPool(){
        return mPoolExecutors.get(IO_POOL);
    }

    public ThreadPoolExecutor getSingleThreadPool(){
        return mPoolExecutors.get(SINGLE_POOL);
    }

    public ThreadPoolExecutor getNetworkThreadPool(){
        return mPoolExecutors.get(NET_WORK_POOL);
    }

    public ThreadPoolExecutor getOtherThreadPool(){
        return mPoolExecutors.get(OTHER_POOL);
    }
}
