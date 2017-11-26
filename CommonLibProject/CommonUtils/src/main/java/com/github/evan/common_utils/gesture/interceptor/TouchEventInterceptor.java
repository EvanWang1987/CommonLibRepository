package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
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

    public boolean interceptTouchEvent(MotionEvent event, InterceptMode interceptMode, ViewGroup destination, ThresholdSwitchable thresholdSwitchable) {
        if (interceptMode == InterceptMode.USE_SUPER_DEFAULT) {
            return true;
        }

        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = event.getX();
            mDownY = event.getY();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            float offsetX = Math.abs(currentX - mDownX);
            float offsetY = Math.abs(currentY - mDownY);
            boolean isHorizontalScroll = offsetX >= offsetY;
            boolean isAchieveHorizontalScrollSlop = offsetX >= mTouchSlop;
            boolean isAchieveVerticalScrollSlop = offsetY >= mTouchSlop;
            TouchEventDirection xDirection = currentX >= mDownX ? TouchEventDirection.LEFT_TO_RIGHT : TouchEventDirection.RIGHT_TO_LEFT;
            TouchEventDirection yDirection = currentY >= mDownY ? TouchEventDirection.TOP_TO_BOTTOM : TouchEventDirection.BOTTOM_TO_TOP;
            boolean isArriveTouchEventThreshold = thresholdSwitchable.isArriveTouchEventThreshold(interceptMode, xDirection, yDirection);
//            Log.d("Evan", "isArriveTouchEventThreshold: " + isArriveTouchEventThreshold);


            if (interceptMode == InterceptMode.ALL_BY_MYSELF) {
                if (isAchieveHorizontalScrollSlop || isAchieveVerticalScrollSlop) {
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
            } else if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD) {
                if (isAchieveHorizontalScrollSlop || isAchieveVerticalScrollSlop) {
                    if (!isArriveTouchEventThreshold) {
                        destination.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    } else {
                        destination.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL) {
                if (isHorizontalScroll && isAchieveHorizontalScrollSlop) {
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD) {
                if (isHorizontalScroll && isAchieveHorizontalScrollSlop) {
                    if (!isArriveTouchEventThreshold) {
                        destination.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                } else {
                    destination.getParent().requestDisallowInterceptTouchEvent(false);
                }
            } else if (interceptMode == InterceptMode.VERTICAL) {
                if (!isHorizontalScroll && isAchieveVerticalScrollSlop) {
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
            } else if (interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                if (!isHorizontalScroll && isAchieveVerticalScrollSlop) {
                    if (!isArriveTouchEventThreshold) {
                        destination.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    } else {
                        destination.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }

        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
        }
        return false;
    }


}
