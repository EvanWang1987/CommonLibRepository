package com.github.evan.common_utils.ui.activity.slideExitActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.view.slideExitView.OnSlideExitListener;
import com.github.evan.common_utils.ui.view.slideExitView.SlideExitLayout;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Evan on 2017/12/18.
 */
public abstract class BaseSlideExitActivity extends BaseActivity implements OnSlideExitListener {
    public abstract View onCreateView(LayoutInflater inflater);
    public abstract SlideExitActivityConfig onCreateConfig();

    private SlideExitLayout mSlideExitLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlideExitLayout = (SlideExitLayout) findViewById(R.id.slide_exit_layout_in_base_slide_exit_activity);
        View view = onCreateView(getLayoutInflater());
        SlideExitActivityConfig config = onCreateConfig();
        if(null == view || null == config){
            throw new IllegalArgumentException("Sub View or SlideExitActivityConfig can not be null!");
        }

        int backgroundColor = config.getBackgroundColor();
        SlideExitDirection exitDirection = config.getExitDirection();
        long rollBackDuration = config.getRollBackDuration();
        long exitDuration = config.getExitDuration();
        float slidingPercentRelativeActivityWhenNotExit = config.getSlidingPercentRelativeActivityWhenNotExit();
        mSlideExitLayout.setSlidingPercentRelativeActivityWhenNotExit(slidingPercentRelativeActivityWhenNotExit);
        mSlideExitLayout.setSlideExitDirection(exitDirection);
        mSlideExitLayout.setSlideExitDuration(exitDuration);
        mSlideExitLayout.setRollbackDuration(rollBackDuration);
        mSlideExitLayout.setBackgroundColor(backgroundColor);
        mSlideExitLayout.setExitListener(this);
        mSlideExitLayout.replaceContentView(view);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_slide_out;
    }
}
