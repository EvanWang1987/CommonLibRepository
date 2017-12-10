package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.PagerNestPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/22.
 */
public class ViewPagerFragment extends BaseFragment {
    @BindView(R.id.tab_layout_view_pager_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.nesting_view_pager_view_pager_fragment)
    NestingViewPager mViewPager;
    @BindView(R.id.loading_pager_view_pager_fragment)
    LoadingPager mLoadingPager;
    private PagerNestPagerAdapter mNestingAdapter;
    private List<TitleInteger> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_pager, null);
        ButterKnife.bind(this, root);
        mNestingAdapter = new PagerNestPagerAdapter(getContext(), true);
        mViewPager.setAdapter(mNestingAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Override
    protected void loadData() {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                int N = 10;
                List<TitleInteger> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(new TitleInteger(i + 1));
                }
                mData = data;
                sendEmptyMessage(LOAD_COMPLETE);
            }
        };

        ThreadManager.getInstance().getIOThreadPool().execute(runnable);
    }

    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            for (int i = 0; i < mData.size(); i++) {
                TabLayout.Tab tab = mTabLayout.newTab();
                mTabLayout.addTab(tab, i == 0);
            }
            mNestingAdapter.replace(mData);
            mNestingAdapter.notifyDataSetChanged();
            mLoadingPager.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
        }
    }
}
