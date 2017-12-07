package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/26.
 */
public class NestingScrollView extends ScrollView implements Nestable, ThresholdSwitchable {
    private InterceptMode mInterceptMode = InterceptMode.VERTICAL;
    private TouchEventInterceptor mInterceptor;
    private ThresholdSwitcher mThresholdSwitcher;
    private boolean mIsHandleParallelSlide = false;


    public NestingScrollView(Context context) {
        super(context);
        init(context, null, R.styleable.NestingScrollView, 0);
    }

    public NestingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, R.styleable.NestingScrollView, 0);
    }

    public NestingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, R.styleable.NestingViewPager, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int[] declareStyleable, int style){
        mThresholdSwitcher = new ThresholdSwitcher(context);
        mInterceptor = new TouchEventInterceptor(context);
        if(null != attrs){
            mInterceptMode = pickupInterceptMode(attrs, declareStyleable, style);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            //保证父类初始化数据
            super.onInterceptTouchEvent(event);
        }
        return mInterceptor.interceptTouchEvent(event, mInterceptMode, this, true);
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if(interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD){
            int scrollY = getScrollY();
            int thisHeight = getHeight();
            int childHeight = getChildAt(0).getHeight();
            boolean isChildLargeThanThis = childHeight >= thisHeight;
            int maxScrollY = childHeight - thisHeight;

            boolean isArrivedTopThreshold = scrollY <= 0;
            boolean isArrivedBottomThreshold = isChildLargeThanThis ? scrollY >= maxScrollY : isArrivedTopThreshold;
            return yDirection == TouchEventDirection.TOP_TO_BOTTOM ? isArrivedTopThreshold : isArrivedBottomThreshold;
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingScrollView_nesting_scroll_view_handle_parallel_Slide, mIsHandleParallelSlide);
        int anInt = typedArray.getInt(R.styleable.NestingScrollView_nesting_scroll_view_touch_intercept_mode, InterceptMode.VERTICAL.value);
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
