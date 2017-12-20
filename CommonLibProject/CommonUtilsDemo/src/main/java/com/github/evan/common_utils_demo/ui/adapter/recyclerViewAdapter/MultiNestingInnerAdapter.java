package com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter;

import android.content.Context;
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
                NestingRecyclerView recyclerView = (NestingRecyclerView) inflater.inflate(R.layout.item_pay_attention_parent, null);
                recyclerView.setInterceptMode(InterceptMode.HORIZONTAL_BUT_THRESHOLD);
                recyclerView.setHandleParallelSlide(true);
                recyclerView.setNestedInSameInterceptModeParent(true);
                itemView = recyclerView;
                break;
        }
        return itemView;
    }

    @Override
    public String get(int position) {
        return super.get(position);
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
    public int getItemViewType(int position) {
        if(position == 3)
            return 1;
        return 0;
    }
}
