package com.github.evan.common_utils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

/**
 * Created by Evan on 2017/10/2.
 */
public class BitmapUtil {

    /**
     * 释放位图
     * @param bitmap
     * @return
     */
    public static boolean recycleBitmap(Bitmap bitmap){
        boolean isRecyclable = null != bitmap && !bitmap.isRecycled();
        if(isRecyclable){
            bitmap.recycle();
        }
        return isRecyclable;
    }

    /**
     * 获取位图
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap createBitmapFromResource(Context context, int resId){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        }catch (OutOfMemoryError e){
            Logger.printStackTrace(e);
        }
        return bitmap;
    }
}
