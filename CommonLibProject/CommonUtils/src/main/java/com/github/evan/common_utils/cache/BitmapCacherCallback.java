package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;

/**
 * Created by Evan on 2018/10/5.
 */

public interface BitmapCacherCallback {

    public void onBitmapLoaded(String url, Bitmap bitmap);

    public void onBitmapLoadFailed(String url, Bitmap defaultBitmap);


}
