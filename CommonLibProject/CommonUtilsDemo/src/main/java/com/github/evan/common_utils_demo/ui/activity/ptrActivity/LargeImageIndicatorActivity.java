package com.github.evan.common_utils_demo.ui.activity.ptrActivity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrFrameLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/1.
 */
public class LargeImageIndicatorActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_layout_ptr_with_grid_view_activity)
    PtrFrameLayout mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPtrLayout.setRefreshableSwitcher(this);
        mPtrLayout.setRefreshListener(this);
        postDelay(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.autoRefresh(true);
                onRefresh();
            }
        }, 1000);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_large_image_indicator_with_ptr;
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
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            ToastUtil.showToastWithShortDuration("刷新完成");
            mPtrLayout.refreshComplete(true);
        }
    }

    @Override
    public void onRefresh() {
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }
}
