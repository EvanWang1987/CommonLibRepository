package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.itemDecoration.GridDecoration;
import com.github.evan.common_utils.ui.itemDecoration.ListDecoration;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;

/**
 * Created by Evan on 2017/11/20.
 */
public class RecyclerViewFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private static final int[] STAGGERED_IMAGE = {R.mipmap.img_staggered_one, R.mipmap.img_staggered_two, R.mipmap.img_staggered_three,
            R.mipmap.img_staggered_four, R.mipmap.img_staggered_five, R.mipmap.img_staggered_six, R.mipmap.img_staggered_seven, R.mipmap.img_staggered_eight, R.mipmap.img_staggered_nine, R.mipmap.img_staggered_ten,
            R.mipmap.img_staggered_eleven, R.mipmap.img_staggered_twelve, R.mipmap.img_staggered_thirteen, R.mipmap.img_staggered_fourteen, R.mipmap.img_staggered_fifteen, R.mipmap.img_staggered_sixteen, R.mipmap.img_staggered_seventeen,
            R.mipmap.img_staggered_eighteen, R.mipmap.img_staggered_nineteen, R.mipmap.img_staggered_twenty};


    @BindView(R.id.tab_layout_recycler_view_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_pager_recycler_view_fragment)
    LoadingPager mLoadingPager;
    private RecyclerAdapter mRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, null);
        ButterKnife.bind(this, root);
        mTabLayout.addOnTabSelectedListener(this);
        return root;
    }


    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
            onTabSelected(tab);
            mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.IDLE);
            mLoadingPager.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData() {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                int N = 20;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(STAGGERED_IMAGE[i]);
                }

                mRecyclerAdapter = new RecyclerAdapter(getContext());
                mRecyclerAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case 0:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_VERTICAL);
                mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 1:
                RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(horizontalLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_HORIZONTAL);
                mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 2:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                mRecyclerView.setLayoutManager(gridLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.GRID_MODE);
                mRecyclerView.setAdapter(mRecyclerAdapter);
                break;

            case 3:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                mRecyclerAdapter.setCurrentMode(RecyclerAdapter.STAGGERED_GRID_MODE);
                mRecyclerView.setAdapter(mRecyclerAdapter);

                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
