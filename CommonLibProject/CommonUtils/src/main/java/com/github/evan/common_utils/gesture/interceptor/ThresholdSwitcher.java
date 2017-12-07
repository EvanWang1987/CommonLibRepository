package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Evan on 2017/12/7.
 */

public class ThresholdSwitcher {
    private float mDownX, mDownY, mLastX, mLastY;
    private int mTouchSlop;

    public ThresholdSwitcher(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean dispatchThreshold(MotionEvent event, InterceptMode interceptMode, View dst, ThresholdSwitchable thresholdSwitchable) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            if (interceptMode == InterceptMode.ALL_BY_MYSELF || interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                dst.getParent().requestDisallowInterceptTouchEvent(true);
            }
            mDownX = event.getX();
            mDownY = event.getY();
            mLastX = mDownX;
            mLastY = mDownY;
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            float offsetX = currentX - mLastX;
            float offsetY = currentY - mLastY;
            mLastX = currentX;
            mLastY = currentY;
            if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                boolean arriveTouchEventThreshold = thresholdSwitchable.isArriveTouchEventThreshold(interceptMode, offsetX >= 0 ? TouchEventDirection.LEFT_TO_RIGHT : TouchEventDirection.RIGHT_TO_LEFT, offsetY >= 0 ? TouchEventDirection.TOP_TO_BOTTOM : TouchEventDirection.BOTTOM_TO_TOP);
                dst.getParent().requestDisallowInterceptTouchEvent(!arriveTouchEventThreshold);
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
            mLastX = 0;
            mLastY = 0;
        }
        return false;
    }
}
