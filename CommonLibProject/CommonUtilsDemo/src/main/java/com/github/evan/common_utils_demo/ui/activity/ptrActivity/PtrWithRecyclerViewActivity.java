package com.github.evan.common_utils_demo.ui.activity.ptrActivity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.DefaultRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/4.
 */
public class PtrWithRecyclerViewActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_layout_ptr_with_view)
    PtrLayout mPtrLayout;
    @BindView(R.id.ptr_with_view_content)
    RecyclerView mRecyclerView;
    DefaultRecyclerAdapter mAdapter;
    private List<Integer> newData;

    @Override
    public final int getLayoutResId() {
        return R.layout.activity_ptr_with_recycler_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mAdapter = new DefaultRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPtrLayout.setPtrSwitcher(this);
        mPtrLayout.setRefreshListener(this);
    }

    @Override
    public boolean checkCanPullToRefresh() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        return (layoutManager.findFirstVisibleItemPosition() == 0 || layoutManager.findFirstVisibleItemPosition() == -1) && !mRecyclerView.canScrollVertically(-1);
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
                int N = 10;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(i + 1);
                }
                newData = data;
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mAdapter.addAll(newData);
            mAdapter.notifyDataSetChanged();
            ToastUtil.showToastWithShortDuration("刷新完成");
            mPtrLayout.refreshComplete(true);
        }
    }

    @OnClick(R.id.btn_auto_ptr)
    void onClick(View view){
        mPtrLayout.autoRefresh(true);
        onRefresh();
    }
}
