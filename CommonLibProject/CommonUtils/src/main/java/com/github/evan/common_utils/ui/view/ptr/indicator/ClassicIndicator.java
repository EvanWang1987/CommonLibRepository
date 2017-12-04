package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.ptr.PtrStatus;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;

import java.util.Locale;

/**
 * Created by Evan on 2017/11/30.
 */
public class ClassicIndicator extends TimeFlagIndicator {
    private TextView mTxtTitle, mTxtDesc;
    private ImageView mIcProgress, mIcArrow;
    private ObjectAnimator mRotationAnim, mArrowAnim;
    private Drawable mProgressDrawable;
    private ProgressRotationDirection mRotationDirection;
    private int mProgressRotationDuration = 500;

    public ClassicIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public ClassicIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClassicIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.indicator_classic, null, false);
        mTxtTitle = root.findViewById(R.id.txt_title_indicator_classic);
        mTxtDesc = root.findViewById(R.id.txt_desc_indicator_classic);
        mIcProgress = root.findViewById(R.id.ic_progress_indicator_classic);
        mIcArrow = root.findViewById(R.id.ic_arrow_indicator_classic);
        return root;
    }

    @Override
    public void onPtrStatusChange(PtrStatus status) {
        Logger.d("status: " + status);
        handleSelfWithPtrStatus(status);
    }

    @Override
    public void onPullDownOffsetChange(int offsetYFromDown, int offsetYFromLastMoved) {

    }

    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseIndicator);
            int anInt = typedArray.getInt(R.styleable.BaseIndicator_progress_rotation_direction, ProgressRotationDirection.CLOCKWISE.value);
            mRotationDirection = ProgressRotationDirection.valueOf(anInt);
            mProgressDrawable = typedArray.getDrawable(R.styleable.BaseIndicator_progress_drawable);
            if (null == mProgressDrawable) {
                mProgressDrawable = getResources().getDrawable(R.mipmap.icon_loading_small);
            }
            mProgressRotationDuration = typedArray.getInt(R.styleable.BaseIndicator_progress_rotation_duration, 500);
            typedArray.recycle();
        }
        mRotationAnim = ObjectAnimator.ofFloat(mIcProgress, "rotation", 0f, 360f);
        mRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        mRotationAnim.setDuration(mProgressRotationDuration);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        mRotationAnim.setInterpolator(linearInterpolator);
        mArrowAnim = ObjectAnimator.ofFloat(mIcArrow, "rotation", 0f, 180f);
        mArrowAnim.setDuration(300);
        mIcProgress.setImageDrawable(mProgressDrawable);
    }


    private void handleSelfWithPtrStatus(PtrStatus status) {
        switch (status) {
            case IDLE:
                mRotationAnim.cancel();
                mArrowAnim.cancel();
                mIcArrow.setRotationY(0f);
                break;

            case START_PULL:
                mTxtTitle.setText(R.string.pulling);
                mIcArrow.setVisibility(VISIBLE);
                mIcProgress.setVisibility(INVISIBLE);
                Long lastUpdateTime = getLastUpdateTime();
                mTxtDesc.setVisibility(lastUpdateTime == -1 ? GONE : VISIBLE);
                String desc = "";
                if (lastUpdateTime != -1) {
                    String time2String = DateUtil.time2String(lastUpdateTime, DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                    desc = ResourceUtil.getString(R.string.last_refresh_time, time2String);
                }
                mTxtDesc.setText(desc);
                break;

            case PULLING:
                mTxtTitle.setText(R.string.pulling);
                if(mIcArrow.getRotation() == 180f){
                    mArrowAnim.setFloatValues(180f, 360f);
                    mArrowAnim.start();
                }

                break;

            case RELEASE_TO_REFRESH:
                mTxtTitle.setText(R.string.release_to_refresh);
                mArrowAnim.setFloatValues(0f, 180f);
                mArrowAnim.start();
                break;

            case REFRESHING:
                mTxtTitle.setText(R.string.refreshing);
                mIcArrow.setVisibility(INVISIBLE);
                mIcProgress.setVisibility(VISIBLE);
                float rotation = mIcProgress.getRotation();
                mRotationAnim.setFloatValues(rotation, mRotationDirection == ProgressRotationDirection.CLOCKWISE ? rotation + 360f : rotation - 360f);
                mRotationAnim.start();
                break;

            case REFRESHED:
                saveUpdateTime(System.currentTimeMillis());
                break;

        }


    }
}
