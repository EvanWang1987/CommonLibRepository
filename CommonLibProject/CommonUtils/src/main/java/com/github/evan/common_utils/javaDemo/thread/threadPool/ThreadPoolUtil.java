package com.github.evan.common_utils.javaDemo.thread.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Evan on 2018/9/12.
 */
public class ThreadPoolUtil {

    /**
     * 获取单线程池
     *
     * 此线程池核心数为1，最大数为1，存活时间为1分钟，队列为LinkedBlockQueue(基于链表的先进先出队列,默认构造容量为Integer.MAX_VALUE),拒绝策略为AbortPolice(提交的Runnable超出最大线程数，超出队列缓存数则拒绝接收Runnable，并抛出异常)
     * 此线程池为单线程池，只维护1个线程，提交的Runnable全缓存至队列，运行完Runnable后队列中没有Runnable线程即销毁。
     *
     * @return
     */
    public static ThreadPoolExecutor getSingleThreadPool(){
        return (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
    }

    /**
     * 获取缓存线程池。
     *
     * 此线程池核心线程数为0，最大线程数为Integer.MAX_VALUE,存活时间为1分钟,队列为SynchronousQueue(一个缓存值为1的阻塞队列),拒绝策略为AbortPolice(提交的Runnable超出最大线程数，超出队列缓存数则拒绝接收Runnable，并抛出异常)
     * 此线程池提交一次Runnable就会开启一个线程去执行，执行完毕后，线程缓存1分钟。
     *
     *
     * @return
     */
    public static ThreadPoolExecutor getCacheThreadPool(){
        return (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }


    /**
     * 获取固定线程池。
     *
     * 此线程池核心数，最大数都为nThreads, 存活时间为0, 缓存队列为LinkedBlockingQueue, 拒绝策略为AbortPolice.
     * 此线程池固定线程维护数量，超出的Runnable放入缓存队列里，等待线程继续执行，队列满则抛出异常。
     *
     *
     * @return
     */
    public static ThreadPoolExecutor getFixedThreadPool(int nThreads){
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 周期线程池。
     *
     * 此线程池可以周期性的执行一个Runnable，核心数为nThreads，最大数为Integer.MAX_VALUE, 队列为DelayedWorkQueue, 拒绝策略为AbortPolice.
     *
     *
     * @param nThreads
     * @return
     */
    public static ScheduledExecutorService getScheduledThreadPool(int nThreads){
        return Executors.newScheduledThreadPool(nThreads);
    }
}
