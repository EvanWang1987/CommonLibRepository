package com.github.evan.common_utils.manager.netManager;

import android.net.wifi.WifiInfo;

/**
 * Created by Evan on 2017/12/22.
 */
public interface WifiSignalObserver {

    void onObserveWifiSignalStrength(WifiInfo wifiInfo, int signalStrengthLevel);

}
