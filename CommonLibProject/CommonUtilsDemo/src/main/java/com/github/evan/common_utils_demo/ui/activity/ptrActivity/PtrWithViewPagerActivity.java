package com.github.evan.common_utils_demo.ui.activity.ptrActivity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/4.
 */
public class PtrWithViewPagerActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_layout_ptr_with_view)
    PtrLayout mPtrLayout;
    @BindView(R.id.ptr_with_view_content)
    ViewPager mViewPager;
    ImagePagerAdapter mAdapter;
    private List<Integer> mNewData;


    @Override
    public final int getLayoutResId() {
        return R.layout.activity_ptr_with_view_pager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPtrLayout.setPtrSwitcher(this);
        mPtrLayout.setRefreshListener(this);
        mAdapter = new ImagePagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
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
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(3000);
                int N = 5;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    int image = ImagePagerAdapter.IMAGES[i];
                    data.add(image);
                }
                mNewData = data;
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mAdapter.addAll(mNewData);
            mAdapter.notifyDataSetChanged();
            mPtrLayout.refreshComplete(true);
        }
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
