package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by Evan on 2017/11/25.
 */
public class TouchEventInterceptor {
    private float mDownX, mDownY;
    private int mTouchSlop;

    public TouchEventInterceptor(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean interceptTouchEvent(MotionEvent event, InterceptMode interceptMode, View dst, boolean isHandleParallelSlide) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = event.getX();
            mDownY = event.getY();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            float offsetX = Math.abs(currentX - mDownX);
            float offsetY = Math.abs(currentY - mDownY);
            boolean isHorizontalSlide = offsetX > offsetY;
            boolean isParallelSlide = offsetX == offsetY;

            if (interceptMode == InterceptMode.ALL_BY_MYSELF || interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD) {
                if (offsetX >= mTouchSlop || offsetY >= mTouchSlop) {
                    return true;
                }
            } else if (interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL) {
                if (!isHandleParallelSlide && isParallelSlide) {
                    return false;
                } else {
                    if (!isHorizontalSlide || isParallelSlide) {
                        if (offsetY >= mTouchSlop) {
                            return true;
                        }
                    }
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL) {
                if (!isHandleParallelSlide && isParallelSlide) {
                    return false;
                } else {
                    if (isHorizontalSlide || isParallelSlide) {
                        if (offsetX >= mTouchSlop) {
                            return true;
                        }
                    }
                }
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
            if (interceptMode == InterceptMode.ALL_BY_MYSELF) {
                dst.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }

        return false;
    }
}
