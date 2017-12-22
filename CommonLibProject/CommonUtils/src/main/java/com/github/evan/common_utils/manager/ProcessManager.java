package com.github.evan.common_utils.manager;

import android.app.ActivityManager;
import android.content.Context;

import com.github.evan.common_utils.BaseApplication;
import com.github.evan.common_utils.utils.UnitConvertUil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Evan on 2017/12/22.
 */

public class ProcessManager {
    private static ProcessManager mInstance = null;
    public static ProcessManager getInstance(){
        if(null == mInstance){
            synchronized (ProcessManager.class){
                if(null == mInstance){
                    mInstance = new ProcessManager();
                }
            }
        }

        return mInstance;
    }

    private ActivityManager mActivityManager;
    private ProcessManager(){
        mActivityManager = (ActivityManager) BaseApplication.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
    }

    public long getTotalRam() {
        try {
            FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            String memTotal = bufferedReader.readLine();
            StringBuffer sb = new StringBuffer();
            for (char c : memTotal.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            long totalMemory = Long.parseLong(sb.toString()) * 1024;
            return totalMemory;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long getAvailableRam(){
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public String getAvailableRamWithUnit(){
        long availableRam = getAvailableRam();
        Object[] result = new Object[2];
        UnitConvertUil.byte2MaxUnit(availableRam, 3, result);
        return result[0] + result[1].toString();
    }

    public String getTotalRamWithUnit(){
        long totalRam = getTotalRam();
        Object[] result = new Object[2];
        UnitConvertUil.byte2MaxUnit(totalRam, 3, result);
        return result[0] + result[1].toString();
    }
}
