package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;


/**
 * Created by Evan on 2017/11/26.
 */
public class NestingHorizontalScrollView extends HorizontalScrollView implements Nestable, ThresholdSwitchable {
    private InterceptMode mInterceptMode = InterceptMode.HORIZONTAL;
    private TouchEventInterceptor mInterceptor;

    public NestingHorizontalScrollView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int style){
        mInterceptor = new TouchEventInterceptor(context);
        if(null != attrs){
            mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingHorizontalScrollView, style);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN){
            super.onInterceptTouchEvent(event);
        }else if(actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onInterceptTouchEvent(event);
        }
        //Make sense super do itself's work.

        return mInterceptor.interceptTouchEvent(event, mInterceptMode, this, this);
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if(interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD){
            int scrollX = getScrollX();
            int thisWidth = this.getWidth();
            int childWidth = getChildAt(0).getWidth();
            boolean isChildLargeThanThis = childWidth >= thisWidth;
            boolean isArriveLeftThreshold = scrollX <= 0;
            boolean isArriveRightThreshold = isChildLargeThanThis ? scrollX >= childWidth - thisWidth : isArriveLeftThreshold;
            return xDirection == TouchEventDirection.LEFT_TO_RIGHT ? isArriveLeftThreshold : isArriveRightThreshold;
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        int anInt = typedArray.getInt(R.styleable.NestingHorizontalScrollView_nesting_horizontal_scroll_view_touch_intercept_mode, InterceptMode.HORIZONTAL.value);
        InterceptMode interceptMode = InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
    }

    @Override
    public void setInterceptMode(InterceptMode mode) {
        if(null == mode)
            return;

        mInterceptMode = mode;
    }

    @Override
    public InterceptMode getInterceptMode() {
        return mInterceptMode;
    }
}
