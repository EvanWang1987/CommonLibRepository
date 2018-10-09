package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;

/**
 * Created by Evan on 2018/10/5.
 */

public class BitmapInfo {
    private String url;
    private long lastModifyTime;
    private String cacheDir;
    private Bitmap defaultBitmap;
    private BitmapCacherCallback mCallback;

    public BitmapInfo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public Bitmap getDefaultBitmap() {
        return defaultBitmap;
    }

    public void setDefaultBitmap(Bitmap defaultBitmap) {
        this.defaultBitmap = defaultBitmap;
    }

    public BitmapCacherCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(BitmapCacherCallback mCallback) {
        this.mCallback = mCallback;
    }
}
