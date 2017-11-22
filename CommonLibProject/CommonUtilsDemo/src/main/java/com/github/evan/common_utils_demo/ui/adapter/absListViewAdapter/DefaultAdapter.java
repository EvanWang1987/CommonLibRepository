package com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter;

import android.content.Context;

import com.github.evan.common_utils.ui.adapter.BaseAbsListViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.DefaultHolder;

/**
 * Created by Evan on 2017/11/20.
 */
public class DefaultAdapter extends BaseAbsListViewAdapter<Integer> {

    public DefaultAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseAbsListViewHolder<Integer> onCreateHolder(Context context, int itemViewType) {
        return new DefaultHolder(context);
    }
}
