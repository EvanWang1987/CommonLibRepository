package com.github.evan.common_utils.ui.view.dragView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Evan on 2017/12/22.
 */
public class DragView extends View{
    private ViewGroup mParent;
    private ViewGroup.LayoutParams mLayoutParams;
    private int mDownX, mDownY, mLastX, mLastY;

    public DragView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mLayoutParams = getLayoutParams();
        mParent = (ViewGroup) getParent();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN){
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastX = mDownX;
            mLastY = mDownY;
        }else if(actionMasked == MotionEvent.ACTION_MOVE){
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int offsetX = currentX - mLastX;
            int offsetY = currentY - mLastY;
            mLastX = offsetX;
            mLastY = offsetY;
            int parentWidth = mParent.getWidth();
            int parentHeight = mParent.getHeight();
            int left = getLeft();
            int right = getRight();
            int top = getTop();
            int bottom = getBottom();

            left = left + offsetX;
            right = right - offsetX;
            top = top + offsetY;
            right = bottom - offsetY;

            if(left < 0){
                left = 0;
            }

            if(top < 0){
                top = 0;
            }

            if(right > parentWidth){
                right = parentWidth;
            }

            if(bottom > parentHeight){
                bottom = parentHeight;
            }

            this.layout(left, top, right, bottom);
        }else if(actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            mDownX = 0;
            mDownY = 0;
            mLastX = 0;
            mLastY = 0;
        }
        return true;
    }
}
