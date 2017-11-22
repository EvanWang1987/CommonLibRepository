package com.github.evan.common_utils.ui.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;

/**
 * Created by Evan on 2017/11/22.
 */

public abstract class BasePagerHolder<Data> {
    public abstract View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent);
    public abstract void instantiateItem(ViewGroup container, int position);
    public abstract void destroyItem(ViewGroup container, int position, Object object);


    private SoftReference<Context> mContext;
    private View mRootView;
    private int mPosition;
    private Data mData;

    public BasePagerHolder(Context context, ViewGroup parent) {
        if(null == context){
            throw new IllegalArgumentException("Context can not be null！");
        }
        this.mContext = new SoftReference<>(context);
        mRootView = onCreateView(context, LayoutInflater.from(context), parent);
    }

    @Nullable
    public Context getContext() {
        return isContextExists() ? mContext.get() : null;
    }

    public boolean isContextExists(){
        return null != mContext && null != mContext.get();
    }

    public void setContext(Context context) {
        if(!isContextExists()){
            mContext = new SoftReference<>(context);
        }
    }

    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }
}
