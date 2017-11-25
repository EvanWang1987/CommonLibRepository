package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */
public class NestingScrollView extends ScrollView implements TouchEventInterceptor.TouchInterceptable {
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF;
    private TouchEventInterceptor mTouchInterceptor;

    public NestingScrollView(Context context) {
        super(context);
        mTouchInterceptor = new TouchEventInterceptor(context);
    }

    public NestingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    public NestingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchInterceptor = new TouchEventInterceptor(context);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent ev) {
        return mTouchInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingScrollView);
        int anInt = typedArray.getInt(R.styleable.NestingScrollView_scroll_view_intercept_mode, TouchEventInterceptor.InterceptMode.UNKNOWN.value);
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
}
