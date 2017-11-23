package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;

/**
 * Created by Evan on 2017/11/21.
 */
public abstract class SwitchRecyclerAdapter<Data> extends BaseRecyclerViewAdapter<Data> {
    public static final int LIST_MODE_VERTICAL = 1;
    public static final int LIST_MODE_HORIZONTAL = 2;
    public static final int GRID_MODE = 3;
    public static final int STAGGERED_GRID_MODE = 4;

    public abstract View onCreateViewWithSwitchMode(LayoutInflater inflater, ViewGroup parent, int viewType, int switchMode);
    public abstract BaseRecyclerViewHolder<Data> onCreateHolderWithSwitchMode(View itemView, ViewGroup parent, int viewType, int switchMode);

    private int mCurrentMode = LIST_MODE_VERTICAL;


    public SwitchRecyclerAdapter(Context context) {
        super(context);
    }

    public int getCurrentMode() {
        return mCurrentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.mCurrentMode = currentMode;
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return onCreateViewWithSwitchMode(inflater, parent, viewType, mCurrentMode);
    }

    @Override
    public final BaseRecyclerViewHolder<Data> onCreateHolder(View itemView, ViewGroup parent, int viewType) {
        return onCreateHolderWithSwitchMode(itemView, parent, viewType, mCurrentMode);
    }
}
