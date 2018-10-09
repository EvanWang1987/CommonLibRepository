package com.github.evan.common_utils_demo.ui.activity.aboutService;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.event.ServiceLifeCycleEvent;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.service.ServiceA;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/11.
 */

public class StartServiceActivity extends BaseLogCatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_start_service, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Subscribe
    public void onReceiveeMessage(ServiceLifeCycleEvent event){
        addLog(event.getmMessage());
    }

    @OnClick({R.id.card_service_start_service_a, R.id.card_stop_service_a})
    void onClick(View view){
        Intent intent = new Intent(this, ServiceA.class);
        switch (view.getId()){
            case R.id.card_service_start_service_a:
                startService(intent);
                break;

            case R.id.card_stop_service_a:
                stopService(intent);
                break;
        }
    }
}
