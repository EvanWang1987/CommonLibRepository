package com.github.evan.common_utils_demo.ui.activity.aboutActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.evan.common_utils.event.ActivityLifeCycleEvent;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/10.
 */

public class ActivityB extends BaseActivity {

    @OnClick({R.id.card_activity_b_finish})
    void onClick(View view){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onCreate()"));
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onStart()"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onResume()"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onPause()"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onStop()"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onRestart()"));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new ActivityLifeCycleEvent("Activity B onDestroy()"));
        super.onDestroy();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_b;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
