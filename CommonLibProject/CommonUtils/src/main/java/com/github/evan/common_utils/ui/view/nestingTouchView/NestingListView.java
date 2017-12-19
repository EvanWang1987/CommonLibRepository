package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;

/**
 * Created by Evan on 2017/12/7.
 */
public class NestingListView extends ListView implements Nestable, ThresholdSwitchable {
    private ThresholdSwitcher mThresholdSwitcher;
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.VERTICAL_BUT_THRESHOLD;
    private boolean mIsHandleParallelSlide = false;


    public NestingListView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NestingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NestingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mThresholdSwitcher = new ThresholdSwitcher(context);
        mInterceptor = new TouchEventInterceptor(context);
        if(null != attrs){
            mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingListView, defStyleAttr);
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
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onInterceptTouchEvent(ev);
        }
        return mInterceptor.interceptTouchEvent(ev, mInterceptMode, this, true);
    }

    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
            ListAdapter adapter = getAdapter();
            if (adapter == null) {
                return true;
            }
            boolean isArriveTopThreshold = !canScrollVertically(-1);
            boolean isArriveBottomThreshold = !canScrollVertically(1);
            return yDirection == TouchEventDirection.TOP_TO_BOTTOM ? isArriveTopThreshold : isArriveBottomThreshold;
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingListView_nesting_list_view_handle_parallel_Slide, mIsHandleParallelSlide);
        int anInt = typedArray.getInt(R.styleable.NestingListView_nesting_list_view_touch_intercept_mode, InterceptMode.VERTICAL_BUT_THRESHOLD.value);
        InterceptMode interceptMode = InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
    }

    @Override
    public void setInterceptMode(InterceptMode mode) {
        if (null == mode)
            return;

        mInterceptMode = mode;
    }

    @Override
    public InterceptMode getInterceptMode() {
        return mInterceptMode;
    }
}
