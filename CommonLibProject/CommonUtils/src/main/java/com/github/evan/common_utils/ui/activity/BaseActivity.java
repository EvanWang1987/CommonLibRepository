package com.github.evan.common_utils.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.github.evan.common_utils.handler.SoftHandler;
import com.github.evan.common_utils.handler.SoftHandlerReceiver;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils.utils.ToastUtil;

import java.util.LinkedList;

/**
 * Created by Evan on 2017/11/3.
 */
public abstract class BaseActivity extends AppCompatActivity implements SoftHandlerReceiver {
    protected static final int IDLE = 0;
    protected static final int UNKNOW_ERROR = -1;
    protected static final int LOAD_COMPLETE = 1;
    protected static final int LOAD_MORE_COMPLETE = 2;
    protected static final int NET_UNAVAILABLE = 3;
    protected static final int NET_TIME_OUT = 4;
    protected static final int LOAD_EMPTY = 5;
    protected static final int LOAD_ERROR = 6;

    public abstract @LayoutRes int getLayoutResId();
    public abstract BaseActivityConfig onCreateActivityConfig();

    private LayoutInflater mLayoutInflater;
    private SoftHandler<BaseActivity> mHandler = new SoftHandler<>(Looper.getMainLooper());
    private boolean mIsDestroyed = false;
    private static LinkedList<BaseActivity> mActivities = new LinkedList<>();
    private BaseActivityConfig mActivityConfig;
    private long mLastBackPressedTime = -1;
    private AlertDialog mMessageDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        init();
    }

    public void showMessageDialog(CharSequence title, CharSequence message, CharSequence okMessage, CharSequence cancelMessage){
        if(!StringUtil.isEmpty(title)){
            mMessageDialog.setTitle(title);
        }

        if(!StringUtil.isEmpty(message)){
            mMessageDialog.setMessage(message);
        }

        if(!StringUtil.isEmpty(okMessage)){
            mMessageDialog.setButton(AlertDialog.BUTTON_POSITIVE, okMessage, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        if(!StringUtil.isEmpty(cancelMessage)){
            mMessageDialog.setButton(AlertDialog.BUTTON_NEGATIVE, cancelMessage, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        if(!mMessageDialog.isShowing()){
            mMessageDialog.show();
        }
    }

    public void dismissMessageDialog(){
        if(mMessageDialog.isShowing()){
            mMessageDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if(mActivityConfig.isPressTwiceToExit){
            long currentTimeMillis = System.currentTimeMillis();
            if(mLastBackPressedTime == -1 || currentTimeMillis - mLastBackPressedTime > mActivityConfig.pressTwiceIntervalTime){
                ToastUtil.showToastWithShortDuration(mActivityConfig.pressTwiceIntervalNotifyText);
                mLastBackPressedTime = currentTimeMillis;
                return;
            }

            super.onBackPressed();
        }
        super.onBackPressed();
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
        mActivityConfig = onCreateActivityConfig();
        mMessageDialog = DialogFactory.createDesignMessageDialog(this, -1, "", "");
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public void loadActivity(Class<? extends Activity> activity) {
        loadActivity(activity, null, false, -1);
    }

    public void loadActivity(Class<? extends Activity> destination, Bundle extras, boolean isForResult, int requestCode) {
        Intent intent = new Intent(this, destination);
        if (null != extras) {
            intent.putExtras(extras);
        }

        if (isForResult) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }

    public void loadActivity(String action, Bundle extras, boolean isForResult, int requestCode) {
        Intent intent = new Intent(action);
        if (null != extras) {
            intent.putExtras(extras);
        }

        if (isForResult) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }

    public boolean postDelay(Runnable runnable, long delay) {
        return mHandler.postDelayed(runnable, delay < 0 ? 0 : delay);
    }

    protected void sendEmptyMessage(int what) {
        sendMessage(what, -1, -1, null, null, -1);
    }

    protected void sendEmptyMessageDelay(int what, long delay) {
        sendMessage(what, -1, -1, null, null, delay);
    }

    protected void sendMessage(int what, int arg1, int arg2, Object object, Bundle bundle, long delay) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.obj = object;
        message.setData(bundle);
        boolean receiverExists = mHandler.isReceiverExists();
        if (receiverExists) {
            BaseActivity receiver = mHandler.getReceiver();
            if (receiver.hashCode() != this.hashCode()) {
                mHandler.clearReceiver();
                mHandler.setReceiver(this);
            }
        } else {
            mHandler.setReceiver(this);
        }

        if (delay <= 0) {
            mHandler.sendMessage(message);
        } else {
            mHandler.sendMessageDelayed(message, delay);
        }
    }

    public boolean isForegroundActivity() {
        return mActivities.getFirst().hashCode() == this.hashCode();
    }

    public boolean isDestoried() {
        return mIsDestroyed;
    }
}
