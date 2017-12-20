package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.util.AttributeSet;

import com.github.evan.common_utils.gesture.interceptor.InterceptMode;


/**
 * Created by Evan on 2017/11/26.
 */
public interface Nestable {

    InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style);

    void setInterceptMode(InterceptMode mode);

    InterceptMode getInterceptMode();

    void requestDisallowInterceptTouchEventJustToParent(boolean disallowIntercept);

    void setNestedInSameInterceptModeParent(boolean nested);

    boolean isNestedInSameInterceptModeParent();
}
