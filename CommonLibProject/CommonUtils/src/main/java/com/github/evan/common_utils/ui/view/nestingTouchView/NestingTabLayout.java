package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;

/**
 * Created by Evan on 2017/12/19.
 */
public class NestingTabLayout extends TabLayout implements Nestable, ThresholdSwitchable {
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.HORIZONTAL;
    private ThresholdSwitcher mThresholdSwitcher;
    private boolean mIsHandleParallelSlide = false;



    public NestingTabLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NestingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NestingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mInterceptor = new TouchEventInterceptor(context);
        mThresholdSwitcher = new ThresholdSwitcher(context);
        if(null != attrs){
            mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingTabLayout, defStyleAttr);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this, mIsHandleParallelSlide);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_MOVE || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onInterceptTouchEvent(ev);
        }
        return mInterceptor.interceptTouchEvent(ev, mInterceptMode, this, mIsHandleParallelSlide);
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if(interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD){
            int scrollX = getScrollX();
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            int width = viewGroup.getWidth();
            int tabLayoutWidth = this.getWidth();
            boolean isChildLargeThanParent = width > tabLayoutWidth;
            boolean isArriveLeft = scrollX <= 0;
            boolean isArriveRight = !isChildLargeThanParent || scrollX >= width - tabLayoutWidth;
            return xDirection == TouchEventDirection.LEFT_TO_RIGHT ? isArriveLeft : isArriveRight;
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        int anInt = typedArray.getInt(R.styleable.NestingTabLayout_nesting_tab_layout_touch_intercept_mode, InterceptMode.HORIZONTAL.value);
        InterceptMode interceptMode = InterceptMode.valueOf(anInt);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingTabLayout_nesting_tab_layout_handle_parallel_Slide, false);
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
