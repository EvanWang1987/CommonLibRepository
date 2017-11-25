package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;
import com.github.evan.common_utils.gesture.TouchEventScrollOverHandler;

/**
 * Created by Evan on 2017/11/24.
 */

public class NestingHorizontalScrollView extends HorizontalScrollView implements TouchEventInterceptor.TouchInterceptable, TouchEventScrollOverHandler.IsAtScrollOverThresholdListener, ViewTreeObserver.OnGlobalLayoutListener {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;
    private TouchEventScrollOverHandler mScrollOverHandler = new TouchEventScrollOverHandler(false);
    private int mViewWidth, mParentWidth;

    public NestingHorizontalScrollView(Context context) {
        super(context);
        mInterceptor = new TouchEventInterceptor(context);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    public NestingHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    @Override
    public void onGlobalLayout() {
        mViewWidth = getWidth();
        ViewGroup parent = (ViewGroup) getParent();
        mParentWidth = parent.getWidth();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }else{
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingHorizontalScrollView);
        int anInt = typedArray.getInt(R.styleable.NestingHorizontalScrollView_horizontal_scroll_view_intercept_mode, TouchEventInterceptor.InterceptMode.UNKNOWN.value);
        TouchEventInterceptor.InterceptMode interceptMode = TouchEventInterceptor.InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
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
    public void setInterceptMode(TouchEventInterceptor.InterceptMode interceptMode) {
        mInterceptMode = interceptMode;
    }

    @Override
    public TouchEventInterceptor.InterceptMode getInterceptMode() {
        return mInterceptMode;
    }

    @Override
    public boolean isAtScrollOverThreshold(TouchEventScrollOverHandler.ScrollDirection xDirection, TouchEventScrollOverHandler.ScrollDirection yDirection) {
        if(mInterceptMode == TouchEventInterceptor.InterceptMode.ALL_BY_ITSELF || mInterceptMode == TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF){
            int scrollX = getScrollX();
            boolean isAtScrollOverThreshold = xDirection == TouchEventScrollOverHandler.ScrollDirection.LEFT_2_RIGHT ? scrollX <= 0 : scrollX >= mParentWidth - mViewWidth;
            return isAtScrollOverThreshold;
        }
        return false;
    }


}
