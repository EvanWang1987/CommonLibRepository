package com.github.evan.common_utils_demo.ui.activity.aboutThread;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.javaDemo.thread.threadPool.ThreadPoolUtil;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/12.
 */
public class AboutThreadPoolActivity extends BaseLogCatActivity {


    private ScheduledExecutorService mScheduledThreadPool;
    private ThreadPoolExecutor mSingleThreadPool;
    private ThreadPoolExecutor mFixedThreadPool;
    private ThreadPoolExecutor mCacheThreadPool;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_about_thread_pool, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_thread_pool_feature, R.id.card_test_cache_thread_pool, R.id.card_test_fix_thread_pool, R.id.card_test_single_thread_pool, R.id.card_test_scheduled_thread_pool, R.id.card_stop_test})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_thread_pool_feature:
                showMessageDialog(getString(R.string.thread_pool_feature), getString(R.string.thread_pool), getString(R.string.confirm), null);
                break;

            case R.id.card_test_cache_thread_pool:
                testCachedThreadPool();
                break;

            case R.id.card_test_fix_thread_pool:
                testFixedThreadPool();
                break;

            case R.id.card_test_single_thread_pool:
                testSingleThreadPool();
                break;

            case R.id.card_test_scheduled_thread_pool:
                testScheduledThreadPool();
                break;
            case R.id.card_stop_test:
                stopTestThreadPool();
                break;
        }
    }

    private void stopTestThreadPool() {
        if(null != mCacheThreadPool && !mCacheThreadPool.isShutdown()){
            mCacheThreadPool.shutdownNow();
            mCacheThreadPool = null;
        }

        if(null != mFixedThreadPool && !mFixedThreadPool.isShutdown()){
            mFixedThreadPool.shutdownNow();
            mFixedThreadPool = null;
        }

        if(null != mSingleThreadPool && !mSingleThreadPool.isShutdown()){
            mSingleThreadPool.shutdownNow();
            mSingleThreadPool = null;
        }

        if(null != mScheduledThreadPool && !mScheduledThreadPool.isShutdown()){
            mScheduledThreadPool.shutdownNow();
            mScheduledThreadPool = null;
        }
    }

    private void testCachedThreadPool(){
        mCacheThreadPool = ThreadPoolUtil.getCacheThreadPool();

        int activeCount = mCacheThreadPool.getActiveCount();
        long keepAliveTime = mCacheThreadPool.getKeepAliveTime(TimeUnit.SECONDS);
        String queueName = mCacheThreadPool.getQueue().getClass().getName();
        String rejectedHandlerName = mCacheThreadPool.getRejectedExecutionHandler().getClass().getName();

        addLog("=========================CachedThreadPool========================");
        addLog("activeCount: " + activeCount);
        addLog("keepAliveTime: " + keepAliveTime + "秒");
        addLog("workQueueName: " + queueName);
        addLog("rejectedHandlerName: " + rejectedHandlerName);
        addLog("----- 开始测试CachedThreadPool -----");


        for (int i = 1; i <= 20; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    addLog("第" + index + "个Runnable, 开始执行");
                    SystemClock.sleep(500);
                    addLog("第" + index + "Runnable执行完毕");

                }
            };
            mCacheThreadPool.submit(runnable);
            addLog("提交第" + index + "个Runnable");
        }
    }

    private void testFixedThreadPool(){
        mFixedThreadPool = ThreadPoolUtil.getFixedThreadPool(5);

        int activeCount = mFixedThreadPool.getActiveCount();
        long keepAliveTime = mFixedThreadPool.getKeepAliveTime(TimeUnit.SECONDS);
        String queueName = mFixedThreadPool.getQueue().getClass().getName();
        String rejectedHandlerName = mFixedThreadPool.getRejectedExecutionHandler().getClass().getName();

        addLog("==========================FixedThreadPool=======================");
        addLog("activeCount: " + activeCount);
        addLog("keepAliveTime: " + keepAliveTime + "秒");
        addLog("workQueueName: " + queueName);
        addLog("rejectedHandlerName: " + rejectedHandlerName);
        addLog("----- 开始测试fixedThreadPool -----");

        for (int i = 1; i <= 20; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    addLog("第" + index + "个Runnable, 开始执行");
                    SystemClock.sleep(500);
                    addLog("第" + index + "Runnable执行完毕");

                }
            };
            mFixedThreadPool.submit(runnable);
            addLog("提交第" + index + "个Runnable");
        }
    }

    private void testSingleThreadPool(){
        mSingleThreadPool = ThreadPoolUtil.getSingleThreadPool();

        int activeCount = mSingleThreadPool.getActiveCount();
        long keepAliveTime = mSingleThreadPool.getKeepAliveTime(TimeUnit.SECONDS);
        String queueName = mSingleThreadPool.getQueue().getClass().getName();
        String rejectedHandlerName = mSingleThreadPool.getRejectedExecutionHandler().getClass().getName();

        addLog("==========================FixedThreadPool=======================");
        addLog("activeCount: " + activeCount);
        addLog("keepAliveTime: " + keepAliveTime + "秒");
        addLog("workQueueName: " + queueName);
        addLog("rejectedHandlerName: " + rejectedHandlerName);
        addLog("----- 开始测试fixedThreadPool -----");

        for (int i = 1; i <= 20; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    addLog("第" + index + "个Runnable, 开始执行");
                    SystemClock.sleep(500);
                    addLog("第" + index + "Runnable执行完毕");

                }
            };
            mSingleThreadPool.submit(runnable);
            addLog("提交第" + index + "个Runnable");
        }
    }

    private void testScheduledThreadPool(){
        mScheduledThreadPool = ThreadPoolUtil.getScheduledThreadPool(5);

        addLog("==========================FixedThreadPool=======================");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addLog("Runnable开始执行");
                SystemClock.sleep(500);
                addLog("Runnable执行完毕");

            }
        };

        mScheduledThreadPool.scheduleAtFixedRate(runnable, 0, 1000L, TimeUnit.MILLISECONDS);
    }


}
