package com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BasePagerAdapter;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.ImagePagerHolder;

/**
 * Created by Evan on 2017/11/22.
 */

public class ImagePagerAdapter extends BasePagerAdapter<Integer> {

    public ImagePagerAdapter(Context context) {
        super(context);
    }

    @Override
    public BasePagerHolder<Integer> onCreateHolder(Context context, ViewGroup parent, int position) {
        return new ImagePagerHolder(context, parent);
    }
}
