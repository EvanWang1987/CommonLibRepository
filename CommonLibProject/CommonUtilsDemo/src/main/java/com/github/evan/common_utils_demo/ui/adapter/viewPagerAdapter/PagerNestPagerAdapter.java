package com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.PagerNestPagerHolder;

/**
 * Created by Evan on 2017/11/24.
 */

public class PagerNestPagerAdapter extends BaseTitlePagerAdapter<TitleInteger> {

    public PagerNestPagerAdapter(Context context) {
        super(context);
    }

    public PagerNestPagerAdapter(Context context, boolean isCacheAllItemView) {
        super(context, isCacheAllItemView);
    }

    @Override
    public BasePagerHolder<TitleInteger> onCreateTitleUsableHolder(Context context, ViewGroup parent, int position) {
        return new PagerNestPagerHolder(context, parent);
    }

}
