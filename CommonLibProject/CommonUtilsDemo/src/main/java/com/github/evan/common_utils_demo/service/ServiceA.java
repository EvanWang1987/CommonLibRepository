package com.github.evan.common_utils_demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.evan.common_utils_demo.event.ServiceLifeCycleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/11.
 */
public class ServiceA extends Service {

    public class ServiceABinder extends Binder{
        public void printLog(){
            EventBus.getDefault().post(new ServiceLifeCycleEvent("Log from ServiceABinder....."));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onCreate()"));
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onStart()"));
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A is running..."));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onStartCommand()"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onDestroy()"));
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onBind()"));
        return new ServiceABinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onUnbind()"));
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        EventBus.getDefault().post(new ServiceLifeCycleEvent("Service A onRebind()"));
        super.onRebind(intent);
    }
}
