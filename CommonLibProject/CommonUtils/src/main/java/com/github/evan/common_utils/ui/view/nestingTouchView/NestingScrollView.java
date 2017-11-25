package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;
import com.github.evan.common_utils.gesture.TouchEventScrollOverHandler;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/11/24.
 */
public class NestingScrollView extends ScrollView implements TouchEventInterceptor.TouchInterceptable, TouchEventScrollOverHandler.IsAtScrollOverThresholdListener {
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF;
    private TouchEventInterceptor mTouchInterceptor;
    private TouchEventScrollOverHandler mScrollOverHandler = new TouchEventScrollOverHandler(false);

    public NestingScrollView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NestingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NestingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mTouchInterceptor = new TouchEventInterceptor(context);
        if (null != attrs) {
            mInterceptMode = convertInterceptModeFromAttrs(attrs);
        }
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent ev) {
        return mTouchInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent ev) {
        return mScrollOverHandler.onTouchEvent(ev, this, this) || super.onTouchEvent(ev);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingScrollView);
        int anInt = typedArray.getInt(R.styleable.NestingScrollView_scroll_view_intercept_mode, TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF.value);
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
    public boolean isAtScrollOverThreshold(TouchEventScrollOverHandler.ScrollDirection xDirection, TouchEventScrollOverHandler.ScrollDirection yDirection, boolean isHorizontalScroll) {
        if (mInterceptMode == TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF) {
            ViewGroup parent = (ViewGroup) getParent();
            View child = getChildAt(0);
            int scrollY = getScrollY();
            int parentHeight = parent.getHeight();
            int childHeight = child.getHeight();
            boolean isChildLargeThanParent = childHeight > parentHeight;
            int largestScrollY = isChildLargeThanParent ? Math.abs(parentHeight - childHeight) : 0;
            boolean isAtScrollOverThreshold = yDirection == TouchEventScrollOverHandler.ScrollDirection.TOP_2_BOTTOM ? scrollY <= 0 : scrollY >= largestScrollY;
            Logger.d("NestingScrollView");
            Logger.d("isHorizontalScroll: " + isHorizontalScroll);
            Logger.d("isAtScrollOverThreshold: " + isAtScrollOverThreshold);
            return isAtScrollOverThreshold;
        }
        return false;
    }

}
