package com.github.evan.common_utils.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.SoftReference;

/**
 * Created by Evan on 2017/10/2.
 */
public class SoftHandler<Receiver extends SoftHandlerReceiver> extends Handler {
    private SoftReference<Receiver> mReceiver;

    public SoftHandler() {
    }

    public SoftHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        if(null != mReceiver){
            final Receiver receiver = mReceiver.get();
            if(null != receiver){
                receiver.onHandleMessage(msg);
            }
        }
    }

    public void setReceiver(Receiver receiver){
        clearReceiver();
        mReceiver = new SoftReference<>(receiver);
    }

    public boolean isReceiverExists(){
        return null != mReceiver && null != mReceiver.get();
    }

    public void clearReceiver(){
        if(null != mReceiver){
            mReceiver.clear();
            mReceiver = null;
        }
    }

}
