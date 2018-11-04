package com.github.evan.common_utils.mvp.p;

import com.github.evan.common_utils.manager.okHttpManager.OkHttpManager;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.mvp.contract.WetherContract;
import com.github.evan.common_utils.mvp.m.WetherModel;
import com.github.evan.common_utils.utils.GsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evan on 2018/10/31.
 */
public class WetherPresenterImpl extends WetherContract.WetherPresenter {
    private static final String URL = "http://mobile.weather.com.cn/data/sk/101010100.html?_=1381891661455";

    @Override
    public void getWether() {
        Request getRequest = OkHttpManager.getInstance().getGetRequest(URL, null);
        final Call call = OkHttpManager.getInstance().getHttpClient().newCall(getRequest);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String string = response.body().toString();
                    WetherModel model = GsonUtil.getInstance().fromJson(string, WetherModel.class);
                    getView().refreshWether(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ThreadManager.getInstance().getNetworkThreadPool().execute(runnable);
    }

}
