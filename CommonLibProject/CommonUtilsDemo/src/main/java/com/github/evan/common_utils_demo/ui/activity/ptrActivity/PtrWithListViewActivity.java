package com.github.evan.common_utils_demo.ui.activity.ptrActivity;

import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.ptr.OnRefreshListener;
import com.github.evan.common_utils.ui.view.ptr.PtrLayout;
import com.github.evan.common_utils.ui.view.ptr.PullToRefreshSwitcher;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.DefaultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/4.
 */
public class PtrWithListViewActivity extends BaseActivity implements PullToRefreshSwitcher, OnRefreshListener {
    @BindView(R.id.ptr_layout_ptr_with_view)
    PtrLayout mPtrLayout;
    @BindView(R.id.ptr_with_view_content)
    ListView mListView;
    DefaultAdapter mAdapter;
    private List<Integer> newData;

    @Override
    public final int getLayoutResId() {
        return R.layout.activity_ptr_with_list_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mAdapter = new DefaultAdapter(this);
        mListView.setAdapter(mAdapter);
        mPtrLayout.setPtrSwitcher(this);
        mPtrLayout.setRefreshListener(this);
    }

    @Override
    public boolean checkCanPullToRefresh() {
        return mListView.getFirstVisiblePosition() == 0 && !mListView.canScrollVertically(-1);
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
