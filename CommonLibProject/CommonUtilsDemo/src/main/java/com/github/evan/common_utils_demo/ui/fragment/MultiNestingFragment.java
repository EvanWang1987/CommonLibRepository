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
import com.github.evan.common_utils.ui.adapter.SimpleFragmentStateAdapter;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/19.
 */

public class MultiNestingFragment extends BaseFragment {
    private static final String[] fragments = {MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName()};


    @BindView(R.id.tab_layout_multi_nesting_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.loading_pager_multi_nesting_fragment)
    LoadingPager mLoadingPager;
    @BindView(R.id.view_pager_multi_nesting_fragment)
    NestingViewPager mViewPager;
    SimpleFragmentStateAdapter mFragmentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_multi_nesting, null);
        ButterKnife.bind(this, root);
        mFragmentAdapter = new SimpleFragmentStateAdapter(getFragmentManager(), getContext(), Arrays.asList(fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mViewPager.setAdapter(mFragmentAdapter);
        }
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
                sendEmptyMessage(LOAD_COMPLETE);
            }
        };
        ThreadManager.getInstance().getIOThreadPool().execute(runnable);
    }
}
