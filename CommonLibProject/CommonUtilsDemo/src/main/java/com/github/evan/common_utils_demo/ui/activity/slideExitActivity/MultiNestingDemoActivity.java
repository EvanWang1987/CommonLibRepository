package com.github.evan.common_utils_demo.ui.activity.slideExitActivity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.adapter.SimpleFragmentStateAdapter;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingTabLayout;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.fragment.MultiNestingInnerFragment;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/19.
 */
public class MultiNestingDemoActivity extends BaseActivity {
    private static final String[] fragments = {MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName(), MultiNestingInnerFragment.class.getName()};
    @BindView(R.id.tab_layout_multi_nesting_fragment)
    NestingTabLayout mTabLayout;
    @BindView(R.id.loading_pager_multi_nesting_fragment)
    LoadingPager mLoadingPager;
    @BindView(R.id.view_pager_multi_nesting_fragment)
    NestingViewPager mViewPager;
    SimpleFragmentStateAdapter mFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mFragmentAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), this, Arrays.asList(fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        loadData();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_multi_nesting;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mViewPager.setAdapter(mFragmentAdapter);
            mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.IDLE);
            mLoadingPager.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
        }
    }

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
