package com.github.evan.common_utils.mvp.v;

import com.github.evan.common_utils.mvp.p.IMvpPresenter;

import java.util.List;

/**
 * Created by Evan on 2018/10/31.
 */

public interface IMvpView {

    List<IMvpPresenter> initPresenter();

    void showLoadingView();

    void showLoadingDialog();

    void showToast(CharSequence text);

    void showLongToast(CharSequence text);

    void loadingSuccess();

    void loadingFailed();

    void loadingEmptyData();

    void loadingNetworkError();

    void loadingErrorView();
}
