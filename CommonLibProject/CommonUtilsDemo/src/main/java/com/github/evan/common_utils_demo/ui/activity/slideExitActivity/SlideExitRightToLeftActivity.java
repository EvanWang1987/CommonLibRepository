package com.github.evan.common_utils_demo.ui.activity.slideExitActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivity;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2017/12/18.
 */

public class SlideExitRightToLeftActivity extends SlideExitActivity {
    private SlideExitDirection mDirection = SlideExitDirection.RIGHT_TO_LEFT;
    private float mSlidePercent = 0.3f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideExitDirection(mDirection);
        setSlidingPercentRelativeActivityWhenExit(mSlidePercent);
    }

    @Override
    protected void onSlideExit(Activity activity) {
        finish();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_slide_exit_demo;
    }
}
