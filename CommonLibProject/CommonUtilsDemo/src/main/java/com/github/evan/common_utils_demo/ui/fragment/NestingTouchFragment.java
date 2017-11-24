package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.bean.PagerTitleUsableInteger;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.NestingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/23.
 */
public class NestingTouchFragment extends BaseFragment {
    @BindView(R.id.tab_layout_nesting_view_pager)
    TabLayout mTabLayout;
    @BindView(R.id.dispatch_view_pager)
    NestingViewPager mInterceptTouchViewPager;
    NestingPagerAdapter mAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nesting_touch, null);
        ButterKnife.bind(this, root);
        mTabLayout.setupWithViewPager(mInterceptTouchViewPager);
        return root;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mInterceptTouchViewPager.setAdapter(mAdapter);
        }
    }

    @Override
    protected void loadData() {
        new Thread(){
            @Override
            public void run() {
                mAdapter = new NestingPagerAdapter(getContext());
                int N = 5;
                List<PagerTitleUsableInteger> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    PagerTitleUsableInteger integer = new PagerTitleUsableInteger(i + 1);
                    data.add(integer);
                }
                mAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }
}
