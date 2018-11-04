package com.github.evan.common_utils.mvp.p;

import com.github.evan.common_utils.mvp.m.IMvpModel;
import com.github.evan.common_utils.mvp.v.IMvpView;

/**
 * Created by Evan on 2018/10/31.
 */

public interface IMvpPresenter {

    void init(IMvpView view, IMvpModel model);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestory();

}
