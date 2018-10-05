package com.github.evan.common_utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import com.github.evan.common_utils.manager.okHttpManager.OkHttpManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evan on 2018/10/5.
 */

public class BitmapCacherTask implements Runnable {
    private static final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private BitmapInfo mBitmapInfo;
    private boolean mIsRequestSuccess;
    private Bitmap mBitmap;

    public BitmapCacherTask(BitmapInfo mBitmapInfo) {
        this.mBitmapInfo = mBitmapInfo;
    }

    @Override
    public void run() {
        //下载图片
        Request getRequest = OkHttpManager.getInstance().getGetRequest(mBitmapInfo.getUrl(), null);
        try {
            Response response = OkHttpManager.getInstance().getHttpClient().newCall(getRequest).execute();
            if(response.isSuccessful()){
                mIsRequestSuccess = true;
                if(response.code() == HttpURLConnection.HTTP_OK){
                    byte[] bytes = response.body().bytes();
                    mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File(mBitmapInfo.getCacheDir(), URLDecoder.decode(mBitmapInfo.getUrl()))));
                }else if(response.code() == HttpURLConnection.HTTP_NOT_MODIFIED){

                }
            }else{
                mIsRequestSuccess = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                //主线程回调
                if(mIsRequestSuccess){
                    BitmapCacherCallback bitmapCacherCallback = mBitmapInfo.getmCallback();
                    if(null != bitmapCacherCallback){
                        bitmapCacherCallback.onBitmapLoaded(mBitmapInfo.getUrl(), mBitmap);
                    }
                }else{
                    BitmapCacherCallback bitmapCacherCallback = mBitmapInfo.getmCallback();
                    if(null != bitmapCacherCallback){
                        bitmapCacherCallback.onBitmapLoadFailed(mBitmapInfo.getUrl(), mBitmapInfo.getDefaultBitmap());
                    }
                }
            }
        });
    }
}
