package com.github.evan.common_utils.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.github.evan.common_utils.handler.SoftHandler;
import com.github.evan.common_utils.handler.SoftHandlerReceiver;
import com.github.evan.common_utils.ui.fragment.BaseFragment;

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

    public void loadActivity(Class<? extends Activity> activity){
        loadActivity(activity, null, false, -1);
    }

    public void loadActivity(Class<? extends Activity> destination, Bundle extras, boolean isForResult, int requestCode){
        Intent intent = new Intent(this, destination);
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

    public boolean postDelay(Runnable runnable, long delay){
        return mHandler.postDelayed(runnable, delay < 0 ? 0 : delay);
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
            BaseActivity receiver = mHandler.getReceiver();
            if(receiver.hashCode() != this.hashCode()){
                mHandler.clearReceiver();
                mHandler.setReceiver(this);
            }
        }else{
            mHandler.setReceiver(this);
        }

        mHandler.sendMessage(message);
    }

    public boolean isForegroundActivity(){
        return mActivities.getFirst().hashCode() == this.hashCode();
    }

    public boolean isDestoried() {
        return mIsDestroyed;
    }
}
