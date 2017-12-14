package com.github.evan.common_utils.ui.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.github.evan.common_utils.R;

/**
 * Created by Evan on 2017/12/14.
 */
public class BatteryView extends AppCompatImageView {
    public static final int STATUS_BATTERY_CHARGING = 0;
    public static final int STATUS_BATTERY_CHARGING_FULL = 1;
    public static final int STATUS_BATTERY_TEN_PERCENT = 10;
    public static final int STATUS_BATTERY_TWENTY_PERCENT = 20;
    public static final int STATUS_BATTERY_FIFTY_PERCENT = 50;
    public static final int STATUS_BATTERY_EIGHTY_PERCENT = 80;
    public static final int STATUS_BATTERY_ONE_HUNDRED_PERCENT = 100;
    private BatteryManager mBatteryManager;
    private PowerManager mPowerManager;

    private class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            int level = extras.getInt(BatteryManager.EXTRA_LEVEL, 0);
            boolean isCharging = false;
            int status = extras.getInt(BatteryManager.EXTRA_STATUS);
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    isCharging = true;
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    break;
                default:
                    break;
            }

            refreshBattery(isCharging, level);
        }
    }

    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private boolean isMonitoringBattery = false;

    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBatteryManager = (BatteryManager) getContext().getSystemService(Context.BATTERY_SERVICE);
        mPowerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
    }

    public void startMonitoringBattery() {
        if (!isMonitoringBattery) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
            intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
            getContext().registerReceiver(mBatteryReceiver, intentFilter);
        }
    }

    public void stopMonitoringBattery() {
        if (isMonitoringBattery) {
            getContext().unregisterReceiver(mBatteryReceiver);
        }
    }

    public void refreshBattery(boolean isCharging, int batteryLevel) {
        if (isCharging) {
            this.setImageResource(R.mipmap.battery_charging);
        } else {
            if (batteryLevel >= 100) {
                this.setImageResource(R.mipmap.battery_full);
            } else if (batteryLevel >= 80 && batteryLevel < 100) {
                this.setImageResource(R.mipmap.battery_80);
            } else if (batteryLevel >= 50 && batteryLevel < 80) {
                this.setImageResource(R.mipmap.battery_50);
            } else if (batteryLevel >= 20 && batteryLevel < 50) {
                this.setImageResource(R.mipmap.battery_20);
            } else {
                this.setImageResource(R.mipmap.battery_10);
            }
        }
    }
}
