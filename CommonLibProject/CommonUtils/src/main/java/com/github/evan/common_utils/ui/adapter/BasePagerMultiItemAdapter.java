package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.holder.BasePagerHolder;

/**
 * Created by Evan on 2017/11/24.
 */

public abstract class BasePagerMultiItemAdapter<Data> extends BasePagerAdapter<Data> {
    public abstract BasePagerHolder<Data> onCreateHolder(Context context, ViewGroup parent, int position, int itemViewType);


    public BasePagerMultiItemAdapter(Context context) {
        super(context);
    }

    public BasePagerMultiItemAdapter(Context context, boolean isCacheAllItemView) {
        super(context, isCacheAllItemView);
    }

    @Override
    public BasePagerHolder<Data> onCreateHolder(Context context, ViewGroup parent, int position) {
        return onCreateHolder(context, parent, position, getItemViewType(position));
    }

    protected int getItemViewType(int position){
        return 0;
    }
}
