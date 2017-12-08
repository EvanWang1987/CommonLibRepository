package com.github.evan.common_utils_demo.ui.holder.viewPagerHolder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.bean.PagerTitleUsableInteger;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/11/23.
 */
public class NestingPagerHolder extends BasePagerHolder<PagerTitleUsableInteger> {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    public NestingPagerHolder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.pager_nesting, null);
        mAdapter = new RecyclerAdapter(getContext());
        return mRecyclerView;
    }

    @Override
    public void instantiateItem(ViewGroup container, int position) {
        int N = 30;
        List<String> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(i + "");
        }
        mAdapter.replace(data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
