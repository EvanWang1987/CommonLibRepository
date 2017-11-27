package com.github.evan.common_utils_demo.bean;

import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;

/**
 * Created by Evan on 2017/11/27.
 */

public class TitleInteger implements BaseTitlePagerAdapter.PagerTitleUsable {
    private Integer mValue;
    private CharSequence mTitle;

    public TitleInteger(Integer value) {
        this.mValue = value;
        mTitle = "Pager " + value;
    }

    public TitleInteger(String value) throws NumberFormatException {
        this.mValue = Integer.valueOf(value);
        mTitle = "Pager " + value;
    }

    public Integer getValue() {
        return mValue;
    }

    public void setValue(Integer value) {
        this.mValue = value;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    @Override
    public CharSequence getTitle() {
        return mTitle;
    }
}
