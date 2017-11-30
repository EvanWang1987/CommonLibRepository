package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrFrameLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.ui.view.ptr.indicator.PtrClassicIndicator;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.MultiNestingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/24.
 */
public class TestActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout mPtrLayout;
    @BindView(R.id.ptr_indicator)
    PtrClassicIndicator mIndicator;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPtrLayout.setRefreshableSwitcher(this);
        mPtrLayout.setRefreshListener(this);
    }

    @Override
    public boolean checkCanPullToRefresh() {
        return true;
    }

    @Override
    public void onStartPulling() {
        ToastUtil.showToastWithShortDuration("开始下拉刷新");
    }

    @Override
    public void onRefresh() {
        postDelay(new Runnable() {
            @Override
            public void run() {
               mPtrLayout.refreshComplete(true);
                ToastUtil.showToastWithShortDuration("刷新完毕");
            }
        }, 3000);
    }
}
