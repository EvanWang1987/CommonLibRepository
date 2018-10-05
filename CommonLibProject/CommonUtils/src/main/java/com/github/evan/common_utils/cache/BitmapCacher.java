package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import com.github.evan.common_utils.manager.threadManager.ThreadFactory;
import com.github.evan.common_utils.utils.FileUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2018/10/5.
 */

public class BitmapCacher implements IBitmapCacher {
    public static BitmapCacher getInstance(){
        if(null == mInstance){
            synchronized (BitmapCacher.class){
                if(null == mInstance){
                    mInstance = new BitmapCacher();
                }
            }
        }

        return mInstance;
    }


    private static final String CACHE_DIR = FileUtil.getApplicationDataDir() + "/";
    ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(5, 15, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ThreadFactory("BitmapCacher Thread Pool"), new ThreadPoolExecutor.AbortPolicy());
    private static Bitmap mDefaultBitmap;
    private static BitmapCacher mInstance;
    private BitmapCacheQueue mBitmapCacheQueue;

    private BitmapCacher() {
        long max = Runtime.getRuntime().maxMemory() / 8;
        mBitmapCacheQueue = new BitmapCacheQueue((int)max);
    }

    @Override
    public Bitmap getBitmap(String url, BitmapCacherCallback callback) {
        String fileName = URLDecoder.decode(url);
        Bitmap cachedBitmap = null;
        //1. 先查内存缓存
        cachedBitmap = mBitmapCacheQueue.get(fileName);
        if(null != cachedBitmap && !cachedBitmap.isRecycled()){
            return cachedBitmap;
        }

        //2. 再查本地缓存
        File file = new File(CACHE_DIR, URLDecoder.decode(url));
        if(file.exists()){
            cachedBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if(null != cachedBitmap && !cachedBitmap.isRecycled()){
                return cachedBitmap;
            }
        }

        BitmapInfo bitmapInfo = new BitmapInfo();
        bitmapInfo.setUrl(url);
        bitmapInfo.setCacheDir(CACHE_DIR);
        bitmapInfo.setDefaultBitmap(mDefaultBitmap);
        bitmapInfo.setLastModifyTime(file.exists() ? file.lastModified() : -1);
        bitmapInfo.setmCallback(callback);
        BitmapCacherTask task = new BitmapCacherTask(bitmapInfo);
        mThreadPool.submit(task);
        return null;
    }

    @Override
    public boolean clearCacheDir() {
        List<File> files = FileUtil.clearDir(CACHE_DIR);
        return null == files || files.size() == 0;
    }

    @Override
    public boolean clearMemoryCache() {
        mBitmapCacheQueue.evictAll();
        return true;
    }

    @Override
    public void setUnifiedDefaultBitmap(@DrawableRes int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mDefaultBitmap = BitmapFactory.decodeResource(ResourceUtil.getResource(), id, options);
    }

    @Override
    public void setUnifiedDefaultBitmap(String drawablePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        mDefaultBitmap = BitmapFactory.decodeFile(drawablePath, options);
    }
}
