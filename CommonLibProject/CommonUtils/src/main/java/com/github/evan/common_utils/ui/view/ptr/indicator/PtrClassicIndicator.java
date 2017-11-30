package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.flagView.flagUpdater.TimeFlagUpdater;
import com.github.evan.common_utils.ui.view.ptr.PtrStatus;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.StringUtil;
import java.util.Locale;

/**
 * Created by Evan on 2017/11/30.
 */
public class PtrClassicIndicator extends LinearLayout implements IIndicator {
    private TimeFlagUpdater mFlagUpdater;
    private TextView mTxtTitle, mTxtDesc;
    private ImageView mIcProgress;
    private ObjectAnimator mRotationAnim;
    private PtrStatus mPtrStatus;
    private Drawable mProgressDrawable;
    private String mFlagName;
    private ProgressRotationDirection mRotationDirection;
    private boolean mIsRotationProgressWhenDragging;

    public PtrClassicIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public PtrClassicIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PtrClassicIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_classic, this, true);
        mTxtTitle = findViewById(R.id.txt_title_indicator_classic);
        mTxtDesc = findViewById(R.id.txt_desc_indicator_classic);
        mIcProgress = findViewById(R.id.ic_progress_indicator_classic);
        mRotationAnim = ObjectAnimator.ofFloat(mIcProgress, "rotation", 0f, 360f);
        mRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        mRotationAnim.setDuration(800);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        mRotationAnim.setInterpolator(linearInterpolator);
        if(null != attrs){
            initAttributes(context, attrs, R.styleable.PtrClassicIndicator);
        }
        mFlagUpdater = new TimeFlagUpdater(mFlagName);
        mIcProgress.setImageDrawable(mProgressDrawable);
    }

    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void setStatus(PtrStatus status) {
        mPtrStatus = status;
        Logger.d("---PtrStatus--- " + status);
        handleSelfWithPtrStatus(status);
    }

    @Override
    public PtrStatus getStatus() {
        return mPtrStatus;
    }

    @Override
    public void setOffsetY(int offsetFromDownPoint, int offsetFromLastMoved) {
        if(mIsRotationProgressWhenDragging){
            mIcProgress.setRotation(mRotationDirection == ProgressRotationDirection.CLOCKWISE ? offsetFromDownPoint : -offsetFromDownPoint);
        }
    }

    @Override
    public void setUpdateTime() {

    }

    @Override
    public void initAttributes(Context context, AttributeSet attrs, int[] styleable) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, styleable);
        mIsRotationProgressWhenDragging = typedArray.getBoolean(R.styleable.PtrClassicIndicator_is_progress_rotation_when_drag, true);
        int anInt = typedArray.getInt(R.styleable.PtrClassicIndicator_progress_rotation_direction, ProgressRotationDirection.CLOCKWISE.value);
        mRotationDirection = ProgressRotationDirection.valueOf(anInt);
        mProgressDrawable = typedArray.getDrawable(R.styleable.PtrClassicIndicator_progress_drawable);
        mFlagName = typedArray.getString(R.styleable.PtrClassicIndicator_update_time_flag);
        if(null == mProgressDrawable){
            mProgressDrawable = getResources().getDrawable(R.mipmap.icon_loading_small);
        }
        if(StringUtil.isEmpty(mFlagName)){
            mFlagName = StringUtil.toStringWithoutHashCode(getContext()) + " / " + this.getClass().getName();
        }
        typedArray.recycle();
    }

    private void handleSelfWithPtrStatus(PtrStatus status){
        switch (status){
            case IDLE:
                mRotationAnim.cancel();
                break;

            case START_PULL:
                mTxtTitle.setText(R.string.pulling);
                Long lastUpdateTime = mFlagUpdater.getFlag();
                String desc = "";
                if(lastUpdateTime != -1){
                    String time2String = DateUtil.time2String(lastUpdateTime, DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                    desc = ResourceUtil.getString(R.string.last_refresh_time, time2String);
                }
                mTxtDesc.setText(desc);
                break;

            case PULLING:
                mTxtTitle.setText(R.string.pulling);
                break;

            case RELEASE_TO_REFRESH:
                mTxtTitle.setText(R.string.release_to_refresh);
                break;

            case REFRESHING:
                mTxtTitle.setText(R.string.refreshing);
                float rotation = mIcProgress.getRotation();
                mRotationAnim.setFloatValues(rotation, mRotationDirection == ProgressRotationDirection.CLOCKWISE ? rotation + 360f : rotation - 360f);
                mRotationAnim.start();
                break;

            case REFRESHED:
                mFlagUpdater.saveFlag(System.currentTimeMillis());
                break;

        }



    }
}
