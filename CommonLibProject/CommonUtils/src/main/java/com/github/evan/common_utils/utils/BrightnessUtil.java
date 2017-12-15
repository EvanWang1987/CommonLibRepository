package com.github.evan.common_utils.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.view.WindowManager;

/**
 * Created by Evan on 2017/12/14.
 */
public class BrightnessUtil {

    /**
     * 获取设置亮度
     *
     * @param context
     * @return
     */
    public static int getSystemBrightness(Context context) {
        int value = 0;
        ContentResolver cr = context.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
        }
        return value;
    }

    /**
     * 是否设置了自动调解亮度
     *
     * @param context
     * @return
     */
    public static boolean isAutoBrightness(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        boolean autoMicBrightness = false;
        try {
            autoMicBrightness = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoMicBrightness;
    }

    /**
     * 关闭自动亮度调节
     *
     * @param context
     */
    public static void closeAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启自动亮度调节
     *
     * @param context
     */
    public static void openAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 设置Activity亮度
     *
     * @param activity
     * @param brightness
     */
    public static void setActivityBrightness(Activity activity, @FloatRange(from = 0f, to = 1f) float brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = brightness;
        activity.getWindow().setAttributes(lp);
    }

    public static float getActivityBrightness(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        return lp.screenBrightness;
    }

    /**
     * 保存设置亮度
     *
     * @param brightness
     */
    public static void saveBrightness(Context context, int brightness) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = android.provider.Settings.System
                .getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness",
                brightness);
        resolver.notifyChange(uri, null);
    }

}
