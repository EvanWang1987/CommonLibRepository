package com.github.evan.common_utils.utils;

import com.github.evan.common_utils.bean.BatteryInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evan on 2017/12/14.
 */
public class BatteryUtil {
    public static final String CURRENT_BATTERY_SERVICE_STATE = "Current Battery Service state";
    public static final String AC_POWERED = "AC powered";
    public static final String USB_POWERED = "USB powered";
    public static final String WIRELESS_POWERED = "Wireless powered";
    public static final String MAX_CHARGING_CURRENT = "Max charging current";
    public static final String STATUS = "status";
    public static final String HEALTH = "health";
    public static final String PRESENT = "present";
    public static final String LEVEL = "level";
    public static final String SCALE = "scale";
    public static final String VOLTAGE = "voltage";
    public static final String TEMPERATURE = "temperature";
    public static final String TECHNOLOGY = "technology";

    public static BatteryInfo getBatteryInfo() {
        InputStream inputStream = CommandLineUtil.execCmdFromInputStream("adb shell dumpsys battery");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Map<String, Object> map = new HashMap<>();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                boolean isContains = line.contains(":");
                if (isContains) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1, line.length()).trim();
                    if (key.equalsIgnoreCase(CURRENT_BATTERY_SERVICE_STATE)) {
                        continue;
                    } else if (key.equalsIgnoreCase(AC_POWERED) || key.equalsIgnoreCase(USB_POWERED) || key.equalsIgnoreCase(WIRELESS_POWERED) || key.equalsIgnoreCase(PRESENT)) {
                        map.put(key, Boolean.valueOf(value));
                    } else if (key.equalsIgnoreCase(HEALTH) || key.equalsIgnoreCase(LEVEL) || key.equalsIgnoreCase(SCALE) || key.equalsIgnoreCase(STATUS) || key.equalsIgnoreCase(TEMPERATURE) || key.equalsIgnoreCase(VOLTAGE)) {
                        map.put(key, Integer.valueOf(value));
                    } else {
                        map.put(key, value);
                    }
                }
            }
            String jsonString = GsonUtil.getInstance().toJsonWithObject(map);
            BatteryInfo batteryInfo = GsonUtil.getInstance().fromJson(jsonString, BatteryInfo.class);
            return batteryInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isCharging() {
        InputStream inputStream = CommandLineUtil.execCmdFromInputStream("adb shell dumpsys battery");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                boolean isContains = line.contains(":");
                if (isContains) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1, line.length()).trim();
                    if (key.equalsIgnoreCase(AC_POWERED) || key.equalsIgnoreCase(USB_POWERED) || key.equalsIgnoreCase(WIRELESS_POWERED)) {
                        boolean isCharging = Boolean.valueOf(value);
                        if (isCharging) {
                            return isCharging;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getBatteryLevel() {
        InputStream inputStream = CommandLineUtil.execCmdFromInputStream("adb shell dumpsys battery");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                boolean isContains = line.contains(":");
                if (isContains) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1, line.length()).trim();
                    if (key.equalsIgnoreCase(LEVEL)) {
                        int level = Integer.parseInt(value);
                        return level;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getBatteryTemperature() {
        InputStream inputStream = CommandLineUtil.execCmdFromInputStream("adb shell dumpsys battery");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                boolean isContains = line.contains(":");
                if (isContains) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1, line.length()).trim();
                    if (key.equalsIgnoreCase(TEMPERATURE)) {
                        int level = Integer.parseInt(value);
                        return level;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
