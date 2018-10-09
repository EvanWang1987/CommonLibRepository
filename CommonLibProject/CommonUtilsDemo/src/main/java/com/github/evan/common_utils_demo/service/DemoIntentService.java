package com.github.evan.common_utils_demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.github.evan.common_utils.event.IntentServiceAlertDialogEvent;
import com.github.evan.common_utils.event.IntentServiceDemoEvent;
import com.github.evan.common_utils.event.IntentServiceProgressEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/11.
 */

public class DemoIntentService extends IntentService {

    public DemoIntentService() {
        super("DemoIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onCreate");
        EventBus.getDefault().post(event);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onStart");
        EventBus.getDefault().post(event);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onStartCommand");
        EventBus.getDefault().post(event);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onBind");
        EventBus.getDefault().post(event);
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onUnbind");
        EventBus.getDefault().post(event);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onDestroy");
        EventBus.getDefault().post(event);
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        IntentServiceDemoEvent event = new IntentServiceDemoEvent("DemoIntentService onHandleIntent current thread ID: " + Thread.currentThread().getId());
        EventBus.getDefault().post(event);
        int max = 100;
        int i = 1;
        EventBus.getDefault().post(new IntentServiceAlertDialogEvent(true));
        while (i < max){
            IntentServiceDemoEvent messageEvent = new IntentServiceDemoEvent("Log from DemoIntentService...");
            IntentServiceProgressEvent progressEvent = new IntentServiceProgressEvent(i);
            EventBus.getDefault().post(messageEvent);
            EventBus.getDefault().post(progressEvent);
            SystemClock.sleep(100);
            i++;
        }
        EventBus.getDefault().post(new IntentServiceAlertDialogEvent(false));
    }
}
