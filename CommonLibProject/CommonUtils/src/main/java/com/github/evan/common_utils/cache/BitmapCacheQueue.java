package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Evan on 2018/10/5.
 */

public class BitmapCacheQueue extends LruCache<String, Bitmap> {

    public BitmapCacheQueue(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes();
    }


}
