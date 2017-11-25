package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */
public class NestingViewPager extends ViewPager implements TouchEventInterceptor.TouchInterceptable {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;


    public NestingViewPager(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
    }

    public NestingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingViewPager);
        int anInt = typedArray.getInt(R.styleable.NestingViewPager_view_pager_intercept_mode, TouchEventInterceptor.InterceptMode.UNKNOWN.value);
        TouchEventInterceptor.InterceptMode interceptMode = TouchEventInterceptor.InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
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
