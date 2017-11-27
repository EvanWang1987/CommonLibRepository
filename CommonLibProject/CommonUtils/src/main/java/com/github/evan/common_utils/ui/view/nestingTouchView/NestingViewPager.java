package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;


/**
 * Created by Evan on 2017/11/26.
 */
public class NestingViewPager extends ViewPager implements Nestable, ThresholdSwitchable {
    private InterceptMode mInterceptMode = InterceptMode.HORIZONTAL;
    private TouchEventInterceptor mInterceptor;

    public NestingViewPager(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
    }

    public NestingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingViewPager, 0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            super.onInterceptTouchEvent(event);
        }
        return mInterceptor.interceptTouchEvent(event, mInterceptMode, this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onTouchEvent(ev);
        }
        boolean result = mInterceptor.interceptTouchEvent(ev, mInterceptMode, this, this);
        return result && super.onTouchEvent(ev);
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        int anInt = typedArray.getInt(R.styleable.NestingViewPager_nesting_view_pager_touch_intercept_mode, InterceptMode.HORIZONTAL_BUT_THRESHOLD.value);
        InterceptMode interceptMode = InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
    }

    @Override
    public void setInterceptMode(InterceptMode mode) {
        if (null == mode) {
            return;
        }
        mInterceptMode = mode;
    }

    @Override
    public InterceptMode getInterceptMode() {
        return mInterceptMode;
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
            PagerAdapter adapter = getAdapter();
            int currentItem = getCurrentItem();

            boolean isArriveLeftThreshold = currentItem == 0;
            boolean isArriveRightThreshold = null == adapter ? isArriveLeftThreshold : currentItem == adapter.getCount() - 1;
            return xDirection == TouchEventDirection.LEFT_TO_RIGHT ? isArriveLeftThreshold : isArriveRightThreshold;
        }
        return false;
    }
}
