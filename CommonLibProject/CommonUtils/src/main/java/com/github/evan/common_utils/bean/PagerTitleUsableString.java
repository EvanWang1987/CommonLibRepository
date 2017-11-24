package com.github.evan.common_utils.bean;

import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;

/**
 * Created by Evan on 2017/11/24.
 */

public class PagerTitleUsableString implements BaseTitlePagerAdapter.PagerTitleUsable {
    private String mValue;

    public PagerTitleUsableString(String value) {
        this.mValue = value;
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public CharSequence getTitle() {
        return "Pager " + mValue;
    }
}
