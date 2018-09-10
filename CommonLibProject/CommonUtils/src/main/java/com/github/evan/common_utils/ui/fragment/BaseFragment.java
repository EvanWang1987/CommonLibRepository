package com.github.evan.common_utils.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.github.evan.common_utils.handler.SoftHandler;
import com.github.evan.common_utils.handler.SoftHandlerReceiver;
import com.github.evan.common_utils.ui.activity.ActivityProvider;

/**
 * Created by Evan on 2017/11/4.
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment implements SoftHandlerReceiver, FragmentProvider {
    protected abstract void loadData();

    protected static final int IDLE = 0;
    protected static final int UNKNOW_ERROR = -1;
    protected static final int LOAD_COMPLETE = 1;
    protected static final int NET_UNAVAILABLE = 2;
    protected static final int NET_TIME_OUT = 3;
    protected static final int LOAD_EMPTY = 4;
    protected static final int LOAD_ERROR = 5;

    private SoftHandler<BaseFragment> mHandler = new SoftHandler<>(Looper.getMainLooper());
    private LayoutInflater mLayoutInflater;
    private boolean mIsLoadedDataAtFirstHiddenChange = false;
    private ActivityProvider mActivityProvider;

    @Override
    public void setActivityProvider(ActivityProvider activityProvider) {
        mActivityProvider = activityProvider;
    }

    @Override
    public ActivityProvider getActivityProvider() {
        return mActivityProvider;
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public void onDestroyView() {
        mHandler.clearReceiver();
        mHandler.removeCallbacksAndMessages(null);
        mActivityProvider = null;
        super.onDestroyView();
    }

    @Override
    public void onHandleMessage(Message message) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            if(!mIsLoadedDataAtFirstHiddenChange){
                loadData();
                mIsLoadedDataAtFirstHiddenChange = true;
            }
        }
    }

    protected SoftHandler getHandler(){
        return mHandler;
    }

    protected boolean postDelay(Runnable runnable, long delayTime){
        return mHandler.postDelayed(runnable, delayTime);
    }

    public void loadActivity(Class<? extends Activity> destination){
        Intent intent = new Intent(getContext(), destination);
        startActivity(intent);
    }

    public void loadActivity(Class<? extends Activity> destination, Bundle extras, boolean isForResult, int requestCode){
        Intent intent = new Intent(getContext(), destination);
        if(null != extras){
            intent.putExtras(extras);
        }

        if(isForResult){
            startActivityForResult(intent, requestCode);
        }else{
            startActivity(intent);
        }
    }

    public void loadActivity(String action, Bundle extras, boolean isForResult, int requestCode){
        Intent intent = new Intent(action);
        if(null != extras){
            intent.putExtras(extras);
        }

        if(isForResult){
            startActivityForResult(intent, requestCode);
        }else{
            startActivity(intent);
        }
    }

    protected void sendEmptyMessage(int what){
        sendMessage(what, -1, -1, null);
    }

    protected void sendMessage(int what, int arg1, int arg2, Bundle bundle){
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.setData(bundle);
        boolean receiverExists = mHandler.isReceiverExists();
        if(receiverExists){
            BaseFragment receiver = mHandler.getReceiver();
            if(receiver.hashCode() != this.hashCode()){
                mHandler.clearReceiver();
                mHandler.setReceiver(this);
            }
        }else{
            mHandler.setReceiver(this);
        }

        mHandler.sendMessage(message);
    }
}
