package com.github.evan.common_utils_demo.ui.fragment;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.manager.netManager.NetManager;
import com.github.evan.common_utils.manager.netManager.WifiSignalObserver;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.deskIcon.icons.WifiSignalLevelIcon;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/22.
 */
public class NetworkManagerFragment extends BaseFragment implements WifiSignalObserver {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_network_manager, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            boolean observeWifiSignalStrength = NetManager.getInstance(getContext()).isObserveWifiSignalStrength();
            if (observeWifiSignalStrength) {
                NetManager.getInstance(getContext()).shutDownObserveWifiSignal();
            }
            onClick(getView().findViewById(R.id.card_stop_observe_wifi_signal));
        }
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_start_observe_wifi_signal, R.id.card_stop_observe_wifi_signal})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_start_observe_wifi_signal:
                NetManager.getInstance(getContext()).startObserveWifiSignalStrength(this);
                DeskIconManager.getInstance(getContext()).showWifiSignalIcon();
                DeskIconManager.getInstance(getContext()).showLogCatIcon();
                break;

            case R.id.card_stop_observe_wifi_signal:
                try {
                    NetManager.getInstance(getContext()).shutDownObserveWifiSignal();
                    DeskIconManager.getInstance(getContext()).dismissWifiSignalIcon();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onObserveWifiSignalStrength(WifiInfo wifiInfo, int signalStrengthLevel) {
        if (null == wifiInfo || signalStrengthLevel == -1) {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_NONE);
            DeskIconManager.getInstance(getContext()).addLog("没有链接到Wifi网络,或者没有信号");
            return;
        }
        String ssId = wifiInfo.getSSID();
        String s = "Wifi ssId: " + ssId + ", 信号强度: " + signalStrengthLevel;
        DeskIconManager.getInstance(getContext()).addLog(s);
        if (signalStrengthLevel >= 0 && signalStrengthLevel < 300) {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_MIN);
        } else if (signalStrengthLevel >= 300 && signalStrengthLevel < 600) {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_MIDDLE);
        } else if (signalStrengthLevel >= 600 && signalStrengthLevel < 900) {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_STRONGER);
        } else if (signalStrengthLevel >= 900 && signalStrengthLevel <= 1200) {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_MAX);
        } else {
            DeskIconManager.getInstance(getContext()).setWifiSignalIconStatus(WifiSignalLevelIcon.WIFI_SIGNAL_NONE);
        }
    }

}
