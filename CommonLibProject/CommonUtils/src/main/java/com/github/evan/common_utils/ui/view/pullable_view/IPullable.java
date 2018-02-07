package com.github.evan.common_utils.ui.view.pullable_view;

/**
 * Created by Evan on 2018/2/5.
 */
public interface IPullable {

    void setIndicatorDisplayMode(IndicatorDisplayMode mode);

    IndicatorDisplayMode getIndicatorDisplayMode();

    PullStatus getPullStatus();


}
