package com.github.evan.common_utils_demo.ui.holder.absListViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2017/12/7.
 */
public class NestHorizontalScrollHolder extends BaseAbsListViewHolder<Integer> {


    public NestHorizontalScrollHolder(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.item_nest_horizontal_scroll, null);
    }

    @Override
    public void onRefreshHolder(Integer integer) {

    }
}
