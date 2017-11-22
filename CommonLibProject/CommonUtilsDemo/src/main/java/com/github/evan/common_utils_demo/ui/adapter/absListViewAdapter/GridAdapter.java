package com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter;

import android.content.Context;

import com.github.evan.common_utils.ui.adapter.BaseAbsListViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.GridItemHolder;

/**
 * Created by Evan on 2017/11/21.
 */
public class GridAdapter extends BaseAbsListViewAdapter<Integer> {

    public GridAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseAbsListViewHolder<Integer> onCreateHolder(Context context, int itemViewType) {
        return new GridItemHolder(context);
    }
}
