package com.github.evan.common_utils.api;

import com.github.evan.common_utils.manager.okHttpManager.OkHttpManager;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evan on 2017/12/11.
 */
public abstract class BaseApi implements Runnable {
    protected Call mCall;
    private Request mRequest;

    public abstract String createApiUrl();

    public abstract Request createRequest(String url, Map<String, String> headers);

    public abstract Map<String, String> createRequestHeaders();

    public abstract void requestSuccess(Response response) throws IOException;

    public abstract void requestException(Response response, IOException e);

    public boolean isExecuted() {
        if (null != mCall) {
            return mCall.isExecuted();
        }
        return false;
    }

    public boolean isCancled() {
        if (null != mCall) {
            return mCall.isCanceled();
        }
        return false;
    }

    public void cancle() {
        if (null != mCall && mCall.isExecuted() && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

    public void execute() {
        ThreadManager.getInstance().getNetworkThreadPool().execute(this);
    }

    @Override
    public final void run() {
        String url = createApiUrl();
        Map<String, String> requestHeaders = createRequestHeaders();
        mRequest = createRequest(url, requestHeaders);
        mCall = OkHttpManager.getInstance().getHttpClient().newCall(mRequest);
        Response response = null;
        try {
            response = mCall.execute();
            requestSuccess(response);
        } catch (IOException e) {
            e.printStackTrace();
            requestException(response, e);
        } finally {
            mCall = null;
            mRequest = null;
        }
    }
}
