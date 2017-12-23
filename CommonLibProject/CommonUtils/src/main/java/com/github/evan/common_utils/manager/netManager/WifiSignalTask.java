package com.github.evan.common_utils.manager.netManager;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.github.evan.common_utils.utils.StringUtil;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Evan on 2017/12/22.
 */
public class WifiSignalTask implements Runnable {
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private LinkedList<WifiSignalObserver> mWifiSignalObservers = new LinkedList<>();
    private WifiManager mWifiManager;
    private boolean mIsStopObserve = false;
    private long mObserveInvertTime = 3 * 1000;

    public WifiSignalTask(WifiManager wifiManager) {
        this.mWifiManager = wifiManager;
    }

    public LinkedList<WifiSignalObserver> getWifiSignalObservers() {
        return mWifiSignalObservers;
    }

    public boolean isStopObserve() {
        return mIsStopObserve;
    }

    public void setStopObserve(boolean isStopObserve) {
        this.mIsStopObserve = isStopObserve;
    }

    public long getObserveInvertTime() {
        return mObserveInvertTime;
    }

    public void setObserveInvertTime(long observeInvertTime) {
        this.mObserveInvertTime = observeInvertTime;
    }

    @Override
    public void run() {
        while (!mIsStopObserve) {
            final WifiInfo connectionInfo = mWifiManager.getConnectionInfo();
            if (!StringUtil.isEmpty(connectionInfo.getBSSID())) {
                int rssi = connectionInfo.getRssi();
                final int signalLevel = WifiManager.calculateSignalLevel(rssi, 1201);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListIterator<WifiSignalObserver> iterator = mWifiSignalObservers.listIterator();
                        while (iterator.hasNext()) {
                            WifiSignalObserver next = iterator.next();
                            next.onObserveWifiSignalStrength(connectionInfo, signalLevel);
                        }
                    }
                });
                SystemClock.sleep(mObserveInvertTime);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListIterator<WifiSignalObserver> iterator = mWifiSignalObservers.listIterator();
                        while (iterator.hasNext()) {
                            WifiSignalObserver next = iterator.next();
                            next.onObserveWifiSignalStrength(null, -1);
                        }
                    }
                });
            }
        }
    }
}
