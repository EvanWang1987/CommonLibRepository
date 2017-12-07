package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/12/7.
 */

public class SimpleFragmentStateAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<String> mData = new ArrayList<>();

    public SimpleFragmentStateAdapter(FragmentManager fm, Context mContext, List<String> mData) {
        super(fm);
        this.mContext = mContext.getApplicationContext();
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int position) {
        String fragmentName = mData.get(position);
        Fragment fragment = Fragment.instantiate(mContext, fragmentName);
        return fragment;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Pager " + (position + 1);
    }
}
