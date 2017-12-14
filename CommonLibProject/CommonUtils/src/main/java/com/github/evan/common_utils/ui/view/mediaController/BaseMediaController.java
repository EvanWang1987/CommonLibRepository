package com.github.evan.common_utils.ui.view.mediaController;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.github.evan.common_utils.gesture.CommonGestures;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitchable;
import com.github.evan.common_utils.gesture.interceptor.ThresholdSwitcher;
import com.github.evan.common_utils.gesture.interceptor.TouchEventDirection;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;
import com.github.evan.common_utils.utils.BrightnessUtil;
import com.github.evan.common_utils.utils.DensityUtil;

/**
 * Created by Evan on 2017/12/13.
 */
public abstract class BaseMediaController extends FrameLayout implements IMediaController, ThresholdSwitchable, ViewTreeObserver.OnGlobalLayoutListener, CommonGestures.TouchListener {
    private TouchEventInterceptor mInterceptor;
    private InterceptMode mInterceptMode = InterceptMode.ALL_BY_MYSELF;
    private ThresholdSwitcher mThresholdSwitcher;
    private float mParentWidth, mParentHeight;
    private CommonGestures mGestures;
    private boolean mIsEnableGesture = true;
    protected MediaControllerListener mListener;
    protected AudioManager mAudioManager;
    protected int mMaxVol;
    protected int mCurrentVol;
    protected int mMaxBirghtness = 255;
    private int mCurrentBrightness;

    public BaseMediaController(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseMediaController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseMediaController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setMediaControllerListener(MediaControllerListener listener) {
        this.mListener = listener;
    }

    private void init() {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mMaxVol = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentVol = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mCurrentBrightness = BrightnessUtil.getScreenBrightness(getContext());
        mInterceptor = new TouchEventInterceptor(getContext());
        mThresholdSwitcher = new ThresholdSwitcher(getContext());
        mGestures = new CommonGestures(getContext().getApplicationContext());
        mGestures.setTouchListener(this);
        mGestures.setGestureEnabled(mIsEnableGesture);
        initParentWidthAndHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mThresholdSwitcher.dispatchThreshold(ev, mInterceptMode, this, BaseMediaController.this, true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mInterceptor.interceptTouchEvent(ev, mInterceptMode, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestures.setGestureEnabled(mIsEnableGesture);
        return mGestures.onTouchEvent(event);
    }

    @Override
    public void onGlobalLayout() {
        mParentWidth = ((ViewGroup) getParent()).getWidth();
        mParentHeight = ((ViewGroup) getParent()).getHeight();
        mGestures.setParentHeight(mParentHeight);
        mGestures.setParentWidth(mParentWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    private void initParentWidthAndHeight() {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup parentGroup = (ViewGroup) parent;
            parentGroup.getViewTreeObserver().addOnGlobalLayoutListener(this);
        } else {
            mParentWidth = DensityUtil.getAppScreenWidthOfPx();
            mParentHeight = DensityUtil.getAppScreenHeightOfPx();
            mGestures.setParentHeight(mParentHeight);
            mGestures.setParentWidth(mParentWidth);
        }
    }


    @Override
    public boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection) {
        return false;
    }

    @Override
    public void onGestureBegin() {
        if(null != mListener){
            mListener.onGestureBegin();
        }
    }

    @Override
    public void onGestureEnd() {
        if(null != mListener){
            mListener.onGestureEnd();
        }
    }

    @Override
    public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        if(null != mListener){
            mListener.onHorizontalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
        }
    }

    @Override
    public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        if(null != mListener){
            mListener.onVerticalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
        }
    }

    @Override
    public void onSingleTap() {
        if(null != mListener){
            mListener.onSingleTap();
        }
    }

    @Override
    public void onDoubleTap() {
        if(null != mListener){
            mListener.onDoubleTap();
        }
    }

    @Override
    public void onScale(float scaleFactor, int state, ScaleGestureDetector detector) {
        if(null != mListener){
            mListener.onScale(scaleFactor, state, detector);
        }
    }

    @Override
    public void onLongPress() {
        if(null != mListener){
            mListener.onLongPress();
        }
    }



}
