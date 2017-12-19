package com.github.evan.common_utils.ui.view.slideExitView;

import android.app.Activity;
import android.view.View;

import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;

/**
 * Created by Evan on 2017/12/19.
 */

public interface OnSlideExitListener {

    void onSlideExit(SlideExitDirection direction, View dst, Activity activity);

}
