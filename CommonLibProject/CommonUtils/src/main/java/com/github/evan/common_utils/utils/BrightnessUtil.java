package com.github.evan.common_utils.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/**
 * Created by Evan on 2017/12/14.
 */
public class BrightnessUtil {


    public static int getScreenBrightness(Context context) {
        int value = 0;
        ContentResolver cr = context.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) { }
        return value;
    }

}
