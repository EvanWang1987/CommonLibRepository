package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.SimpleFragmentStateAdapter;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/7.
 */
public class ViewPagerNestListViewFragment extends BaseFragment {
    private static final String[] fragments = {ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName(), ListViewNestMultiHorizontalFragment.class.getName()};


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.nesting_view_pager)
    NestingViewPager mViewPager;
    SimpleFragmentStateAdapter mFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_pager_nest_list_view, null);
        ButterKnife.bind(this, root);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mFragmentAdapter = new SimpleFragmentStateAdapter(getChildFragmentManager(), getContext(), Arrays.asList(fragments));
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Override
    protected void loadData() {

    }
}
