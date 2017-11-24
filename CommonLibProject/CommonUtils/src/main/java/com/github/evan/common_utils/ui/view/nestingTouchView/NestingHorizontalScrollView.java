package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */

public class NestingHorizontalScrollView extends HorizontalScrollView implements TouchEventInterceptor.TouchInterceptable {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;

    public NestingHorizontalScrollView(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInterceptor = new TouchEventInterceptor(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
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
