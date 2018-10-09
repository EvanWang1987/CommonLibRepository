package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

/**
 * Created by Evan on 2018/10/5.
 */

public interface IBitmapCacher {

    public Bitmap getBitmap(String url, BitmapCacherCallback callback);

    public boolean clearCacheDir();

    public boolean clearMemoryCache();

    public void setUnifiedDefaultBitmap(@DrawableRes int id);

    public void setUnifiedDefaultBitmap(String drawablePath);
}
