package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;


/**
 * Created by Evan on 2017/11/27.
 */
public class NestingRecyclerView extends RecyclerView implements Nestable, ThresholdSwitchable {
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.VERTICAL;
    private ThresholdSwitcher mThresholdSwitcher;
    private boolean mIsHandleParallelSlide = false;


    public NestingRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NestingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NestingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        mThresholdSwitcher = new ThresholdSwitcher(context);
        mInterceptor = new TouchEventInterceptor(context);
        if(null != attrs){
            mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingRecyclerView, defStyle);
        }
    }

    public boolean isHandleParallelSlide() {
        return mIsHandleParallelSlide;
    }

    public void setHandleParallelSlide(boolean isHandleParallelSlide) {
        this.mIsHandleParallelSlide = isHandleParallelSlide;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this, mIsHandleParallelSlide);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int actionMasked = e.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onInterceptTouchEvent(e);
        }
        return mInterceptor.interceptTouchEvent(e, mInterceptMode, this, true);
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if(interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD){
            return yDirection == TouchEventDirection.TOP_TO_BOTTOM ? canScrollVertically(-1) : canScrollVertically(1);
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingRecyclerView_nesting_recycler_view_handle_parallel_Slide, mIsHandleParallelSlide);
        int anInt = typedArray.getInt(R.styleable.NestingRecyclerView_nesting_recycler_view_touch_intercept_mode, InterceptMode.VERTICAL.value);
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
