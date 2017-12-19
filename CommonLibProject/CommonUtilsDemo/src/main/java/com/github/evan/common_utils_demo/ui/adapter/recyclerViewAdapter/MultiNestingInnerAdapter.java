package com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.ui.adapter.BaseRecyclerViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingRecyclerView;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.DefaultHolder;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.MultiNestingInnerHolder;

/**
 * Created by Evan on 2017/12/19.
 */

public class MultiNestingInnerAdapter extends BaseRecyclerViewAdapter<String> {
    public MultiNestingInnerAdapter(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.item_list, parent, false);
                break;

            case 1:
                NestingRecyclerView recyclerView = new NestingRecyclerView(parent.getContext());
                RecyclerView.LayoutParams lps = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                recyclerView.setLayoutParams(lps);
                recyclerView.setInterceptMode(InterceptMode.HORIZONTAL_BUT_THRESHOLD);
                recyclerView.setHandleParallelSlide(true);
                itemView = recyclerView;
                break;
        }
        return itemView;
    }

    @Override
    public BaseRecyclerViewHolder<String> onCreateHolder(View itemView, ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder<String> holder = null;
        switch (viewType) {
            case 0:
                holder = new DefaultHolder(itemView.getContext(), itemView);
                break;

            case 1:
                holder = new MultiNestingInnerHolder(itemView.getContext(), itemView);
                break;
        }
        return holder;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String positionString = position + "";
        if (positionString.length() >= 2) {
            int remainder = position % 10;
            if (remainder == Integer.valueOf(positionString.substring(0, positionString.length()))) {
                return 1;
            }
        }
        return 0;
    }
}
