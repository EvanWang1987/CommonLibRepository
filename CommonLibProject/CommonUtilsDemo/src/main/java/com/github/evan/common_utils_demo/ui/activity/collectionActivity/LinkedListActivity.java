package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.slideExitActivity.BaseSlideExitActivity;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/25.
 */

public class LinkedListActivity extends BaseSlideExitActivity {
    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_linked_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public SlideExitActivityConfig onCreateConfig() {
        SlideExitActivityConfig config = new SlideExitActivityConfig();
        config.setBackgroundColor(ResourceUtil.getColor(com.github.evan.common_utils.R.color.White));
        config.setSlidingPercentRelativeActivityWhenNotExit(0.3f);
        config.setExitDirection(SlideExitDirection.LEFT_TO_RIGHT);
        config.setExitDuration(200);
        config.setRollBackDuration(300);
        return config;
    }

    @Override
    public void onSlideExit(SlideExitDirection direction, View dst, Activity activity) {
        activity.finish();
    }
}
