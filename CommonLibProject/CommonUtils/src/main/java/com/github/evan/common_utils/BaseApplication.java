package com.github.evan.common_utils;

import android.app.Application;
import android.content.Context;

import com.github.evan.common_utils.handler.CrashHandler;


/**
 * Created by Evan on 2017/10/4.
 */

public class BaseApplication extends Application{
    private static Context mApplication;
    public static Context getApplication(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
    }
}
