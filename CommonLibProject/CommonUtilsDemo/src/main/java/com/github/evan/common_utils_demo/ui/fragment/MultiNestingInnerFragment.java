package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingRecyclerView;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.MultiNestingInnerAdapter;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.ImagePagerHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/19.
 */
public class MultiNestingInnerFragment extends BaseFragment {
    @BindView(R.id.view_pager_multi_nesting_inner_fragment)
    NestingViewPager mViewPager;
    @BindView(R.id.recycler_view_multi_nesting_inner_fragment)
    NestingRecyclerView mRecyclerView;

    ImagePagerAdapter mPagerAdapter;
    MultiNestingInnerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_multi_nesting_inner, null);
        ButterKnife.bind(this, root);
        mAdapter = new MultiNestingInnerAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mPagerAdapter = new ImagePagerAdapter(getContext());
        mViewPager.setAdapter(mPagerAdapter);
        loadData();
        return root;
    }

    @Override
    protected void loadData() {
        List<String> imageData = new ArrayList<>();
        for (int i = 0; i < ImagePagerAdapter.IMAGES.length; i++) {
            String link = ImagePagerAdapter.IMAGES[i];
            imageData.add(link);
        }

        mPagerAdapter.replace(imageData);
        mPagerAdapter.notifyDataSetChanged();

        List<String> listData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            listData.add("" + i);
        }
        mAdapter.replace(listData);
        mAdapter.notifyDataSetChanged();
    }
}
