package com.github.evan.common_utils.ui.view.viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/23.
 */
public class InterceptTouchViewPager extends ViewPager implements TouchInterceptable {
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;
    private TouchEventInterceptor mInterceptor;

    public InterceptTouchViewPager(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
    }

    public InterceptTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mInterceptor.onInterceptTouchEvent(event, mInterceptMode, this) || super.onInterceptTouchEvent(event);
    }

    @Override
    public void setInterceptMode(TouchEventInterceptor.InterceptMode interceptMode) {
        mInterceptMode = interceptMode;
    }

    @Override
    public TouchEventInterceptor.InterceptMode getInterceptMode() {
        return mInterceptMode;
    }
}
