package com.github.evan.common_utils.utils;


import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.Evan.CommonUtil.BaseApplication;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Evan on 2017/10/1.
 */
public class DensityUtil {

    /**
     * 获取设备屏幕真实宽度
     * @return
     */
    public static int getRealScreenWidthOfPx() {
        int width = 0;
        WindowManager windowManager = (WindowManager) BaseApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        try {
            Display display = windowManager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Point point = new Point();
                display.getRealSize(point);
                width = point.x;
            } else {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                width = (int)display.getClass().getMethod("getRawWidth").invoke(displayMetrics);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return width;
    }

    /**
     * 获取设备屏幕真实高度
     * @return
     */
    public static int getRealScreenHeightOfPx() {
        int height = 0;
        WindowManager windowManager = (WindowManager) BaseApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        try {
            Display display = windowManager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Point point = new Point();
                display.getRealSize(point);
                height = point.y;
            } else {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                height = (int)display.getClass().getMethod("getRawHeight").invoke(displayMetrics);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return height;
    }

    /**
     * 获取App宽 单位像素
     *
     * @return
     */
    public static int getAppScreenWidthOfPx() {
        return ResourceUtil.getResource().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取App高 单位像素
     *
     * @return
     */
    public static int getAppScreenHeightOfPx() {
        return ResourceUtil.getResource().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取X轴DPI
     *
     * @return
     */
    public static float getXDpi() {
        return ResourceUtil.getResource().getDisplayMetrics().xdpi;
    }

    /**
     * 获取Y轴DPI
     *
     * @return
     */
    public static float getYDpi() {
        return ResourceUtil.getResource().getDisplayMetrics().ydpi;
    }

    /**
     * 获取密度比例
     *
     * @return
     */
    public static float getDensity() {
        return ResourceUtil.getResource().getDisplayMetrics().density;
    }

    /**
     * 获取字体密度比例
     *
     * @return
     */
    public static float getScaleDensity() {
        return ResourceUtil.getResource().getDisplayMetrics().scaledDensity;
    }

    /**
     * dp转px
     *
     * @param value
     * @return
     */
    public static int dp2px(float value) {
        float density = getDensity();
        return (int) (value * density + 0.5f);
    }

    /**
     * px转dp
     *
     * @param value
     * @return
     */
    public static int px2dp(float value) {
        float density = getDensity();
        return (int) (value / density + 0.5f);
    }

    /**
     * sp转px, 根据scaleDensity转换,也会受系统设置中的字体大小影响
     *
     * @param value
     * @return
     */
    public static int sp2px(float value) {
        float scaleDensity = getScaleDensity();
        return (int) (value * scaleDensity + 0.5f);
    }

    /**
     * px转sp, 根据scaleDensity转换,也会受系统设置中的字体大小影响
     *
     * @param value
     * @return
     */
    public static int px2sp(float value) {
        float scaleDensity = getScaleDensity();
        return (int) (value / scaleDensity + 0.5f);
    }

    /**
     * 获取分辨率
     *
     * @return
     */
    public static String getResolutionString() {
        return getRealScreenWidthOfPx() + " * " + getRealScreenHeightOfPx();
    }
}
