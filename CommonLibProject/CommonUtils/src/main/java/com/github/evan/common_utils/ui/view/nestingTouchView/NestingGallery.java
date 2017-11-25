package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;
import com.github.evan.common_utils.gesture.TouchEventScrollOverHandler;

/**
 * Created by Evan on 2017/11/24.
 */

public class NestingGallery extends Gallery implements TouchEventInterceptor.TouchInterceptable, TouchEventScrollOverHandler.IsAtScrollOverThresholdListener {
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF;
    private TouchEventInterceptor mInterceptor;
    private TouchEventScrollOverHandler mScrollOverHandler = new TouchEventScrollOverHandler(false);

    public NestingGallery(Context context) {
        super(context);
    }

    public NestingGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    public NestingGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        return mScrollOverHandler.onTouchEvent(event, this, this) || super.onTouchEvent(event);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingGallery);
        int anInt = typedArray.getInt(R.styleable.NestingGallery_gallery_intercept_mode, TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF.value);
        TouchEventInterceptor.InterceptMode interceptMode = TouchEventInterceptor.InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
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
        SpinnerAdapter adapter = this.getAdapter();
        int currentItem = getSelectedItemPosition();
        if (mInterceptMode == TouchEventInterceptor.InterceptMode.ALL_BY_ITSELF || mInterceptMode == TouchEventInterceptor.InterceptMode.HORIZONTAL_BY_ITSELF) {
            if (xDirection == TouchEventScrollOverHandler.ScrollDirection.LEFT_2_RIGHT) {
                return currentItem == 0;
            } else {
                return adapter != null ? currentItem == (adapter.getCount() - 1) : currentItem == 0;
            }
        }
        return false;
    }
}
