package com.github.evan.common_utils.gesture;

import android.view.MotionEvent;
import android.view.ViewGroup;

import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/11/25.
 * <p>
 * 配合TouchEventInterceptor进行相同InterceptMode的ViewGroup嵌套情况下的手势拦截处理
 */
public class TouchEventScrollOverHandler {

    public enum ScrollDirection {
        UN_KNOWN, LEFT_2_RIGHT, RIGHT_2_LEFT, TOP_2_BOTTOM, BOTTOM_2_TOP;
    }

    public interface IsAtScrollOverThresholdListener {

        boolean isAtScrollOverThreshold(ScrollDirection xDirection, ScrollDirection yDirection);
    }

    /**
     * 是否处理手势滑到尽头后，继续滑动而进来的ACTION_MOVE事件
     * <p>
     * true --> 当前ViewGroup继续处理
     * false --> 交给父ViewGroup处理
     */
    private boolean mIsHandleScrollOverEvent = false;
    private float mDownX, mDownY;

    public TouchEventScrollOverHandler(boolean isHandleScrollOverEvent) {
        this.mIsHandleScrollOverEvent = isHandleScrollOverEvent;
    }

    public boolean onTouchEvent(MotionEvent event, ViewGroup destination, IsAtScrollOverThresholdListener listener) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            mDownX = event.getX();
            mDownY = event.getY();
            destination.getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();
            ScrollDirection xDirection = currentX >= mDownX ? ScrollDirection.LEFT_2_RIGHT : ScrollDirection.RIGHT_2_LEFT;
            ScrollDirection yDirection = currentY >= mDownY ? ScrollDirection.TOP_2_BOTTOM : ScrollDirection.BOTTOM_2_TOP;
            boolean isAtScrollOverThreshold = listener.isAtScrollOverThreshold(xDirection, yDirection);
            Logger.d("isAtScrollOverThreshold: " + isAtScrollOverThreshold);
            if (isAtScrollOverThreshold) {
                if(mIsHandleScrollOverEvent){
                    destination.getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    destination.getParent().requestDisallowInterceptTouchEvent(false);
                }
            } else {
                destination.getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
        }
        return false;
    }
}
