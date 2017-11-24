package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.MultiNestingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/24.
 */

public class TestActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.tab_layout_test_activity)
    TabLayout mTabLayout;
    @BindView(R.id.nesting_view_pager_test_activity)
    NestingViewPager mViewPager;
    private MultiNestingPagerAdapter mNestingAdapter;



    @Override
    public int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mNestingAdapter = new MultiNestingPagerAdapter(this, true);
        int N = 10;
        List<Integer> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(i + 1);
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText("Page " + (i + 1));
            mTabLayout.addTab(tab, i == 0);
        }
        mNestingAdapter.replace(data);
        mViewPager.setAdapter(mNestingAdapter);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
