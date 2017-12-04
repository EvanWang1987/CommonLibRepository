package com.github.evan.common_utils_demo.ui.activity.ptrActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/4.
 */
public class PtrWithViewGroupActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_layout_ptr_with_view)
    PtrLayout mPtrLayout;

    @Override
    public final int getLayoutResId() {
        return R.layout.activity_ptr_with_view_group;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPtrLayout.setPtrSwitcher(this);
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
                ToastUtil.showToastWithShortDuration("刷新完成");
            }
        }, 3000);
    }

    @OnClick(R.id.btn_auto_ptr)
    void onClick(View view){
        mPtrLayout.autoRefresh(true);
        ToastUtil.showToastWithShortDuration("开始下拉刷新");
        postDelay(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.refreshComplete(true);
                ToastUtil.showToastWithShortDuration("刷新完成");
            }
        }, 3000);


    }
}
