package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;
import com.github.evan.common_utils.utils.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by Evan on 2017/11/26.
 */
public class NestingViewPager extends ViewPager implements Nestable, ThresholdSwitchable {
    private InterceptMode mInterceptMode = InterceptMode.HORIZONTAL;
    private TouchEventInterceptor mInterceptor;
    private ThresholdSwitcher mThresholdSwitcher;
    private boolean mIsHandleParallelSlide = true;
    private boolean mIsNestedInSameInterceptModeParent = false;

    public NestingViewPager(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
        mThresholdSwitcher = new ThresholdSwitcher(context);
    }

    public NestingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
        mThresholdSwitcher = new ThresholdSwitcher(context);
        mInterceptMode = pickupInterceptMode(attrs, R.styleable.NestingViewPager, 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        mThresholdSwitcher.dispatchThreshold(event, mInterceptMode, this, this, mIsHandleParallelSlide);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            mThresholdSwitcher.setDownXAndDownY(event.getX(), event.getY());
        }

        boolean intercept = mInterceptor.interceptTouchEvent(event, mInterceptMode, this, true);
        if(intercept){
            event.setAction(MotionEvent.ACTION_DOWN);
            super.onInterceptTouchEvent(event);
        }

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this, mIsHandleParallelSlide, mIsNestedInSameInterceptModeParent);
        return super.onTouchEvent(ev);
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingViewPager_nesting_view_pager_handle_parallel_Slide, mIsHandleParallelSlide);
        mIsNestedInSameInterceptModeParent = typedArray.getBoolean(R.styleable.NestingViewPager_nesting_view_pager_is_nested_in_same_intercept_mode_parent, mIsNestedInSameInterceptModeParent);
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
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public void requestDisallowInterceptTouchEventJustToParent(boolean disallowIntercept) {
        try {
            Class<ViewPager> viewPagerClass = ViewPager.class;

            Field mGroupFlagsField = viewPagerClass.getSuperclass().getDeclaredField("mGroupFlags");
            mGroupFlagsField.setAccessible(true);
            int mGroupFlags = (int) mGroupFlagsField.get(this);

            Field flag_disallow_interceptField = viewPagerClass.getSuperclass().getDeclaredField("FLAG_DISALLOW_INTERCEPT");
            flag_disallow_interceptField.setAccessible(true);
            int FLAG_DISALLOW_INTERCEPT = (int) flag_disallow_interceptField.get(this);

            Field mParentField = viewPagerClass.getSuperclass().getSuperclass().getDeclaredField("mParent");
            mParentField.setAccessible(true);
            ViewParent mParent = (ViewParent) mParentField.get(this);

            if (disallowIntercept == ((mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0)) {
                // We're already in this state, assume our ancestors are too
                return;
            }

            if (disallowIntercept) {
                mGroupFlags |= FLAG_DISALLOW_INTERCEPT;
                mGroupFlagsField.set(this, mGroupFlags);
                mParent.requestDisallowInterceptTouchEvent(true);
            } else {
                mGroupFlags &= ~FLAG_DISALLOW_INTERCEPT;
                mGroupFlagsField.set(this, mGroupFlags);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
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

    @Override
    public void setNestedInSameInterceptModeParent(boolean nested) {
        mIsNestedInSameInterceptModeParent = nested;
    }

    @Override
    public boolean isNestedInSameInterceptModeParent() {
        return mIsNestedInSameInterceptModeParent;
    }
}
