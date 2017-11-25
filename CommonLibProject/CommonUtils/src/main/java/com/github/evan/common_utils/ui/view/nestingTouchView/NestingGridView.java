package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */
public class NestingGridView extends GridView implements TouchEventInterceptor.TouchInterceptable {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF;

    public NestingGridView(Context context) {
        super(context);
    }

    public NestingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    public NestingGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInterceptMode = convertInterceptModeFromAttrs(attrs);
    }

    @Override
    public TouchEventInterceptor.InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NestingGridView);
        int anInt = typedArray.getInt(R.styleable.NestingGridView_grid_view_intercept_mode, TouchEventInterceptor.InterceptMode.UNKNOWN.value);
        TouchEventInterceptor.InterceptMode interceptMode = TouchEventInterceptor.InterceptMode.valueOf(anInt);
        typedArray.recycle();
        return interceptMode;
    }

    @Override
    public final boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInterceptor.onInterceptTouchEvent(ev, mInterceptMode, this) || super.onInterceptTouchEvent(ev);
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
