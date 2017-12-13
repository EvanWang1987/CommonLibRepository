package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Evan on 2017/12/13.
 */

public class DisableSlideNestingViewPager extends NestingViewPager {
    private boolean mIsDisableSlide = true;


    public DisableSlideNestingViewPager(Context context) {
        super(context);
    }

    public DisableSlideNestingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isDisableSlide() {
        return mIsDisableSlide;
    }

    public void setDisableSlide(boolean isDisableSlide) {
        this.mIsDisableSlide = isDisableSlide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !mIsDisableSlide && super.onInterceptTouchEvent(event);
    }
}
