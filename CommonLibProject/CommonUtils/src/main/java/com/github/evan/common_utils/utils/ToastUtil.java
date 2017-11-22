package com.github.evan.common_utils.utils;

import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;
import com.github.evan.common_utils.BaseApplication;

/**
 * Created by Evan on 2017/11/19.
 */
public class ToastUtil {

    public static void showToastWithShortDuration(String message){
        Toast.makeText(BaseApplication.getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastWithLongDuration(String message){
        Toast.makeText(BaseApplication.getApplication(), message, Toast.LENGTH_LONG).show();
    }

    public static void showToastWithShortDuration(@StringRes int message){
        Toast.makeText(BaseApplication.getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastWithLongDuration(@StringRes int message){
        Toast.makeText(BaseApplication.getApplication(), message, Toast.LENGTH_LONG).show();
    }

    public static void showToastWithShortDuation(String message, View view){
        showToast(message, Toast.LENGTH_SHORT, -1, -1, -1, view, -1, -1);
    }

    public static void showToastWithLongDuation(String message, View view){
        showToast(message, Toast.LENGTH_LONG, -1, -1, -1, view, -1, -1);
    }

    public static void showToastWithShortDuration(@StringRes int message, View view){
        showToast(ResourceUtil.getString(message), Toast.LENGTH_SHORT, -1, -1, -1, view, -1, -1);
    }

    public static void showToastWithLongDuration(@StringRes int message, View view){
        showToast(ResourceUtil.getString(message), Toast.LENGTH_LONG, -1, -1, -1, view, -1, -1);
    }

    public static void showToast(String message, int duration, int gravity, int x, int y, View view, int horizontalMargin, int verticalMargin){
        Toast toast = null;
        toast = Toast.makeText(BaseApplication.getApplication(), message, duration);

        if(x > 0 && y > 0){
            toast.setGravity(gravity, x, y);
        }

        if(null != view){
            toast.setView(view);
        }

        if(horizontalMargin > 0 && verticalMargin > 0){
            toast.setMargin(horizontalMargin, verticalMargin);
        }

        toast.show();
    }


}
