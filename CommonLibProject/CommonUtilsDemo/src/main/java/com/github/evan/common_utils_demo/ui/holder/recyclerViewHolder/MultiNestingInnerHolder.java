package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingRecyclerView;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.PayAttentionAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/12/19.
 */

public class MultiNestingInnerHolder extends BaseRecyclerViewHolder<String> {
    private static final int[] protraits = {R.mipmap.portrait_01, R.mipmap.portrait_02, R.mipmap.portrait_03, R.mipmap.portrait_04, R.mipmap.portrait_05, R.mipmap.portrait_06, R.mipmap.portrait_07, R.mipmap.portrait_08, R.mipmap.portrait_09, R.mipmap.portrait_10};
    private NestingRecyclerView mRecyclerView;
    private PayAttentionAdapter mAdapter;

    public MultiNestingInnerHolder(Context context, View itemView) {
        super(context, itemView);
        mRecyclerView = (NestingRecyclerView) itemView;
        mAdapter = new PayAttentionAdapter(context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void onRefreshHolder(String string) {
        List<TitleInteger> data = new ArrayList<>();
        for (int i = 0; i < protraits.length; i++) {
            int res = protraits[i];
            TitleInteger titleInteger = new TitleInteger(res);
            titleInteger.setTitle("频道 " + i);
            data.add(titleInteger);
        }
        mAdapter.replace(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHolderRecycled() {

    }
}
