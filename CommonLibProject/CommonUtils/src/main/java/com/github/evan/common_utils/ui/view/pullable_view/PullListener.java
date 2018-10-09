package com.github.evan.common_utils.ui.view.pullable_view;

/**
 * Created by Evan on 2018/2/5.
 */

public interface PullListener {

    void onStartPull();

    void onInvoke(boolean isFromTopSide, boolean isFromBottomSide, boolean isFromLeftSide, boolean isFromRightSide);

}
