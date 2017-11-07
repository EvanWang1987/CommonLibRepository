package com.github.evan.common_utils.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/2.
 */
public class LoadingPager extends FrameLayout {
    private Drawable mLoadingDrawable, mUnknownDrawable, mLoadEmptyDrawable, mNetUnAvailableDrawable, mNetTimeoutDrawable;
    private CharSequence mLoadingText, mUnknownText, mLoadEmptyText, mNetUnAvailableText, mNetTimeoutText;
    private ImageView mStatusDrawable;
    private TextView mStatusText;
    private LinearLayout mStatusContainer;
    private boolean mIsLoadingWithRotateAnim = true;
    private ObjectAnimator mRotateAnim;
    private @ColorInt int mStatusTextColor;
    private @Dimension int mStatusTextSize;


    public enum LoadingStatus {
        IDLE, LOADING, UNKNOWN_ERROR, LOAD_EMPTY, NET_UNAVAILABLE, NET_TIME_OUT,
    }

    private LoadingStatus mLoadingStatus;

    public LoadingPager(@NonNull Context context) {
        this(context, null, 0);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        mStatusContainer = new LinearLayout(context);
        mStatusDrawable = new ImageView(context);
        mStatusText = new TextView(context);
        mStatusContainer.setOrientation(LinearLayout.VERTICAL);
        mStatusContainer.setGravity(Gravity.CENTER);

        LayoutParams containerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams drawableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        containerParams.gravity = Gravity.CENTER;

        mStatusContainer.addView(mStatusDrawable, drawableParams);
        mStatusContainer.addView(mStatusText, textParams);
        this.addView(mStatusContainer, containerParams);
        mRotateAnim = ObjectAnimator.ofFloat(mStatusDrawable, "rotation", 0f, 360f);
        mRotateAnim.setRepeatCount(ObjectAnimator.INFINITE);
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingPager);
            mLoadingDrawable = typedArray.getDrawable(R.styleable.LoadingPager_loadingDrawable);
            mLoadingText = typedArray.getText(R.styleable.LoadingPager_loadingText);
            mUnknownDrawable = typedArray.getDrawable(R.styleable.LoadingPager_loadingDrawable);
            mUnknownText = typedArray.getText(R.styleable.LoadingPager_loadingText);
            mLoadEmptyDrawable = typedArray.getDrawable(R.styleable.LoadingPager_loadingDrawable);
            mLoadEmptyText = typedArray.getText(R.styleable.LoadingPager_loadingText);
            mNetUnAvailableDrawable = typedArray.getDrawable(R.styleable.LoadingPager_loadingDrawable);
            mNetUnAvailableText = typedArray.getText(R.styleable.LoadingPager_loadingText);
            mNetTimeoutDrawable = typedArray.getDrawable(R.styleable.LoadingPager_loadingDrawable);
            mNetTimeoutText = typedArray.getText(R.styleable.LoadingPager_loadingText);
            mStatusTextColor = typedArray.getColor(R.styleable.LoadingPager_textColor, ResourceUtil.getColor(R.color.DarkGray));
            mStatusTextSize = (int) typedArray.getDimension(R.styleable.LoadingPager_textSize, DensityUtil.sp2px(16));
            mIsLoadingWithRotateAnim = typedArray.getBoolean(R.styleable.LoadingPager_isLoadingWithRotateAnimation, true);
            typedArray.recycle();
        } else {
            mLoadingDrawable = ResourceUtil.getDrawable(R.mipmap.status_loading);
            mUnknownDrawable = ResourceUtil.getDrawable(R.mipmap.status_unknown_error);
            mLoadEmptyDrawable = ResourceUtil.getDrawable(R.mipmap.status_empty_data);
            mNetUnAvailableDrawable = ResourceUtil.getDrawable(R.mipmap.status_net_unavailable);
            mNetTimeoutDrawable = ResourceUtil.getDrawable(R.mipmap.status_net_timeout);
            mLoadingText = ResourceUtil.getString(R.string.loading_pager_loading);
            mUnknownText = ResourceUtil.getString(R.string.loading_pager_unknown_error);
            mLoadEmptyText = ResourceUtil.getString(R.string.loading_pager_empty_data);
            mNetUnAvailableText = ResourceUtil.getString(R.string.loading_pager_net_unavailable);
            mNetTimeoutText = ResourceUtil.getString(R.string.loading_pager_net_timeout);
            mStatusTextColor = ResourceUtil.getColor(R.color.DarkGray);
            mStatusTextSize = DensityUtil.sp2px(16);
            mIsLoadingWithRotateAnim = true;
        }
    }
    
    private void startRotateAnim(){
        if(mIsLoadingWithRotateAnim){
            mRotateAnim.start();
        }
    }
    
    private void stopRotateAnim(){
        if(mIsLoadingWithRotateAnim){
            if(mRotateAnim.isRunning()){
                mRotateAnim.cancel();
            }
        }
    }

    public LoadingStatus getLoadingStatus() {
        return mLoadingStatus;
    }

    public void setLoadingStatus(LoadingStatus loadingStatus) {
        this.mLoadingStatus = loadingStatus;
        switch (mLoadingStatus){
            case IDLE:
                mStatusDrawable.setVisibility(INVISIBLE);
                mStatusText.setVisibility(INVISIBLE);
                stopRotateAnim();
                break;

            case LOADING:
                mStatusDrawable.setImageDrawable(mLoadingDrawable);
                mStatusText.setText(mLoadingText);
                startRotateAnim();
                break;

            case UNKNOWN_ERROR:
                mStatusDrawable.setImageDrawable(mLoadingDrawable);
                mStatusText.setText(mLoadingText);
                stopRotateAnim();
                break;

            case LOAD_EMPTY:
                mStatusDrawable.setImageDrawable(mLoadingDrawable);
                mStatusText.setText(mLoadingText);
                stopRotateAnim();
                break;

            case NET_UNAVAILABLE:
                mStatusDrawable.setImageDrawable(mLoadingDrawable);
                mStatusText.setText(mLoadingText);
                stopRotateAnim();
                break;

            case NET_TIME_OUT:
                mStatusDrawable.setImageDrawable(mLoadingDrawable);
                mStatusText.setText(mLoadingText);
                stopRotateAnim();
                break;
        }

        if(mLoadingStatus != LoadingStatus.IDLE){
            mStatusDrawable.setVisibility(VISIBLE);
            mStatusText.setVisibility(VISIBLE);
        }
    }

    public int getStatusTextColor() {
        return mStatusTextColor;
    }

    public void setStatusTextColor(int statusTextColor) {
        this.mStatusTextColor = statusTextColor;
    }

    public int getStatusTextSize() {
        return mStatusTextSize;
    }

    public void setStatusTextSize(int statusTextSize) {
        this.mStatusTextSize = statusTextSize;
    }

    public Drawable getLoadingDrawable() {
        return mLoadingDrawable;
    }

    public void setLoadingDrawable(Drawable loadingDrawable) {
        this.mLoadingDrawable = loadingDrawable;
    }

    public Drawable getUnknownDrawable() {
        return mUnknownDrawable;
    }

    public void setUnknownDrawable(Drawable unknownDrawable) {
        this.mUnknownDrawable = unknownDrawable;
    }

    public Drawable getLoadEmptyDrawable() {
        return mLoadEmptyDrawable;
    }

    public void setLoadEmptyDrawable(Drawable loadEmptyDrawable) {
        this.mLoadEmptyDrawable = loadEmptyDrawable;
    }

    public Drawable getNetUnAvailableDrawable() {
        return mNetUnAvailableDrawable;
    }

    public void setNetUnAvailableDrawable(Drawable netUnAvailableDrawable) {
        this.mNetUnAvailableDrawable = netUnAvailableDrawable;
    }

    public Drawable getNetTimeoutDrawable() {
        return mNetTimeoutDrawable;
    }

    public void setNetTimeoutDrawable(Drawable netTimeoutDrawable) {
        this.mNetTimeoutDrawable = netTimeoutDrawable;
    }

    public CharSequence getLoadingText() {
        return mLoadingText;
    }

    public void setLoadingText(CharSequence loadingText) {
        this.mLoadingText = loadingText;
    }

    public CharSequence getUnknownText() {
        return mUnknownText;
    }

    public void setUnknownText(CharSequence unknownText) {
        this.mUnknownText = unknownText;
    }

    public CharSequence getLoadEmptyText() {
        return mLoadEmptyText;
    }

    public void setmLoadEmptyText(CharSequence loadEmptyText) {
        this.mLoadEmptyText = loadEmptyText;
    }

    public CharSequence getNetUnAvailableText() {
        return mNetUnAvailableText;
    }

    public void setNetUnAvailableText(CharSequence netUnAvailableText) {
        this.mNetUnAvailableText = netUnAvailableText;
    }

    public CharSequence getNetTimeoutText() {
        return mNetTimeoutText;
    }

    public void setNetTimeoutText(CharSequence netTimeoutText) {
        this.mNetTimeoutText = netTimeoutText;
    }
}
