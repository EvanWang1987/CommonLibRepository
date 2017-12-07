package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.NestMultiHorizontalScrollAdapter;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/7.
 */
public class ListViewNestMultiHorizontalFragment extends BaseFragment {
    @BindView(R.id.loadingPager_list_view_nest_multi_horizontal_fragment)
    LoadingPager mLoadingPager;
    @BindView(R.id.list_view_nest_multi_horizontal_fragment)
    ListView mListView;
    NestMultiHorizontalScrollAdapter mListAdapter;
    NestingViewPager mViewPager;
    ImagePagerAdapter mPagerAdapter;
    List<Integer> mNewListData;
    List<String> mNewPagerData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listview_nest_multi_horizontal, null);
        ButterKnife.bind(this, root);
        mPagerAdapter = new ImagePagerAdapter(getContext());
        mViewPager = new NestingViewPager(getContext());
        mViewPager.setInterceptMode(InterceptMode.HORIZONTAL_BUT_THRESHOLD);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(150)));
        mListView.addHeaderView(mViewPager, null, false);
        mListAdapter = new NestMultiHorizontalScrollAdapter(getContext());
        mListView.setAdapter(mListAdapter);
        loadData();
        return root;
    }

    @Override
    protected void loadData() {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.INVISIBLE);
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);
                int N = 10;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(i + 1);
                }
                mNewListData = data;
                int Z = 5;
                List<String> pagerData = new ArrayList<>(Z);
                for (int i = 0; i < Z; i++) {
                    pagerData.add(ImagePagerAdapter.IMAGES[i]);
                }
                mNewPagerData = pagerData;
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mListAdapter.replace(mNewListData);
            mListAdapter.notifyDataSetChanged();
            mPagerAdapter.replace(mNewPagerData);
            mPagerAdapter.notifyDataSetChanged();
            mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.IDLE);
            mLoadingPager.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
        }
    }
}
