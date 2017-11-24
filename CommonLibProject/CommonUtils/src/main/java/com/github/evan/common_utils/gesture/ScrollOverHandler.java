package com.github.evan.common_utils.gesture;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by Evan on 2017/11/24.
 */
public class ScrollOverHandler {

    /**
     * 处理模式
     * <p>
     * unknown & use_view_default 都使用destination View的默认处理方式
     * <p>
     * all_by_itself 所有手势都拦截
     * <p>
     * horizontal_by_itself 只拦截横向手势
     * <p>
     * vertical_by_itself 只拦截竖向手势
     */
    public enum ScrollOverHandleMode {
        UNKNOWN(-1), USE_VIEW_DEFAULT(0), ALL_BY_ITSELF(1), HORIZONTAL_BY_ITSELF(2), HORIZONTAL_UTIL_SCROLL_OVER_LEFT_RIGHT_SIDE(3), VERTICAL_BY_ITSELF(4), VERTICAL_UTIL_SCROLL_OVER_TOP_BOTTOM_SIDE(5);

        public int value;

        ScrollOverHandleMode(int value) {
            this.value = value;
        }

        public static ScrollOverHandleMode valueOf(int value) {
            ScrollOverHandleMode returnValue = UNKNOWN;
            ScrollOverHandleMode[] values = values();
            int N = values.length;
            for (int i = 0; i < N; i++) {
                ScrollOverHandleMode temp = values[i];
                if (temp.value == value) {
                    returnValue = temp;
                    break;
                }
            }

            return returnValue;
        }
    }

    public interface ScrollOverHandlable {

        void setScrollOverHandleMode(TouchEventInterceptor.InterceptMode interceptMode);

        ScrollOverHandleMode getScrollOverHandleMode();

    }


    private float mDownX = 0, mDownY = 0;
    private float mDownXBetweenParentLeft = 0, mDownXBetweenParentRight = 0;
    private float mDownYBetweenParentTop = 0, mDownYBetweenParentBottom = 0;
    private float mTouchSlop;

    public ScrollOverHandler(Context context) {
        mTouchSlop = ViewConfiguration.get(context.getApplicationContext()).getScaledTouchSlop();
    }

    public boolean onTouchEvent(MotionEvent event, ScrollOverHandleMode scrollOverHandleMode, ViewGroup destination) {
        if (scrollOverHandleMode == ScrollOverHandleMode.UNKNOWN || scrollOverHandleMode == ScrollOverHandleMode.USE_VIEW_DEFAULT) {
            return destination.onTouchEvent(event);
        }

        if (scrollOverHandleMode == ScrollOverHandleMode.ALL_BY_ITSELF) {
            destination.getParent().requestDisallowInterceptTouchEvent(true);
            return destination.onTouchEvent(event);
        }

        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        if ((action == MotionEvent.ACTION_DOWN)) {
            mDownX = event.getX();
            mDownY = event.getY();

            int left = 0;
            int top = 0;
            int right = destination.getWidth();
            int bottom = destination.getHeight();

            mDownXBetweenParentLeft = Math.abs(mDownX - left);
            mDownXBetweenParentRight = Math.abs(mDownX - top);
            mDownYBetweenParentTop = Math.abs(mDownY - right);
            mDownYBetweenParentBottom = Math.abs(mDownY - bottom);
            return destination.onTouchEvent(event);
        } else if (action == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();

            float offsetX = Math.abs(currentX - mDownX);
            float offsetY = Math.abs(currentY - mDownY);
            boolean isHorizontalScroll = offsetX > offsetY;
            boolean isAchieveScrollOffset = isHorizontalScroll ? offsetX >= mTouchSlop : offsetY >= mTouchSlop;
            boolean isTopToBottomScroll = currentY >= mDownY;
            boolean isBottomToTopScroll = currentY < mDownY;
            boolean isLeftToRightScroll = currentX >= mDownX;
            boolean isRightToLeftScroll = currentX < mDownX;
            boolean isScrollOverLeftSide = offsetX >= mDownXBetweenParentLeft;
            boolean isScrollOverRightSide = offsetX >= mDownXBetweenParentRight;
            boolean isScrollOverTopSide = offsetY >= mDownYBetweenParentTop;
            boolean isScrollOverBottomSide = offsetY >= mDownYBetweenParentBottom;


            if (isHorizontalScroll && isAchieveScrollOffset) {
                //本次是横向滑动
                if (scrollOverHandleMode == ScrollOverHandleMode.HORIZONTAL_BY_ITSELF) {
                    //所有横向都处理
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                    return destination.onTouchEvent(event);
                } else if (scrollOverHandleMode == ScrollOverHandleMode.HORIZONTAL_UTIL_SCROLL_OVER_LEFT_RIGHT_SIDE) {
                    //滑动到两边尽头之内
                    if (isLeftToRightScroll) {
                        if (!isScrollOverLeftSide) {
                            destination.getParent().requestDisallowInterceptTouchEvent(true);
                            return destination.onTouchEvent(event);
                        } else {
                            destination.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    } else if (isRightToLeftScroll) {
                        if (!isScrollOverRightSide) {
                            destination.getParent().requestDisallowInterceptTouchEvent(true);
                            return destination.onTouchEvent(event);
                        } else {
                            destination.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    } else {
                        return destination.onTouchEvent(event);
                    }
                } else {
                    return destination.onTouchEvent(event);
                }
            } else if (!isHorizontalScroll && isAchieveScrollOffset) {
                //本次是竖向滑动
                if (scrollOverHandleMode == ScrollOverHandleMode.VERTICAL_BY_ITSELF) {
                    //所有竖向都处理
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                } else if (scrollOverHandleMode == ScrollOverHandleMode.VERTICAL_UTIL_SCROLL_OVER_TOP_BOTTOM_SIDE) {
                    //只处理滑动到上下两边之内
                    if (isTopToBottomScroll) {
                        if (!isScrollOverTopSide) {
                            destination.getParent().requestDisallowInterceptTouchEvent(true);
                            return destination.onTouchEvent(event);
                        } else {
                            destination.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    } else if (isBottomToTopScroll) {
                        if (!isScrollOverBottomSide) {
                            destination.getParent().requestDisallowInterceptTouchEvent(true);
                            return destination.onTouchEvent(event);
                        } else {
                            destination.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    } else {
                        return destination.onTouchEvent(event);
                    }
                } else {
                    return destination.onTouchEvent(event);
                }
            } else {
                return destination.onTouchEvent(event);
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
            mDownXBetweenParentLeft = 0;
            mDownXBetweenParentRight = 0;
            mDownYBetweenParentTop = 0;
            mDownYBetweenParentBottom = 0;
            return destination.onTouchEvent(event);
        } else {
            return destination.onTouchEvent(event);
        }
    }


}
