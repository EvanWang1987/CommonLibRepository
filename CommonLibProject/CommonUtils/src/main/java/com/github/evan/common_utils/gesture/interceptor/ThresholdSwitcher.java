package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.github.evan.common_utils.utils.Logger;


/**
 * Created by Evan on 2017/12/7.
 */

public class ThresholdSwitcher {
    private float mDownX, mDownY;
    private int mTouchSlop;

    public ThresholdSwitcher(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
    }

    public void dispatchThreshold(MotionEvent event, InterceptMode interceptMode, View dst, ThresholdSwitchable thresholdSwitchable, boolean isHandleParallelSlide) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            if (interceptMode == InterceptMode.ALL_BY_MYSELF || interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                dst.getParent().requestDisallowInterceptTouchEvent(true);
            }
            mDownX = event.getX();
            mDownY = event.getY();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            float offsetX = currentX - mDownX;
            float offsetY = currentY - mDownY;
            boolean isHorizontalSlide = Math.abs(offsetX) > Math.abs(offsetY);
            boolean isParallelSlide = Math.abs(offsetX) == Math.abs(offsetY);
            boolean isArriveVerticalSlop = Math.abs(offsetY) >= mTouchSlop;
            boolean isArriveHorizontalSlop = Math.abs(offsetX) >= mTouchSlop;

            boolean isArriveTouchEventThreshold = thresholdSwitchable.isArriveTouchEventThreshold(interceptMode, offsetX >= 0 ? TouchEventDirection.LEFT_TO_RIGHT : TouchEventDirection.RIGHT_TO_LEFT, offsetY >= 0 ? TouchEventDirection.TOP_TO_BOTTOM : TouchEventDirection.BOTTOM_TO_TOP);
            if (interceptMode == InterceptMode.ALL_BY_MYSELF) {
                //永远不解开
            } else if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD) {
                if (isArriveTouchEventThreshold) {
                    dst.getParent().requestDisallowInterceptTouchEvent(false);
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD) {
                if (!isHorizontalSlide || (isParallelSlide && !isHandleParallelSlide)) {
                    if (isArriveVerticalSlop) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (isArriveTouchEventThreshold) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            } else if (interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                if (isHorizontalSlide || (isParallelSlide && !isHandleParallelSlide)) {
                    if (isArriveHorizontalSlop) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (isArriveTouchEventThreshold) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
        }
    }
}
