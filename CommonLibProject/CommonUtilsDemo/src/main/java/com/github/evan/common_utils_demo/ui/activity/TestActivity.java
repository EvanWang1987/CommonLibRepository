package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrFrameLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.ui.view.ptr.indicator.ClassicIndicator;
import com.github.evan.common_utils.ui.view.ptr.indicator.LargeImageAnimationIndicator;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/24.
 */
public class TestActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout mPtrLayout;
    @BindView(R.id.ptr_indicator)
    ClassicIndicator mIndicator;

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
        }, 5000);
    }
}
