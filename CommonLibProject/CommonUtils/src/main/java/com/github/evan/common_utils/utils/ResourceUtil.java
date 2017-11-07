package com.github.evan.common_utils.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import com.Evan.CommonUtil.BaseApplication;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/10/31.
 */
public class ResourceUtil {

    /**
     * 获取资源
     * @return
     */
    public static Resources getResource(){
        return BaseApplication.getApplication().getResources();
    }

    /**
     * 获取图像
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId){
        return getResource().getDrawable(resId);
    }

    /**
     * 获取颜色
     * @param resId
     * @return
     */
    public static @ColorInt int getColor(@ColorRes int resId){
        return getResource().getColor(resId);
    }

    /**
     * 获取字符串
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId){
        return getResource().getString(resId);
    }

    /**
     * 获取字符串数组
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId){
        return getResource().getStringArray(resId);
    }

    /**
     * 获取整形
     * @param resId
     * @return
     */
    public static int getInteger(@IntegerRes int resId){
        return getResource().getInteger(resId);
    }

    /**
     * 获取整形数组
     * @param resId
     * @return
     */
    public static int[] getIntegerArray(int resId){
        return getResource().getIntArray(resId);
    }

    /**
     * 获取尺寸，最终结果转换成px，如果资源id对应得就是px，则直接返回px，不做density处理
     * @param resId
     * @return
     */
    public static float getDimenOfPixel(@DimenRes int resId){
        return getResource().getDimension(resId);
    }

    /**
     * 获取补间动画
     * @param resId
     */
    public static void getAnimation(@AnimRes int resId){
        getResource().getAnimation(resId);
    }

    /**
     * 打开Asset文件
     * @param fileFullName
     * @param mode
     * @return
     */
    public static InputStream openAssetFile(String fileFullName, int mode) {
        AssetManager assets = getResource().getAssets();
        try {
            InputStream inputStream = assets.open(fileFullName, mode);
            return inputStream;
        } catch (IOException e) {
            Logger.printStackTrace(e);
            return null;
        }
    }

    /**
     * 流的方式打开Asset文件
     * @param fileFullName
     * @return
     */
    public static InputStream openAssetFile(String fileFullName){
        return openAssetFile(fileFullName, AssetManager.ACCESS_STREAMING);
    }

    /**
     * 返回所有指定Asset目录下的文件/目录
     * @param dirFullName
     * @return
     */
    public static List<File> listAssetFile(String dirFullName){
        AssetManager assets = getResource().getAssets();
        List<File> returnValue = new ArrayList<>();
        try {
            String[] list = assets.list(dirFullName);
            for (int i = 0; i < list.length; i++) {
                File file = new File(list[i]);
                returnValue.add(file);
            }
        } catch (IOException e) {
            Logger.printStackTrace(e);
        }
        return returnValue;
    }

    /**
     * 将Asset文件复制到指定目录
     * @param assetFileName
     * @param targetPath
     * @param isDeleteIfExists
     * @return
     */
    public static FileUtil.FileStatus copyAsset2File(String assetFileName, String targetPath, boolean isDeleteIfExists){
        FileUtil.FileStatus status = FileUtil.createFile(targetPath, isDeleteIfExists);
        if(status == FileUtil.FileStatus.NOT_DELETE){
            return FileUtil.FileStatus.NOT_DELETE;
        }

        if(status == FileUtil.FileStatus.CREATE_SUCCESS){
            InputStream inputStream = openAssetFile(assetFileName);
            return FileUtil.copyFile(inputStream, targetPath, isDeleteIfExists);
        }

        return status;
    }

}
