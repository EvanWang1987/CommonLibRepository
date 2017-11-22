package com.github.evan.common_utils.ui.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * Created by Evan on 2017/11/20.
 */

public abstract class BaseAbsListViewHolder<Data> {
    public static final int DEFAULT_ITEM_TYPE = 0;
    public abstract View onCreateView(LayoutInflater inflater);
    public abstract void onRefreshHolder(Data data);


    private SoftReference<Context> mContext;
    private View mRootView;
    private Data mData;
    private int mPosition;

    public BaseAbsListViewHolder(Context context) {
        if(null == context){
            throw new IllegalArgumentException("Context can not be null！");
        }
        mContext = new SoftReference<>(context);
        mRootView = onCreateView(LayoutInflater.from(context));
    }

    public Context getContext() {
        return mContext.get();
    }

    public boolean isContextExists(){
        return null != mContext && null != mContext.get();
    }

    public void setContext(Context context) {
        if(!isContextExists()){
            if(null == context){
                throw new IllegalArgumentException("Context can not be null！");
            }
        }
        this.mContext = new SoftReference<>(context);
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    public int getItemViewType(){
        return DEFAULT_ITEM_TYPE;
    }
}
