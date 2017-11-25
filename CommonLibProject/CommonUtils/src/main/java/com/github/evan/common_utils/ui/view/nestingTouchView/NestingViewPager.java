package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;
import com.github.evan.common_utils.gesture.TouchEventScrollOverHandler;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/11/24.
 */
public class NestingViewPager extends ViewPager implements TouchEventInterceptor.TouchInterceptable, TouchEventScrollOverHandler.IsAtScrollOverThresholdListener {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;
    private TouchEventScrollOverHandler mScrollOverHandler = new TouchEventScrollOverHandler(false);

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
    public final boolean onTouchEvent(MotionEvent ev) {
        return mScrollOverHandler.onTouchEvent(ev, this, this) || super.onTouchEvent(ev);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingViewPager);
        int anInt = typedArray.getInt(R.styleable.NestingViewPager_view_pager_intercept_mode, TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF.value);
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


    @Override
    public boolean isAtScrollOverThreshold(TouchEventScrollOverHandler.ScrollDirection xDirection, TouchEventScrollOverHandler.ScrollDirection yDirection, boolean isHorizontalScroll) {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        if (mInterceptMode == TouchEventInterceptor.InterceptMode.ALL_BY_ITSELF || mInterceptMode == TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF) {
            if (xDirection == TouchEventScrollOverHandler.ScrollDirection.LEFT_2_RIGHT) {
                return currentItem == 0;
            } else {
                return adapter != null ? currentItem == (adapter.getCount() - 1) : currentItem == 0;
            }
        }
        return false;
    }
}
