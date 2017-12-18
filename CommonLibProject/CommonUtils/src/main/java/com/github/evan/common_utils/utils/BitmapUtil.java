package com.github.evan.common_utils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
     * 获取较小位图
     * @param bitmap
     * @return
     */
    public static Bitmap getSmallerBitmap(Bitmap bitmap){
        int size = bitmap.getWidth()*bitmap.getHeight() / 160000;
        if (size <= 1) return bitmap; // 如果小于
        else {
            Matrix matrix = new Matrix();
            matrix.postScale((float) (1 / Math.sqrt(size)), (float) (1 / Math.sqrt(size)));
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizeBitmap;
        }
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

    public static Bitmap clipBitmap(Bitmap sourceBitmap, int dstWidth, int dstHeight, boolean recycleSource){
        if(null == sourceBitmap || sourceBitmap.isRecycled()){
            return null;
        }

        int sourceHeight = sourceBitmap.getHeight();
        int sourceWidth = sourceBitmap.getWidth();

        float heightScale = (float) dstHeight / (float)sourceHeight;
        float widthScale = (float) dstHeight / (float)sourceWidth;
        if(heightScale > widthScale){
            widthScale = heightScale;
        }else if(widthScale > heightScale){
            heightScale = widthScale;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceWidth, sourceHeight, matrix, true);
        if(recycleSource){
            sourceBitmap.recycle();
        }

        return bitmap;
    }

    public static Bitmap copyBitmap(Bitmap source){
        if(null == source || source.isRecycled()){
            return null;
        }

        Bitmap copy = source.copy(source.getConfig(), false);
        return copy;
    }
}
