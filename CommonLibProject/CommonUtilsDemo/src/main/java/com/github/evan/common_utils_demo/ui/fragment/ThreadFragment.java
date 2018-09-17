package com.github.evan.common_utils_demo.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.javaDemo.thread.deadLock.DeadLockARunnable;
import com.github.evan.common_utils.javaDemo.thread.deadLock.DeadLockBRunnable;
import com.github.evan.common_utils.javaDemo.thread.deadLock.Observer;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.ConsumerRunnable;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.ProduceRunnable;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.Resource;
import com.github.evan.common_utils.javaDemo.thread.threadPool.ThreadPoolUtil;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.DialogMode;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.dialog.InputDialog;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutThread.AboutThreadPoolActivity;

import java.util.concurrent.ExecutorService;

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
    private AsyncTask<String, Integer, String> mAsyncTask;
    private String mResultString;

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

    @Override
    public void onDialogConfirmButtonClick(DialogInterface dialog, DialogMode mode) {
        if(mode == DialogMode.INPUT_DIALOG){
            InputDialog inputDialog = (InputDialog) dialog;
            CharSequence inputText = inputDialog.getEditText(0);
            mResultString = inputText.toString();
            testAsyncTask(mResultString);
            mResultString = null;
            inputDialog.dismiss();
        }
    }

    @Override
    public void onDialogCancelButtonClick(DialogInterface dialog, DialogMode mode) {
        dialog.dismiss();
    }

    @OnClick({R.id.card_single_producer_consumer, R.id.card_stop_single_producer_consumer, R.id.card_multi_producer_consumer, R.id.card_stop_multi_producer_consumer, R.id.card_dead_lock, R.id.card_stop_dead_lock, R.id.card_about_thread_pool, R.id.card_about_async_task})
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

            case R.id.card_about_thread_pool:
                loadActivity(AboutThreadPoolActivity.class);
                break;

            case R.id.card_about_async_task:
                getActivityProvider().showDialog(this, DialogMode.INPUT_DIALOG, getString(R.string.notice), null, -1, getString(R.string.confirm), getString(R.string.cancel), new String[]{getString(R.string.hint_input_async_start_result)}, 1, new int[]{1}, -1, -1, -1);
                break;
        }
    }

    private void testAsyncTask(String resultString){
        Logger.d("resultString: " + resultString);
        mAsyncTask = new AsyncTask<String, Integer, String>() {



            @Override
            protected void onPreExecute() {
                getActivityProvider().showDialog(ThreadFragment.this, DialogMode.PROGRESS_DIALOG, getString(R.string.warning), getString(R.string.initializing), -1, getString(R.string.confirm), getString(R.string.cancel), null, -1, null, 100, 1, ProgressDialog.STYLE_HORIZONTAL);
            }

            @Override
            protected String doInBackground(String... params) {
                int i = 1;
                while (i <= 100){
                    SystemClock.sleep(100);
                    publishProgress(i);
                    i++;
                }
                return params[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                getActivityProvider().updateProgressDialogFromActivityProvider(values[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                getActivityProvider().dismissDialogsFromActivityProvider();
                getActivityProvider().showDialog(ThreadFragment.this, DialogMode.MESSAGE_DIALOG, getString(R.string.warning), s, -1, getString(R.string.confirm), getString(R.string.cancel), null, 1, null, -1, -1, -1);
            }
        };

        mAsyncTask.execute(resultString);
    }


    private void singleProducerConsumer(){
        mSingleResource = new Resource("商品");
        mSingleResource.setObserver(this);
        mProduceRunnable = new ProduceRunnable(mSingleResource);
        mConsumerRunnable = new ConsumerRunnable(mSingleResource);
        mProduceRunnable.setStop(false);
        mConsumerRunnable.setStop(false);
        Thread producerThread = new Thread(mProduceRunnable);
        Thread consumerThread = new Thread(mConsumerRunnable);
        producerThread.setName("生产者线程 " + producerThread.getId());
        consumerThread.setName("消费者线程 " + consumerThread.getId());
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
            mSingleResource.setObserver(null);
            mSingleResource = null;
        }

    }

    private void multiProducerConsumer(){
        mMultiResource = new Resource("商品");
        mMultiResource.setObserver(this);
        mProduceARunnable = new ProduceRunnable(mMultiResource);
        mProduceBRunnable = new ProduceRunnable(mMultiResource);
        mProduceCRunnable = new ProduceRunnable(mMultiResource);
        mConsumerARunnable = new ConsumerRunnable(mMultiResource);
        mConsumerBRunnable = new ConsumerRunnable(mMultiResource);
        mConsumerCRunnable = new ConsumerRunnable(mMultiResource);
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
        producerAThread.setName("生产者线程 " + producerAThread.getId());
        producerBThread.setName("生产者线程 " + producerBThread.getId());
        producerCThread.setName("生产者线程 " + producerCThread.getId());
        consumerAThread.setName("消费者线程 " + consumerAThread.getId());
        consumerBThread.setName("消费者线程 " + consumerBThread.getId());
        consumerCThread.setName("消费者线程 " + consumerCThread.getId());
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
            mMultiResource.setObserver(null);
            mMultiResource = null;
        }

    }

    private void deadLockDemo(){
        Object lockA = new Object();
        Object lockB = new Object();


        mDeadLockARunnable = new DeadLockARunnable();
        mDeadLockARunnable.setLockA(lockA);
        mDeadLockARunnable.setLockB(lockB);
        mDeadLockARunnable.setObserver(this);
        mDeadLockARunnable.setStop(false);

        mDeadLockBRunnable = new DeadLockBRunnable();
        mDeadLockBRunnable.setLockA(lockA);
        mDeadLockBRunnable.setLockB(lockB);
        mDeadLockBRunnable.setObserver(this);
        mDeadLockBRunnable.setStop(false);

        DeskIconManager.getInstance(getContext()).showLogCatIcon();

        Thread threadA = new Thread(mDeadLockARunnable);
        Thread threadB = new Thread(mDeadLockBRunnable);
        threadA.start();
        threadB.start();
    }

    private void stopDeadLock() {
        if(null != mDeadLockARunnable){
            mDeadLockARunnable.setLockA(null);
            mDeadLockARunnable.setLockB(null);
            mDeadLockARunnable.setObserver(null);
            mDeadLockARunnable.setStop(true);
            mDeadLockARunnable = null;
        }

        if(null != mDeadLockBRunnable){
            mDeadLockBRunnable.setLockA(null);
            mDeadLockBRunnable.setLockB(null);
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
