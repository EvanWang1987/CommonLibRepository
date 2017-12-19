package com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BaseRecyclerViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.PayAttentionHolder;

/**
 * Created by Evan on 2017/12/19.
 */

public class PayAttentionAdapter extends BaseRecyclerViewAdapter<TitleInteger> {
    public PayAttentionAdapter(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return inflater.inflate(R.layout.item_pay_attention, parent, false);
    }

    @Override
    public BaseRecyclerViewHolder<TitleInteger> onCreateHolder(View itemView, ViewGroup parent, int viewType) {
        return new PayAttentionHolder(itemView.getContext(), itemView);
    }
}
