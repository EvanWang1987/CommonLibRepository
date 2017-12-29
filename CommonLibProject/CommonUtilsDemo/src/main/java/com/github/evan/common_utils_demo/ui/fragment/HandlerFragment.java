package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/28.
 */

public class HandlerFragment extends BaseFragment {
    public static final int SEND_MESSAGE_WITH_WHAT = 1;
    public static final int SEND_MESSAGE_WITH_ARG1_ARG2 = 2;
    public static final int SEND_MESSAGE_WITH_OBJECT = 3;


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            int arg1 = msg.arg1;
            int arg2 = msg.arg2;
            Object obj = msg.obj;

            if (what == 0) {
                DeskIconManager.getInstance(getContext()).addLog("UI线程" + Thread.currentThread().getId() + "接收到空消息");
            } else {
                if(what == SEND_MESSAGE_WITH_WHAT){
                    DeskIconManager.getInstance(getContext()).addLog("UI线程" + Thread.currentThread().getId() + "接收到携带what的消息, what= " + what);
                }else if(what == SEND_MESSAGE_WITH_ARG1_ARG2){
                    DeskIconManager.getInstance(getContext()).addLog("UI线程" + Thread.currentThread().getId() + "接收到携带arg1 & arg2的消息, arg1= " + arg1 + ", arg2= " + arg2);
                }else if(what == SEND_MESSAGE_WITH_OBJECT){
                    DeskIconManager.getInstance(getContext()).addLog("UI线程" + Thread.currentThread().getId() + "接收到携带Object的消息, object= " + obj);
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_handler, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            mHandler.removeCallbacksAndMessages(null);
            DeskIconManager.getInstance(getContext()).dismissLogCatIcon();
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_send_empty_message_to_ui_thread, R.id.card_send_message_with_what_to_ui_thread, R.id.card_send_message_with_args1_args2, R.id.card_send_message_with_object_to_ui_thread, R.id.card_send_message_with_runnable_to_ui_thread})
    void onClick(View view) {
        DeskIconManager.getInstance(getContext()).showLogCatIcon();
        int clickedViewId = view.getId();
        switch (clickedViewId) {
            case R.id.card_send_empty_message_to_ui_thread:
                new Thread() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        DeskIconManager.getInstance(getContext()).addLog("线程" + Thread.currentThread().getId() + "向UI线程发送一个空消息");
                        mHandler.sendMessage(message);
                    }
                }.start();
                break;

            case R.id.card_send_message_with_what_to_ui_thread:
                new Thread() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.what = SEND_MESSAGE_WITH_WHAT;
                        DeskIconManager.getInstance(getContext()).addLog("线程" + Thread.currentThread().getId() + "向UI线程发送一个携带what的消息 what: " + message.what);
                        mHandler.sendMessage(message);
                    }
                }.start();
                break;

            case R.id.card_send_message_with_args1_args2:
                new Thread() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.what = SEND_MESSAGE_WITH_ARG1_ARG2;
                        message.arg1 = 0;
                        message.arg2 = 1;
                        DeskIconManager.getInstance(getContext()).addLog("线程" + Thread.currentThread().getId() + "向UI线程发送一个携带arg1 & arg2的消息, arg1: " + message.arg1 + ", arg2: " + message.arg2);
                        mHandler.sendMessage(message);
                    }
                }.start();
                break;

            case R.id.card_send_message_with_object_to_ui_thread:
                new Thread() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.what = SEND_MESSAGE_WITH_OBJECT;
                        message.obj = "Demo String";
                        DeskIconManager.getInstance(getContext()).addLog("线程" + Thread.currentThread().getId() + "向UI线程发送一个携带Object的消息, , object: " + message.obj.toString());
                        mHandler.sendMessage(message);
                    }
                }.start();
                break;

            case R.id.card_send_message_with_runnable_to_ui_thread:
                new Thread() {
                    @Override
                    public void run() {
                        DeskIconManager.getInstance(getContext()).addLog("线程" + Thread.currentThread().getId() + "向UI线程发送一个携带Runnable的消息");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                DeskIconManager.getInstance(getContext()).addLog("运行Runnable");
                                DeskIconManager.getInstance(getContext()).addLog("UI线程运行了这个Runnable");
                            }
                        });
                    }
                }.start();
                break;
        }


    }
}
