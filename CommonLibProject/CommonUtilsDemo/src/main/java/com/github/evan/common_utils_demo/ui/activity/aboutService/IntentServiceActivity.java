package com.github.evan.common_utils_demo.ui.activity.aboutService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.event.IntentServiceAlertDialogEvent;
import com.github.evan.common_utils_demo.event.IntentServiceDemoEvent;
import com.github.evan.common_utils_demo.event.IntentServiceProgressEvent;
import com.github.evan.common_utils_demo.service.DemoIntentService;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/11.
 */

public class IntentServiceActivity extends BaseLogCatActivity {
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mDialog = new ProgressDialog(this);
        mDialog.setTitle(R.string.simulate_progress);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Subscribe
    public void onReceiveEvent(IntentServiceDemoEvent event){
        String message = event.getmMessage();
        addLog(message);
    }

    @Subscribe
    public void onReceiveProgress(IntentServiceProgressEvent event){
        int progress = event.getmProgress();
        mDialog.setProgress(progress);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveAlertEvent(IntentServiceAlertDialogEvent event){
        boolean isAlert = event.ismIsAlert();
        if(isAlert){
            mDialog.show();
        }else{
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_intent_service, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_start_intent_service})
    void onClick(View view){
        Intent intent = new Intent(this, DemoIntentService.class);
        startService(intent);
    }
}
