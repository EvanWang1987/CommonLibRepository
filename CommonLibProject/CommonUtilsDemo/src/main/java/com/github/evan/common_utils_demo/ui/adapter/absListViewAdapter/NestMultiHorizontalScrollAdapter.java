package com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter;

import android.content.Context;

import com.github.evan.common_utils.ui.adapter.BaseAbsListViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.NestHorizontalScrollHolder;

/**
 * Created by Evan on 2017/12/7.
 */
public class NestMultiHorizontalScrollAdapter extends BaseAbsListViewAdapter<Integer> {

    public NestMultiHorizontalScrollAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseAbsListViewHolder<Integer> onCreateHolder(Context context, int itemViewType) {
        return new NestHorizontalScrollHolder(context);
    }
}
