package com.github.evan.common_utils.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.github.evan.common_utils.handler.SoftHandler;
import com.github.evan.common_utils.handler.SoftHandlerReceiver;

import java.util.LinkedList;

/**
 * Created by Evan on 2017/11/3.
 */
public abstract class BaseActivity extends AppCompatActivity implements SoftHandlerReceiver {
    public abstract @LayoutRes int getLayoutResId();
//    public abstract BaseActivityConfig onCreateActivityConfig();

    private LayoutInflater mLayoutInflater;
    private SoftHandler<BaseActivity> mHandler = new SoftHandler<>(Looper.getMainLooper());
    private boolean mIsDestroyed = false;
    private static LinkedList<BaseActivity> mActivities = new LinkedList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        init();
    }

    @Override
    protected void onDestroy() {
        mHandler.clearReceiver();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        mActivities.remove(this);
        mIsDestroyed = true;
    }

    @Override
    public void onHandleMessage(Message message) {

    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(this);
        mHandler.setReceiver(this);
        mActivities.addFirst(this);
//        BaseActivityConfig baseActivityConfig = onCreateActivityConfig();
    }

    public LayoutInflater getLayoutInflater(){
        return mLayoutInflater;
    }

    public boolean post(Runnable runnable, long delay){
        return mHandler.postDelayed(runnable, delay < 0 ? 0 : delay);
    }

    public boolean isForegroundActivity(){
        return mActivities.getFirst().hashCode() == this.hashCode();
    }

    public boolean isDestoried() {
        return mIsDestroyed;
    }
}
