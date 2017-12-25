package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.javaDemo.thread.deadLock.DeadLockARunnable;
import com.github.evan.common_utils.javaDemo.thread.deadLock.DeadLockBRunnable;
import com.github.evan.common_utils.javaDemo.thread.deadLock.Observer;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.ConsumerRunnable;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.ProduceRunnable;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.Resource;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;

import java.util.concurrent.ThreadPoolExecutor;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/24.
 */
public class ThreadFragment extends BaseFragment implements Observer, com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.Observer {
    private static DeadLockARunnable mDeadLockARunnable;
    private static DeadLockBRunnable mDeadLockBRunnable;
    private ProduceRunnable mProduceRunnable;
    private ConsumerRunnable mConsumerRunnable;
    private Resource mSingleResource;
    private Resource mMultiResource;
    private ProduceRunnable mProduceARunnable;
    private ProduceRunnable mProduceBRunnable;
    private ProduceRunnable mProduceCRunnable;
    private ConsumerRunnable mConsumerARunnable;
    private ConsumerRunnable mConsumerBRunnable;
    private ConsumerRunnable mConsumerCRunnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thread, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            stopSingleProducerConsumer();
            stopMultiProducerConsumer();
            stopDeadLock();
            DeskIconManager.getInstance(getContext()).dismissLogCatIcon();
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_single_producer_consumer, R.id.card_stop_single_producer_consumer, R.id.card_multi_producer_consumer, R.id.card_stop_multi_producer_consumer, R.id.card_dead_lock, R.id.card_stop_dead_lock})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_single_producer_consumer:
                singleProducerConsumer();
                break;

            case R.id.card_stop_single_producer_consumer:
                stopSingleProducerConsumer();
                break;

            case R.id.card_multi_producer_consumer:
                multiProducerConsumer();
                break;

            case R.id.card_stop_multi_producer_consumer:
                stopMultiProducerConsumer();
                break;

            case R.id.card_dead_lock:
                deadLockDemo();
                break;

            case R.id.card_stop_dead_lock:
                stopDeadLock();
                break;
        }
    }




    private void singleProducerConsumer(){
        mSingleResource = new Resource("商品");
        mSingleResource.setmObserver(this);
        mProduceRunnable = new ProduceRunnable(mSingleResource);
        mConsumerRunnable = new ConsumerRunnable(mSingleResource);
        mProduceRunnable.setStop(false);
        mConsumerRunnable.setStop(false);
        Thread producerThread = new Thread(mProduceRunnable);
        Thread consumerThread = new Thread(mConsumerRunnable);
        producerThread.setName("生产者");
        consumerThread.setName("消费者");
        DeskIconManager.getInstance(getContext()).showLogCatIcon();
        producerThread.start();
        consumerThread.start();
    }

    private void stopSingleProducerConsumer() {
        if(null != mProduceRunnable){
            mProduceRunnable.setStop(true);
            mProduceRunnable = null;
        }

        if(null != mConsumerRunnable){
            mConsumerRunnable.setStop(true);
            mConsumerRunnable = null;
        }

        if(null != mSingleResource){
            mSingleResource.setmObserver(null);
            mSingleResource = null;
        }

    }

    private void multiProducerConsumer(){
        mSingleResource = new Resource("商品");
        mSingleResource.setmObserver(this);
        mProduceARunnable = new ProduceRunnable(mSingleResource);
        mProduceBRunnable = new ProduceRunnable(mSingleResource);
        mProduceCRunnable = new ProduceRunnable(mSingleResource);
        mConsumerARunnable = new ConsumerRunnable(mSingleResource);
        mConsumerBRunnable = new ConsumerRunnable(mSingleResource);
        mConsumerCRunnable = new ConsumerRunnable(mSingleResource);
        mProduceARunnable.setStop(false);
        mProduceBRunnable.setStop(false);
        mProduceCRunnable.setStop(false);
        mConsumerARunnable.setStop(false);
        mConsumerBRunnable.setStop(false);
        mConsumerCRunnable.setStop(false);
        Thread producerAThread = new Thread(mProduceARunnable);
        Thread producerBThread = new Thread(mProduceBRunnable);
        Thread producerCThread = new Thread(mProduceCRunnable);
        Thread consumerAThread = new Thread(mConsumerARunnable);
        Thread consumerBThread = new Thread(mConsumerBRunnable);
        Thread consumerCThread = new Thread(mConsumerCRunnable);
        producerAThread.setName("生产者A");
        producerBThread.setName("生产者B");
        producerCThread.setName("生产者C");
        consumerAThread.setName("消费者A");
        consumerBThread.setName("消费者B");
        consumerCThread.setName("消费者C");
        DeskIconManager.getInstance(getContext()).showLogCatIcon();
        producerAThread.start();
        producerBThread.start();
        producerCThread.start();
        consumerAThread.start();
        consumerBThread.start();
        consumerCThread.start();
    }

    private void stopMultiProducerConsumer() {
        if(null != mProduceARunnable){
            mProduceARunnable.setStop(true);
            mProduceARunnable = null;
        }

        if(null != mProduceBRunnable){
            mProduceBRunnable.setStop(true);
            mProduceBRunnable = null;
        }

        if(null != mProduceCRunnable){
            mProduceCRunnable.setStop(true);
            mProduceCRunnable = null;
        }

        if(null != mConsumerARunnable){
            mConsumerARunnable.setStop(true);
            mConsumerARunnable = null;
        }

        if(null != mConsumerBRunnable){
            mConsumerBRunnable.setStop(true);
            mConsumerBRunnable = null;
        }

        if(null != mConsumerCRunnable){
            mConsumerCRunnable.setStop(true);
            mConsumerCRunnable = null;
        }

        if(null != mMultiResource){
            mMultiResource.setmObserver(null);
            mMultiResource = null;
        }

    }

    private void deadLockDemo(){
        mDeadLockARunnable = new DeadLockARunnable();
        mDeadLockARunnable.setObserver(this);
        mDeadLockARunnable.setStop(false);

        mDeadLockBRunnable = new DeadLockBRunnable();
        mDeadLockBRunnable.setObserver(this);
        mDeadLockBRunnable.setStop(false);

        DeskIconManager.getInstance(getContext()).showLogCatIcon();

        ThreadPoolExecutor otherThreadPool = ThreadManager.getInstance().getOtherThreadPool();
        otherThreadPool.execute(mDeadLockARunnable);
        otherThreadPool.execute(mDeadLockBRunnable);
    }

    private void stopDeadLock() {
        if(null != mDeadLockARunnable){
            mDeadLockARunnable.setObserver(null);
            mDeadLockARunnable.setStop(true);
            mDeadLockARunnable = null;
        }

        if(null != mDeadLockBRunnable){
            mDeadLockBRunnable.setObserver(null);
            mDeadLockBRunnable.setStop(true);
            mDeadLockBRunnable = null;
        }
    }

    @Override
    public void onPrintDeadLockDemoLog(String log) {
        DeskIconManager.getInstance(getContext()).addLog(log);
    }

    @Override
    public void onSingleProducerConsumerPrintLog(CharSequence log) {
        DeskIconManager.getInstance(getContext()).addLog(log);
    }
}
