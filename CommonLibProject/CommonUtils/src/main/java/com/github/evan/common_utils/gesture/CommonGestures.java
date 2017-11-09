package com.github.evan.common_utils.gesture;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by Evan on 2017/11/8.
 */
public class CommonGestures {
    public static final int SCALE_STATE_BEGIN = 0;
    public static final int SCALE_STATE_SCALING = 1;
    public static final int SCALE_STATE_END = 2;

    private boolean mGestureEnabled;
    private float mParentWidth, mParentHeight;

    private GestureDetectorCompat mDoubleTapGestureDetector;
    private GestureDetectorCompat mTapGestureDetector;
    private ScaleGestureDetector mScaleDetector;


    public CommonGestures(Context context, float parentWidth, float parentHeight) {
        mParentWidth = parentWidth;
        mParentHeight = parentHeight;
        if (mParentWidth <= 0 || mParentHeight <= 0) {
            throw new IllegalArgumentException("Parent width and height can not less than zero.");
        }
        mDoubleTapGestureDetector = new GestureDetectorCompat(context, new DoubleTapGestureListener());
        mTapGestureDetector = new GestureDetectorCompat(context, new TapGestureListener());
        mScaleDetector = new ScaleGestureDetector(context, new ScaleDetectorListener());
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (mListener == null)
            return false;

        if (null == event)
            return false;

        if (mTapGestureDetector.onTouchEvent(event))
            return true;

        if (event.getPointerCount() > 1) {
            try {
                if (mScaleDetector != null && mScaleDetector.onTouchEvent(event))
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mDoubleTapGestureDetector.onTouchEvent(event))
            return true;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                mListener.onGestureEnd();
                break;
        }

        return true;
    }

    private class TapGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if (mListener != null)
                mListener.onSingleTap();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mListener != null && mGestureEnabled)
                mListener.onLongPress();
        }
    }

    private class ScaleDetectorListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (mListener != null && mGestureEnabled)
                mListener.onScale(detector.getScaleFactor(), SCALE_STATE_SCALING, detector);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (mListener != null && mGestureEnabled)
                mListener.onScale(0F, SCALE_STATE_END, detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            if (mListener != null && mGestureEnabled)
                mListener.onScale(0F, SCALE_STATE_BEGIN, detector);
            return true;
        }
    }

    private class DoubleTapGestureListener extends SimpleOnGestureListener {
        private boolean mDown = false;

        @Override
        public boolean onDown(MotionEvent event) {
            mDown = true;
            return super.onDown(event);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mListener != null && mGestureEnabled && e1 != null && e2 != null) {
                if (mDown) {
                    mListener.onGestureBegin();
                    mDown = false;
                }

                float wholeDistanceX = Math.abs(e2.getX() - e1.getX()), wholeDistanceY = Math.abs(e2.getY() - e1.getY());
                if (wholeDistanceX >= wholeDistanceY) {
                    float oldX = e1.getX(), oldY = e1.getY();
                    float horizontalPercent = (oldX - e2.getX(0)) / mParentWidth;
                    float verticalPpercent = (oldY - e2.getY(0)) / mParentHeight;

                    mListener.onHorizontalSlide(horizontalPercent, verticalPpercent, e2.getX() - e1.getX(), e2.getY() - e1.getY());
                } else {
                    float oldX = e1.getX(), oldY = e1.getY();
                    float horizontalPercent = (oldX - e2.getX(0)) / mParentWidth;
                    float verticalPpercent = (oldY - e2.getY(0)) / mParentHeight;

                    mListener.onVerticalSlide(horizontalPercent, verticalPpercent, e2.getX() - e1.getX(), e2.getY() - e1.getY());
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            if (mListener != null && mGestureEnabled)
                mListener.onDoubleTap();
            return super.onDoubleTap(event);
        }
    }

    public void setTouchListener(TouchListener l, boolean enable) {
        mListener = l;
        mGestureEnabled = enable;
    }

    private TouchListener mListener;

    public interface TouchListener {
        public void onGestureBegin();

        public void onGestureEnd();

        public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY);

        public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY);

        public void onSingleTap();

        public void onDoubleTap();

        public void onScale(float scaleFactor, int state, ScaleGestureDetector detector);

        public void onLongPress();
    }
}
