package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.MultiNestingPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/22.
 */

public class ViewPagerFragment extends BaseFragment {
    @BindView(R.id.tab_layout_view_pager_fragment)
    TabLayout mTabLayout;
    @BindView(R.id.nesting_view_pager_view_pager_fragment)
    NestingViewPager mViewPager;
    private MultiNestingPagerAdapter mNestingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_pager, null);
        ButterKnife.bind(this, root);
        mNestingAdapter = new MultiNestingPagerAdapter(getContext(), true);
        int N = 10;
        List<TitleInteger> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(new TitleInteger(i + 1));
            TabLayout.Tab tab = mTabLayout.newTab();
            mTabLayout.addTab(tab, i == 0);
        }
        mNestingAdapter.replace(data);
        mViewPager.setAdapter(mNestingAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){

        }
    }

    @Override
    protected void loadData() {
    }
}
