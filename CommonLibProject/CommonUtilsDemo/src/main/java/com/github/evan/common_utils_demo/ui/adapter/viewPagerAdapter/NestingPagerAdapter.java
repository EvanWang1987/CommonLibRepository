package com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.bean.PagerTitleUsableInteger;
import com.github.evan.common_utils.ui.adapter.BasePagerAdapter;
import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.NestingPagerHolder;

/**
 * Created by Evan on 2017/11/23.
 */

public class NestingPagerAdapter extends BaseTitlePagerAdapter<PagerTitleUsableInteger> {

    @Override
    public BasePagerHolder<PagerTitleUsableInteger> onCreateTitleUsableHolder(Context context, ViewGroup parent, int position) {
        return new NestingPagerHolder(context, parent);
    }

    public NestingPagerAdapter(Context context) {
        super(context);
    }

    public NestingPagerAdapter(Context context, boolean isCacheAllItemView) {
        super(context, isCacheAllItemView);
    }

}
