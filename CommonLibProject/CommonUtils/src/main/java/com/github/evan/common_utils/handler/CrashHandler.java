package com.github.evan.common_utils.handler;


import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/10/4.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.printStackTrace(e);
        System.exit(0);
    }

}
