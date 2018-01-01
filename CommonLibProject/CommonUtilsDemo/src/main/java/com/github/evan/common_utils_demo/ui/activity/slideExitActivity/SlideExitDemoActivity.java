package com.github.evan.common_utils_demo.ui.activity.slideExitActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.BaseSlideExitActivity;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.view.slideExitView.OnSlideExitListener;
import com.github.evan.common_utils.ui.view.slideExitView.SlideExitLayout;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/18.
 */
public class SlideExitDemoActivity extends BaseSlideExitActivity {
    public static final String SLIDE_EXIT_DIRECTION = "slide_exit_direction";
    private SlideExitDirection mSlideExitDirection = SlideExitDirection.LEFT_TO_RIGHT;

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            int anInt = extras.getInt(SLIDE_EXIT_DIRECTION, SlideExitDirection.LEFT_TO_RIGHT.value);
            mSlideExitDirection = SlideExitDirection.valueOf(anInt);
        }
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ResourceUtil.getColor(R.color.text_color_black));
        textView.setText(mSlideExitDirection.toString());
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public SlideExitActivityConfig onCreateConfig() {
        SlideExitActivityConfig config = new SlideExitActivityConfig();
        config.setExitDuration(200);
        config.setRollBackDuration(300);
        config.setSlidingPercentRelativeActivityWhenNotExit(0.3f);
        config.setBackgroundColor(ResourceUtil.getColor(R.color.White));
        config.setExitDirection(mSlideExitDirection);
        return config;
    }

    @Override
    public void onSlideExit(SlideExitDirection direction, View dst, Activity activity) {
        activity.finish();
    }
}
