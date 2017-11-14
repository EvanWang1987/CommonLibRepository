package com.github.evan.common_utils.ui.fragment;


import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;

import com.github.evan.common_utils.handler.SoftHandler;
import com.github.evan.common_utils.handler.SoftHandlerReceiver;

/**
 * Created by Evan on 2017/11/4.
 */
public class BaseFragment extends android.support.v4.app.Fragment implements SoftHandlerReceiver {
    private SoftHandler<BaseFragment> mHandler = new SoftHandler<>(Looper.getMainLooper());
    private LayoutInflater mLayoutInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onDestroyView() {
        mHandler.clearReceiver();
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Override
    public void onHandleMessage(Message message) {

    }

    protected SoftHandler getHandler(){
        return mHandler;
    }

    protected boolean postDelay(Runnable runnable, long delayTime){
        return mHandler.postDelayed(runnable, delayTime);
    }
}
