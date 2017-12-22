package com.github.evan.common_utils.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.github.evan.common_utils.BaseApplication;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by Evan on 2017/10/4.
 */

public class DeviceUtil {

    /**
     * 获取CPU核心数
     *
     * @return
     */
    public static int getNumberOfCPUCores() {
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = -1;
        } catch (NullPointerException e) {
            cores = -1;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 获取设备硬件信息
     *
     * @return
     */
    public static String getDeviceInfo() {
        StringBuilder sBuilder = new StringBuilder();

        String board = Build.BOARD;
        String bootloader = Build.BOOTLOADER;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String display = Build.DISPLAY;
        String fingerprint = Build.FINGERPRINT;
        String hardware = Build.HARDWARE;
        String host = Build.HOST;
        String id = Build.ID;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String tags = Build.TAGS;
        long time = Build.TIME;
        String type = Build.TYPE;
        String unknown = Build.UNKNOWN;
        String user = Build.USER;
        String radioVersion = Build.getRadioVersion();
        String serial = Build.SERIAL;

        sBuilder.append("board: " + board);
        sBuilder.append("\r\n");
        sBuilder.append("bootloader: " + bootloader);
        sBuilder.append("\r\n");
        sBuilder.append("brand: " + brand);
        sBuilder.append("device: " + device);
        sBuilder.append("\r\n");
        sBuilder.append("display: " + display);
        sBuilder.append("\r\n");
        sBuilder.append("fingerprint: " + fingerprint);
        sBuilder.append("\r\n");
        sBuilder.append("hardware: " + hardware);
        sBuilder.append("\r\n");
        sBuilder.append("host: " + host);
        sBuilder.append("\r\n");
        sBuilder.append("id: " + id);
        sBuilder.append("\r\n");
        sBuilder.append("manufacturer: " + manufacturer);
        sBuilder.append("\r\n");
        sBuilder.append("model: " + model);
        sBuilder.append("\r\n");
        sBuilder.append("product: " + product);
        sBuilder.append("\r\n");
        sBuilder.append("tags: " + tags);
        sBuilder.append("\r\n");
        sBuilder.append("time: " + time);
        sBuilder.append("\r\n");
        sBuilder.append("type: " + type);
        sBuilder.append("\r\n");
        sBuilder.append("unknown: " + unknown);
        sBuilder.append("\r\n");
        sBuilder.append("user: " + user);
        sBuilder.append("\r\n");
        sBuilder.append("radioVersion: " + radioVersion);
        sBuilder.append("\r\n");
        sBuilder.append("serial: " + serial);
        return sBuilder.toString();
    }

    public static String getOsInfo() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("System: Android");
        sBuilder.append("\r\n");
        sBuilder.append("System Version Name: " + getOsCodeBuildVersionName());
        sBuilder.append("\r\n");
        sBuilder.append("System Version Code: " + getOsVersionCode());
        sBuilder.append("\r\n");
        sBuilder.append("System API Level: " + getApiLevel());
        sBuilder.append("\r\n");
        return sBuilder.toString();
    }

    /**
     * 获取系统代码编译版本
     *
     * @return
     */
    public static String getOsCodeBuildVersionName() {
        String sdk = Build.VERSION.INCREMENTAL;
        return sdk;
    }

    /**
     * 获取设备系统版本号
     *
     * @return
     */
    public static String getOsVersionCode() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取API版本号
     *
     * @return
     */

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备制造商
     *
     * @return
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取IMEI
     *
     * @return
     */
    public static String getIMEI() {
        Context application = BaseApplication.getApplication();
        TelephonyManager telephonyManager = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }

    public static String getRadioVersion() {
        return Build.getRadioVersion();
    }


}
