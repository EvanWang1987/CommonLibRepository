package com.github.evan.common_utils.bean;

import com.github.evan.common_utils.ui.adapter.BaseTitlePagerAdapter;

/**
 * Created by Evan on 2017/11/24.
 */
public class PagerTitleUsableInteger implements BaseTitlePagerAdapter.PagerTitleUsable {
    private Integer mValue;

    public PagerTitleUsableInteger(Integer value) {
        this.mValue = value;
    }

    public PagerTitleUsableInteger(String value) throws NumberFormatException{
        this.mValue = Integer.valueOf(value);
    }

    public Integer getValue() {
        return mValue;
    }

    public void setValue(Integer value) {
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
