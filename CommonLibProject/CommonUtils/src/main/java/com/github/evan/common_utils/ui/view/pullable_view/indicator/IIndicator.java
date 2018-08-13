package com.github.evan.common_utils.ui.view.pullable_view.indicator;

import android.view.View;

import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;

/**
 * Created by Evan on 2018/2/5.
 */
public interface IIndicator {

    View getIndicatorView();

    void onPullStatusChange(PullStatus status);

    void onDistanceChange(int dX, int dY, float dXPercentRelativeParent, float dYPercentRelativeParent);

    void onSlideOverIndicator(boolean isSlideOverIndicator);

    void onDistanceChangeWhenSlideOverIndicator(int dX, int dY, float dXPercentRelativeParent, float dYPercentRelativeParent);

}
