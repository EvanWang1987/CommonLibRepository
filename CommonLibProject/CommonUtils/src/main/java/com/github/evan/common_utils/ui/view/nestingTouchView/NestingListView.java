package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */

public class NestingListView extends ListView implements TouchEventInterceptor.TouchInterceptable {
    private TouchEventInterceptor mInterceptor;
    private TouchEventInterceptor.InterceptMode mInterceptMode = TouchEventInterceptor.InterceptMode.VERTICAL_BY_ITSELF;

    public NestingListView(Context context) {
        super(context);
    }

    public NestingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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
