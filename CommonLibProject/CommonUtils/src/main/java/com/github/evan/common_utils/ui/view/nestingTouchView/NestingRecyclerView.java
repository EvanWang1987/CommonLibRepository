package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
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
 * Created by Evan on 2017/11/27.
 */
public class NestingRecyclerView extends RecyclerView implements Nestable, ThresholdSwitchable {
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.VERTICAL;
    private ThresholdSwitcher mThresholdSwitcher;
    private boolean mIsHandleParallelSlide = false;
    private boolean mIsNestedInSameInterceptModeParent = false;


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

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mThresholdSwitcher = new ThresholdSwitcher(context);
        mInterceptor = new TouchEventInterceptor(context);
        if (null != attrs) {
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
//        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, this, mIsHandleParallelSlide);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN || actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            super.onInterceptTouchEvent(event);
            if(actionMasked == MotionEvent.ACTION_DOWN){
                mThresholdSwitcher.setDownXAndDownY(event.getX(), event.getY());
            }
        }

        return mInterceptor.interceptTouchEvent(event, mInterceptMode, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mThresholdSwitcher.dispatchThreshold(e, mInterceptMode, this, this, mIsHandleParallelSlide, mIsNestedInSameInterceptModeParent);
        return super.onTouchEvent(e);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        try {
            Class<RecyclerView> recyclerViewClass = RecyclerView.class;

            Field mOnItemTouchListenersField = recyclerViewClass.getDeclaredField("mOnItemTouchListeners");
            mOnItemTouchListenersField.setAccessible(true);
            ArrayList<OnItemTouchListener> onItemTouchListeners = (ArrayList<OnItemTouchListener>) mOnItemTouchListenersField.get(this);
            final int listenerCount = onItemTouchListeners.size();
            for (int i = 0; i < listenerCount; i++) {
                final OnItemTouchListener listener = onItemTouchListeners.get(i);
                listener.onRequestDisallowInterceptTouchEvent(disallowIntercept);
            }

            Field mGroupFlagsField = recyclerViewClass.getSuperclass().getDeclaredField("mGroupFlags");
            mGroupFlagsField.setAccessible(true);
            int mGroupFlags = (int) mGroupFlagsField.get(this);

            Field flag_disallow_interceptField = recyclerViewClass.getSuperclass().getDeclaredField("FLAG_DISALLOW_INTERCEPT");
            flag_disallow_interceptField.setAccessible(true);
            int FLAG_DISALLOW_INTERCEPT = (int) flag_disallow_interceptField.get(this);

            Field mParentField = recyclerViewClass.getSuperclass().getSuperclass().getDeclaredField("mParent");
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
        if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                boolean isVertical = linearLayoutManager.getOrientation() == VERTICAL;
                if (isVertical) {
                    return yDirection == TouchEventDirection.TOP_TO_BOTTOM ? !canScrollVertically(-1) : !canScrollVertically(1);
                } else {
                    boolean isArrive = xDirection == TouchEventDirection.LEFT_TO_RIGHT ? !canScrollHorizontally(-1) : !canScrollHorizontally(1);
                    return isArrive;
                }
            } else {
                return yDirection == TouchEventDirection.TOP_TO_BOTTOM ? !canScrollVertically(-1) : !canScrollVertically(1);
            }
        }
        return false;
    }

    @Override
    public InterceptMode pickupInterceptMode(AttributeSet attr, int[] declareStyleable, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attr, declareStyleable);
        mIsHandleParallelSlide = typedArray.getBoolean(R.styleable.NestingRecyclerView_nesting_recycler_view_handle_parallel_Slide, mIsHandleParallelSlide);
        mIsNestedInSameInterceptModeParent = typedArray.getBoolean(R.styleable.NestingRecyclerView_nesting_recycler_view_is_nested_in_same_intercept_mode_parent, mIsNestedInSameInterceptModeParent);
        int anInt = typedArray.getInt(R.styleable.NestingRecyclerView_nesting_recycler_view_touch_intercept_mode, InterceptMode.VERTICAL.value);
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
            Class<RecyclerView> recyclerViewClass = RecyclerView.class;

            Field mOnItemTouchListenersField = recyclerViewClass.getDeclaredField("mOnItemTouchListeners");
            mOnItemTouchListenersField.setAccessible(true);
            ArrayList<OnItemTouchListener> onItemTouchListeners = (ArrayList<OnItemTouchListener>) mOnItemTouchListenersField.get(this);
            final int listenerCount = onItemTouchListeners.size();
            for (int i = 0; i < listenerCount; i++) {
                final OnItemTouchListener listener = onItemTouchListeners.get(i);
                listener.onRequestDisallowInterceptTouchEvent(disallowIntercept);
            }

            Field mGroupFlagsField = recyclerViewClass.getSuperclass().getDeclaredField("mGroupFlags");
            mGroupFlagsField.setAccessible(true);
            int mGroupFlags = (int) mGroupFlagsField.get(this);

            Field flag_disallow_interceptField = recyclerViewClass.getSuperclass().getDeclaredField("FLAG_DISALLOW_INTERCEPT");
            flag_disallow_interceptField.setAccessible(true);
            int FLAG_DISALLOW_INTERCEPT = (int) flag_disallow_interceptField.get(this);

            Field mParentField = recyclerViewClass.getSuperclass().getSuperclass().getDeclaredField("mParent");
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
    public void setNestedInSameInterceptModeParent(boolean nested) {
        mIsNestedInSameInterceptModeParent = nested;
    }

    @Override
    public boolean isNestedInSameInterceptModeParent() {
        return mIsNestedInSameInterceptModeParent;
    }
}
