package com.github.evan.common_utils.ui.view.pullable_view;

/**
 * Created by Evan on 2018/2/5.
 */

public interface PullChecker {

    boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide);

}
