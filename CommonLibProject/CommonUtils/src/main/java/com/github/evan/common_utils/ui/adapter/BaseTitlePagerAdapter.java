package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.holder.BasePagerHolder;

/**
 * Created by Evan on 2017/11/24.
 */

public class BaseTitlePagerAdapter<Data extends BaseTitlePagerAdapter.PagerTitleUsable> extends BasePagerAdapter<Data> {

    public interface PagerTitleUsable {
        void setTitle(CharSequence title);

        CharSequence getTitle();
    }

    public BaseTitlePagerAdapter(Context context) {
        super(context);
    }

    public BaseTitlePagerAdapter(Context context, boolean isCacheAllItemView) {
        super(context, isCacheAllItemView);
    }

    @Override
    public BasePagerHolder<Data> onCreateHolder(Context context, ViewGroup parent, int position) {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return get(position).getTitle();
    }
}
