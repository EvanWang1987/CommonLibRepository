package com.github.evan.common_utils_demo.ui.activity;

import android.app.Activity;

import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivity;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2017/11/24.
 */
public class TestActivity extends SlideExitActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onSlideExit(Activity activity) {
        onBackPressed();
    }
}
