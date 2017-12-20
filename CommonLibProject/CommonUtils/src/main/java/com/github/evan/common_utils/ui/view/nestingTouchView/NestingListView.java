package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Evan on 2017/12/7.
 */
public class NestingListView extends ListView implements Nestable, ThresholdSwitchable {
    private ThresholdSwitcher mThresholdSwitcher;
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.VERTICAL_BUT_THRESHOLD;
    private boolean mIsHandleParallelSlide = false;
    private boolean mIsNestedInSameInterceptModeParent = false;


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
//        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this, mIsHandleParallelSlide);
        return super.dispatchTouchEvent(ev);
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
        mIsNestedInSameInterceptModeParent = typedArray.getBoolean(R.styleable.NestingListView_nesting_list_view_is_nested_in_same_intercept_mode_parent, mIsNestedInSameInterceptModeParent);
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

    @Override
    public void requestDisallowInterceptTouchEventJustToParent(boolean disallowIntercept) {
        try {
            Class<ListView> listViewClass = ListView.class;

            Method recycleVelocityTrackerMethod = listViewClass.getDeclaredMethod("recycleVelocityTracker", null);
            recycleVelocityTrackerMethod.setAccessible(true);
            recycleVelocityTrackerMethod.invoke(this, null);

            Field mGroupFlagsField = listViewClass.getSuperclass().getDeclaredField("mGroupFlags");
            mGroupFlagsField.setAccessible(true);
            int mGroupFlags = (int) mGroupFlagsField.get(this);

            Field flag_disallow_interceptField = listViewClass.getSuperclass().getDeclaredField("FLAG_DISALLOW_INTERCEPT");
            flag_disallow_interceptField.setAccessible(true);
            int FLAG_DISALLOW_INTERCEPT = (int) flag_disallow_interceptField.get(this);

            Field mParentField = listViewClass.getSuperclass().getSuperclass().getDeclaredField("mParent");
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
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
