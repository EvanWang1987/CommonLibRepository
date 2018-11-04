package com.github.evan.common_utils.mvp.p;

import com.github.evan.common_utils.mvp.m.IMvpModel;
import com.github.evan.common_utils.mvp.v.IMvpView;

import java.lang.ref.SoftReference;

/**
 * Created by Evan on 2018/10/31.
 */

public class BaseMvpPresenter<V extends IMvpView, M extends IMvpModel> implements IMvpPresenter {
    private SoftReference<V> mView;
    private M mModel;

    public V getView() {
        return mView.get();
    }

    public M getModel() {
        return mModel;
    }

    @Override
    public void init(IMvpView view, IMvpModel model) {
        mView = (SoftReference<V>) new SoftReference<>(view);
        mModel = (M) model;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {

    }
}
