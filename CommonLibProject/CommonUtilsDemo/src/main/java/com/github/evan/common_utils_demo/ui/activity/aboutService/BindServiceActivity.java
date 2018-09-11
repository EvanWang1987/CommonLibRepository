package com.github.evan.common_utils_demo.ui.activity.aboutService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.event.ServiceLifeCycleEvent;
import com.github.evan.common_utils_demo.service.ServiceA;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/11.
 */

public class BindServiceActivity extends BaseLogCatActivity {

    private ServiceA.ServiceABinder mServiceABinder;
    private BindServiceActivity.ServiceConn mServiceConn;

    private class ServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(null != service && service instanceof ServiceA.ServiceABinder){
                mServiceABinder = (ServiceA.ServiceABinder) service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

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
        View root = inflater.inflate(R.layout.activity_bind_service, null);
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

    @OnClick({R.id.card_bind_service_a, R.id.card_unbind_service_a, R.id.card_invoke_binder})
    void onClick(View view){
        Intent intent = new Intent(this, ServiceA.class);
        switch (view.getId()){
            case R.id.card_bind_service_a:
                if(mServiceConn != null){
                    mServiceConn = null;
                    mServiceABinder = null;
                }

                mServiceConn = new BindServiceActivity.ServiceConn();
                bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
                break;

            case R.id.card_unbind_service_a:
                if(null != mServiceConn && null != mServiceABinder){
                    mServiceABinder = null;
                    unbindService(mServiceConn);
                    mServiceConn = null;
                }
                break;

            case R.id.card_invoke_binder:
                if(mServiceABinder != null){
                    mServiceABinder.printLog();
                }
                break;
        }
    }
}
