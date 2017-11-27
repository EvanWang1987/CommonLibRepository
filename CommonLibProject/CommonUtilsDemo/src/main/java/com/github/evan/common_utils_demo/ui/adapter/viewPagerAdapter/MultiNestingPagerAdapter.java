package com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BasePagerAdapter;
import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.MultiNestingPagerHolder;

/**
 * Created by Evan on 2017/11/24.
 */

public class MultiNestingPagerAdapter extends BaseTitlePagerAdapter<TitleInteger> {

    public MultiNestingPagerAdapter(Context context) {
        super(context);
    }

    public MultiNestingPagerAdapter(Context context, boolean isCacheAllItemView) {
        super(context, isCacheAllItemView);
    }

    @Override
    public BasePagerHolder<TitleInteger> onCreateTitleUsableHolder(Context context, ViewGroup parent, int position) {
        return new MultiNestingPagerHolder(context, parent);
    }

}
