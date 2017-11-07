package com.github.evan.common_utils.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Evan on 2017/10/2.
 */
public class Logger {
    private static final String TAG = "Evan";
    public static final String LOG_FILE_PATH = FileUtil.getApplicationDataDir() + "/Log.txt";
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARNNING = 4;
    private static final int ERROR = 5;

    private static boolean mIsLogEnable = true;
    private static boolean mIsWrite2File = true;

    private static int mCurrentLevel = ERROR;

    public static void v(String log) {
        if (mIsLogEnable && mCurrentLevel >= VERBOSE) {
            Log.v(TAG, log);
            write2File(log);
        }
    }

    public static void d(String log) {
        if (mIsLogEnable && mCurrentLevel >= DEBUG) {
            Log.d(TAG, log);
            write2File(log);
        }
    }

    public static void i(String log) {
        if (mIsLogEnable && mCurrentLevel >= INFO) {
            Log.i(TAG, log);
            write2File(log);
        }
    }

    public static void w(String log) {
        if (mIsLogEnable && mCurrentLevel >= WARNNING) {
            Log.w(TAG, log);
            write2File(log);
        }
    }

    public static void e(String log) {
        if (mIsLogEnable && mCurrentLevel >= ERROR) {
            Log.e(TAG, log);
            write2File(log);
        }
    }

    public static void printStackTrace(Throwable e) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(e.getMessage());
        sBuilder.append("\r\n");
        StackTraceElement[] stackTrace = e.getCause().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String trace = stackTraceElement.toString();
            sBuilder.append(trace);
            sBuilder.append("\r\n");
        }
        String stacktrace = sBuilder.toString();
        Log.w(TAG, stacktrace);
        write2File(e, stacktrace);
    }

    private static void write2File(String log) {
        if (mIsWrite2File) {
            FileUtil.write2File(LOG_FILE_PATH, log, false, true);
        }
    }

    private static void write2File(Throwable e, String stacktrace) {
        String time = DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("-----↓-----" + time + "-----↓-----");
        sBuilder.append("\r\n");
        sBuilder.append("=====StackTrace=====");
        sBuilder.append("\r\n");
        sBuilder.append(stacktrace);
        sBuilder.append("=====StackTrace=====");
        sBuilder.append("\r\n");
        sBuilder.append("\r\n");
        String deviceInfo = DeviceUtil.getDeviceInfo();
        sBuilder.append("=====Device Information=====");
        sBuilder.append("\r\n");
        sBuilder.append(deviceInfo);
        sBuilder.append("\r\n");
        sBuilder.append("=====Device Information=====");
        sBuilder.append("\r\n");
        sBuilder.append("\r\n");
        sBuilder.append("=====System Information=====");
        sBuilder.append("\r\n");
        String osInfo = DeviceUtil.getOsInfo();
        sBuilder.append(osInfo);
        sBuilder.append("=====System Information=====");
        sBuilder.append("\r\n");
        sBuilder.append("-----↑-----" + time + "-----↑-----");
        sBuilder.append("\r\n");
        String log = sBuilder.toString();

        FileUtil.write2File(LOG_FILE_PATH, log, false, true);
    }
}
