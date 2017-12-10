package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.GridAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.DefaultAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.ThreeStyleItemAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.TwoStyleItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Evan on 2017/11/20.
 */
public class ListGridViewFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout_list_grid_view_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.loadingPager_list_grid_view_fragment)
    LoadingPager mLoadingPager;
    private DefaultAdapter mDefaultAdapter;
    private TwoStyleItemAdapter mTwoStyleItemAdapter;
    private ThreeStyleItemAdapter mThreeStyleItemAdapter;
    private GridAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_grid_view, null);
        ButterKnife.bind(this, root);
        mTabLayout.addOnTabSelectedListener(this);
        return root;
    }

    @Override
    public void onDestroy() {
        if(null != mDefaultAdapter) mDefaultAdapter.recycle();
        if(null != mTwoStyleItemAdapter) mTwoStyleItemAdapter.recycle();
        if(null != mThreeStyleItemAdapter) mThreeStyleItemAdapter.recycle();
        if(null != mGridAdapter) mGridAdapter.recycle();
        super.onDestroy();
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
            onTabSelected(tab);
            mTabLayout.setEnabled(true);
            mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.IDLE);
            mLoadingPager.setVisibility(View.GONE);
        }
    }

    @Override
    protected void loadData() {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mTabLayout.setEnabled(false);
        mLoadingPager.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mGridView.setVisibility(View.GONE);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                int N = 30;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(i + 1);
                }
                mDefaultAdapter = new DefaultAdapter(getContext());
                mDefaultAdapter.replace(data);
                mTwoStyleItemAdapter = new TwoStyleItemAdapter(getContext());
                mTwoStyleItemAdapter.replace(data);
                mThreeStyleItemAdapter = new ThreeStyleItemAdapter(getContext());
                mThreeStyleItemAdapter.replace(data);
                mGridAdapter = new GridAdapter(getContext());
                mGridAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        };

        ThreadManager.getInstance().getIOThreadPool().execute(runnable);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()){
            case 0:
                mListView.setAdapter(mDefaultAdapter);
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.INVISIBLE);
                break;

            case 1:
                mListView.setAdapter(mTwoStyleItemAdapter);
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.INVISIBLE);
                break;

            case 2:
                mListView.setAdapter(mThreeStyleItemAdapter);
                mListView.setVisibility(View.VISIBLE);
                mGridView.setVisibility(View.INVISIBLE);
                break;

            case 3:
                mGridView.setAdapter(mGridAdapter);
                mGridView.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.INVISIBLE);
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
