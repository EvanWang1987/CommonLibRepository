package com.github.evan.common_utils.api;

import android.os.Handler;
import android.os.Looper;

import com.github.evan.common_utils.manager.okHttpManager.OkHttpManager;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evan on 2017/12/12.
 */
public class ApiGroup implements Runnable {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private List<BaseApi> mApis = new ArrayList<>();
    private List<BaseApi> mSuccessApi = new ArrayList<>();
    private List<BaseApi> mFailApi = new ArrayList<>();
    private List<IOException> mFailExceptions = new ArrayList<>();
    private ApiGroupCallback mCallback;


    public ApiGroup() {
    }

    public void setCallback(ApiGroupCallback callback) {
        this.mCallback = callback;
    }

    public void addApi(BaseApi api) {
        mApis.add(api);
    }

    public void removeApi(BaseApi api) {
        mApis.remove(api);
    }

    public void execute() {
        ThreadManager.getInstance().getNetworkThreadPool().execute(this);
    }

    @Override
    public void run() {
        int N = mApis.size();
        for (int i = 0; i < N; i++) {
            BaseApi baseApi = mApis.get(i);
            String url = baseApi.createApiUrl();
            Map<String, String> requestHeaders = baseApi.createRequestHeaders();
            Request request = baseApi.createRequest(url, requestHeaders);
            Call call = OkHttpManager.getInstance().getHttpClient().newCall(request);
            Response response = null;
            try {
                response = call.execute();
                baseApi.requestSuccess(response);
            } catch (IOException e) {
                e.printStackTrace();
                baseApi.requestException(response, e);
                mFailApi.add(baseApi);
                mFailExceptions.add(e);
            }

            mSuccessApi.add(baseApi);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (null != mCallback) {
                        if (!mFailApi.isEmpty()) {
                            mCallback.onRequestFail(mSuccessApi, mFailApi, mFailExceptions);
                        }else{
                            mCallback.onRequestSuccess(mApis);
                        }

                        mSuccessApi.clear();
                        mFailApi.clear();
                        mFailExceptions.clear();
                    }
                }
            });
        }
    }
}
