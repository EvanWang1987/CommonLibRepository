package com.github.evan.common_utils.api;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evan on 2017/12/11.
 */
public abstract class ApiWithCallback extends BaseApi {
    private Callback mCallback;


    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void requestSuccess(Response response) throws IOException {
        if (null != mCallback) {
            mCallback.onResponse(mCall, response);
        }
    }

    @Override
    public void requestException(Response response, IOException e) {
        if (null != mCallback) {
            mCallback.onFailure(mCall, e);
        }
    }


}
