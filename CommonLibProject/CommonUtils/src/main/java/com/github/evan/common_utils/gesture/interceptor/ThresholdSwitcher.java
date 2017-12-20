package com.github.evan.common_utils.gesture.interceptor;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.github.evan.common_utils.ui.view.nestingTouchView.Nestable;

/**
 * Created by Evan on 2017/12/7.
 */
public class ThresholdSwitcher {
    private float mDownX, mDownY;
    private int mTouchSlop;

    public ThresholdSwitcher(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
    }

    public void setDownXAndDownY(float downX, float downY) {
        mDownX = downX;
        mDownY = downY;
    }

    public void dispatchThreshold(MotionEvent event, InterceptMode interceptMode, View dst, ThresholdSwitchable thresholdSwitchable, boolean isHandleParallelSlide, boolean isNestedInSameInterceptModeParent) {
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
            TouchEventDirection xDirection = offsetX >= 0 ? TouchEventDirection.LEFT_TO_RIGHT : TouchEventDirection.RIGHT_TO_LEFT;
            TouchEventDirection yDirection = offsetY >= 0 ? TouchEventDirection.TOP_TO_BOTTOM : TouchEventDirection.BOTTOM_TO_TOP;

            boolean isArriveTouchEventThreshold = thresholdSwitchable.isArriveTouchEventThreshold(interceptMode, xDirection, yDirection);
            if (interceptMode == InterceptMode.ALL_BY_MYSELF) {
                //永远不解开
            } else if (interceptMode == InterceptMode.ALL_BY_MYSELF_BUT_THRESHOLD) {
                if (isArriveTouchEventThreshold) {
                    ViewParent parent = dst.getParent();
                    while (parent != null) {
                        if (parent instanceof Nestable) {
                            Nestable nestable = (Nestable) parent;
                            nestable.requestDisallowInterceptTouchEventJustToParent(false);
                            break;
                        }
                        parent = parent.getParent();
                    }
                    dst.getParent().requestDisallowInterceptTouchEvent(false);
                }
            } else if (interceptMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD) {
                if (!isHorizontalSlide || (isParallelSlide && !isHandleParallelSlide)) {
                    if (isArriveVerticalSlop) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (isArriveTouchEventThreshold) {
                        if (isNestedInSameInterceptModeParent) {
                            ViewParent parent = dst.getParent();
                            while (parent != null) {
                                if (parent instanceof Nestable) {
                                    Nestable nestable = (Nestable) parent;
                                    InterceptMode parentMode = nestable.getInterceptMode();
                                    if (parentMode == InterceptMode.HORIZONTAL || parentMode == InterceptMode.HORIZONTAL_BUT_THRESHOLD) {
                                        nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        break;
                                    }else if(parentMode == InterceptMode.HORIZONTAL_ONLY_LEFT_TO_RIGHT){
                                        if(xDirection == TouchEventDirection.LEFT_TO_RIGHT){
                                            nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        }
                                    }else if(parentMode == InterceptMode.HORIZONTAL_ONLY_RIGHT_TO_LEFT){
                                        if(xDirection == TouchEventDirection.RIGHT_TO_LEFT){
                                            nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        }
                                    }
                                }
                                parent = parent.getParent();
                            }
                        } else {
                            dst.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
            } else if (interceptMode == InterceptMode.VERTICAL_BUT_THRESHOLD) {
                if (isHorizontalSlide || (isParallelSlide && !isHandleParallelSlide)) {
                    if (isArriveHorizontalSlop) {
                        dst.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else {
                    if (isArriveTouchEventThreshold) {
                        if (isNestedInSameInterceptModeParent) {
                            ViewParent parent = dst.getParent();
                            while (parent != null) {
                                if (parent instanceof Nestable) {
                                    Nestable nestable = (Nestable) parent;
                                    InterceptMode parentMode = nestable.getInterceptMode();
                                    if (parentMode == InterceptMode.VERTICAL || parentMode == InterceptMode.VERTICAL_BUT_THRESHOLD || parentMode == InterceptMode.VERTICAL_ONLY_TOP_TO_BOTTOM || parentMode == InterceptMode.VERTICAL_ONLY_BOTTOM_TO_TOP) {
                                        nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        break;
                                    }else if(parentMode == InterceptMode.VERTICAL_ONLY_TOP_TO_BOTTOM){
                                        if(yDirection == TouchEventDirection.TOP_TO_BOTTOM){
                                            nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        }
                                    }else if(parentMode == InterceptMode.VERTICAL_ONLY_BOTTOM_TO_TOP){
                                        if(yDirection == TouchEventDirection.BOTTOM_TO_TOP){
                                            nestable.requestDisallowInterceptTouchEventJustToParent(false);
                                        }
                                    }
                                }
                                parent = parent.getParent();
                            }
                        }else{
                            dst.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
        }
    }
}
