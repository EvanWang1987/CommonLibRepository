package com.github.evan.common_utils_demo.ui.activity.slideExitActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.view.slideExitView.OnSlideExitListener;
import com.github.evan.common_utils.ui.view.slideExitView.SlideExitLayout;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/18.
 */
public class SlideExitDemoActivity extends BaseActivity implements OnSlideExitListener {
    public static final String SLIDE_EXIT_DIRECTION = "slide_exit_direction";

    @BindView(R.id.slide_exit_layout)
    SlideExitLayout mSlideExitLayout;
    @BindView(R.id.txt_desc_slide_exit_demo_activity)
    TextView mTxtDesc;
    private SlideExitDirection mSlideExitDirection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            int anInt = extras.getInt(SLIDE_EXIT_DIRECTION, SlideExitDirection.LEFT_TO_RIGHT.value);
            mSlideExitDirection = SlideExitDirection.valueOf(anInt);
        }
        mTxtDesc.setText(mSlideExitDirection.toString());
        mSlideExitLayout.setExitListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_slide_exit_demo;
    }

    @Override
    public void onSlideExit(SlideExitDirection direction, View dst, Activity activity) {
        activity.finish();
    }
}
