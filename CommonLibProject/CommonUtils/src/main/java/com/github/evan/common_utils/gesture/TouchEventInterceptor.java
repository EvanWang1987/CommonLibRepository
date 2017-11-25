package com.github.evan.common_utils.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


/**
 * Created by Evan on 2017/11/24.
 */
public class TouchEventInterceptor {

    /**
     * 拦截模式
     *
     * unknown & use_view_default 都使用destination View的默认处理方式
     *
     * all_by_itself 所有手势都拦截
     *
     * horizontal_by_itself 只拦截横向手势
     *
     * vertical_by_itself 只拦截竖向手势
     */
    public enum InterceptMode {
        UNKNOWN(-1), USE_VIEW_DEFAULT(0), ALL_BY_ITSELF(1), HORIZONTAL_BY_ITSELF(2), VERTICAL_BY_ITSELF(3);

        public int value;

        InterceptMode(int value) {
            this.value = value;
        }

        public static InterceptMode valueOf(int value) {
            InterceptMode returnValue = UNKNOWN;
            InterceptMode[] values = values();
            int N = values.length;
            for (int i = 0; i < N; i++) {
                InterceptMode temp = values[i];
                if (temp.value == value) {
                    returnValue = temp;
                    break;
                }
            }

            return returnValue;
        }
    }

    public interface TouchInterceptable {

        InterceptMode convertInterceptModeFromAttrs(AttributeSet attrs);

        void setInterceptMode(TouchEventInterceptor.InterceptMode interceptMode);

        TouchEventInterceptor.InterceptMode getInterceptMode();

    }


    private float mDownX = -1, mDownY = -1;
    private float mTouchSlop;

    public TouchEventInterceptor(Context context) {
        mTouchSlop = ViewConfiguration.get(context.getApplicationContext()).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent event, InterceptMode interceptMode, ViewGroup destination) {
        if (interceptMode == InterceptMode.UNKNOWN || interceptMode == InterceptMode.USE_VIEW_DEFAULT) {
            return false;
        }

        if (interceptMode == InterceptMode.ALL_BY_ITSELF) {
            destination.getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }

        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        if ((action == MotionEvent.ACTION_DOWN)) {
            mDownX = event.getX();
            mDownY = event.getY();
            return false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float currentX = event.getX();
            float currentY = event.getY();

            float offsetX = Math.abs(currentX - mDownX);
            float offsetY = Math.abs(currentY - mDownY);
            boolean isHorizontalScroll = offsetX > offsetY;
            boolean isAchieveScrollOffset = isHorizontalScroll ? offsetX >= mTouchSlop : offsetY >= mTouchSlop;

            if (interceptMode == InterceptMode.HORIZONTAL_BY_ITSELF) {
                //外部设置拦截横向
                if(isHorizontalScroll){
                    //本次也是横向滑动
                    if(isAchieveScrollOffset){
                        //到达最短滑动距离
                        destination.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }else{
                        mDownX = -1;
                        mDownY = -1;
                        destination.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }else{
                    mDownX = -1;
                    mDownY = -1;
                    destination.getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            } else {
                //外部设置拦截竖向
                if(!isHorizontalScroll){
                    //本次也是竖向滑动
                    if(isAchieveScrollOffset){
                        //到达了最短滑动距离
                        destination.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }else{
                        mDownX = -1;
                        mDownY = -1;
                        destination.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }else{
                    mDownX = -1;
                    mDownY = -1;
                    destination.getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }

        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mDownX = -1;
            mDownY = -1;
            destination.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        } else {
            destination.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
        }
    }

}
