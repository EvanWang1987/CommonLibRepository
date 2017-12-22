package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/11/25.
 */
public class TouchEventInterceptor {
    private float mDownX, mDownY, mLastX, mLastY;
    private int mTouchSlop;

    public TouchEventInterceptor(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean interceptTouchEvent(MotionEvent event, InterceptMode interceptMode, View dst, boolean isHandleParallelSlide) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = event.getX();
            mDownY = event.getY();
            mLastX = mDownX;
            mLastY = mDownY;
            if (interceptMode == InterceptMode.ALL_BY_MYSELF || interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                dst.getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            float offsetX = Math.abs(currentX - mDownX);
            float offsetY = Math.abs(currentY - mDownY);
            boolean isHorizontalSlide = offsetX > offsetY;
            boolean isParallelSlide = offsetX == offsetY;
            TouchEventDirection xDirection = currentX - mLastX >= 0 ? TouchEventDirection.LEFT_TO_RIGHT : TouchEventDirection.RIGHT_TO_LEFT;
            TouchEventDirection yDirection = currentY - mLastY >= 0 ? TouchEventDirection.TOP_TO_BOTTOM : TouchEventDirection.BOTTOM_TO_TOP;
            mLastX = currentX;
            mLastY = currentY;

            if (interceptMode == InterceptMode.ALL_BY_MYSELF || interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD) {
                if (offsetX >= mTouchSlop || offsetY >= mTouchSlop) {
                    return true;
                }
            } else if (interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD || interceptMode == InterceptMode.VERTICAL || interceptMode == InterceptMode.VERTICAL_ONLY_TOP_TO_BOTTOM || interceptMode == InterceptMode.VERTICAL_ONLY_BOTTOM_TO_TOP) {
                if (!isHandleParallelSlide && isParallelSlide) {
                    return false;
                } else {
                    if (!isHorizontalSlide || isParallelSlide) {
                        if (offsetY >= mTouchSlop) {
                            if(interceptMode == InterceptMode.VERTICAL_ONLY_BOTTOM_TO_TOP){
                                if(yDirection == TouchEventDirection.BOTTOM_TO_TOP){
                                    return true;
                                }
                            }else if(interceptMode == InterceptMode.VERTICAL_ONLY_TOP_TO_BOTTOM){
                                if(yDirection == TouchEventDirection.TOP_TO_BOTTOM){
                                    return true;
                                }
                            }else{
                                return true;
                            }
                        }
                    }
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD || interceptMode == InterceptMode.HORIZONTAL || interceptMode == InterceptMode.HORIZONTAL_ONLY_LEFT_TO_RIGHT || interceptMode == InterceptMode.HORIZONTAL_ONLY_RIGHT_TO_LEFT) {
                if (!isHandleParallelSlide && isParallelSlide) {
                    return false;
                } else {
                    if (isHorizontalSlide || isParallelSlide) {
                        if (offsetX >= mTouchSlop) {
                            if(interceptMode == InterceptMode.HORIZONTAL_ONLY_LEFT_TO_RIGHT){
                                if(xDirection == TouchEventDirection.LEFT_TO_RIGHT){
                                    return true;
                                }
                            }else if(interceptMode == InterceptMode.HORIZONTAL_ONLY_RIGHT_TO_LEFT){
                                if(xDirection == TouchEventDirection.RIGHT_TO_LEFT){
                                    return true;
                                }
                            }else{
                                return true;
                            }
                        }
                    }
                }
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
