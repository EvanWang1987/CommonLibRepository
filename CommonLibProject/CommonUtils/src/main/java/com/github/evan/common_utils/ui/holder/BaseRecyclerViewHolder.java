package com.github.evan.common_utils.ui.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.SoftReference;


/**
 * Created by Evan on 2017/11/20.
 */

public abstract class BaseRecyclerViewHolder<Data> extends RecyclerView.ViewHolder {
    public static final int DEFAULT_ITEM_TYPE = 0;

    public abstract void onRefreshHolder(Data data);
    public abstract void onHolderRecycled();

    private SoftReference<Context> mContext;
    private Data mData;

    public BaseRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        if(null == context){
            throw new IllegalArgumentException("Context can not be null！");
        }
        mContext = new SoftReference<>(context);
    }

    @Nullable
    public Context getContext() {
        return mContext.get();
    }

    public boolean isContextExists(){
        return null != mContext && null != mContext.get();
    }

    public void clearContext(){
        if(isContextExists()){
            mContext.clear();
        }
    }

    public void setContext(Context context) {
        if(!isContextExists()){
            if(null == context){
                throw new IllegalArgumentException("Context can not be null！");
            }
            mContext = new SoftReference<>(context);
        }
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }



}
