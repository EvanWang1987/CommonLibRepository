package com.github.evan.common_utils.ui.view.pullable_view.indicator;

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
import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;

import java.util.Locale;

/**
 * Created by Evan on 2018/2/8.
 */
public class ClassicProRefreshIndicator extends LinearLayout implements IIndicator {
    public static final int PROGRESS_ROTATION_DIRECTION_LEFT = 1;
    public static final int PROGRESS_ROTATION_DIRECTION_RIGHT = 2;
    private PullStatus mLastPullStatus = PullStatus.IDLE;
    private PullStatus mPullStatus = PullStatus.IDLE;
    private TextView mTxtTitle;
    private ImageView mIcProgress;
    private Drawable mProgressDrawable;
    private int mProgressDirection = PROGRESS_ROTATION_DIRECTION_RIGHT;
    private long mProgressRotationDuration = 1000;
    private ObjectAnimator mRotationAnim;

    public ClassicProRefreshIndicator(Context context) {
        super(context);
        init(null, 0);
    }

    public ClassicProRefreshIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ClassicProRefreshIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void onPullStatusChange(PullStatus status) {
        Logger.d("PullStatus: " + status);
        mPullStatus = status;
        switch (status) {
            case IDLE:
                mRotationAnim.cancel();
                mIcProgress.setRotation(0f);
                mLastPullStatus = PullStatus.IDLE;
                break;

            case START_PULL:
                break;

            case TOP_PULLING:
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                break;

            case BOTTOM_PULLING:
                break;

            case RELEASE_TO_INVOKE:
                mTxtTitle.setText(getResources().getString(R.string.release_to_refresh));
                break;

            case INVOKING:
                mTxtTitle.setText(getResources().getString(R.string.refreshing));
                float startAngle = mIcProgress.getRotation();
                float endAngle = mProgressDirection == PROGRESS_ROTATION_DIRECTION_RIGHT ? startAngle + 360f : startAngle - 360f;
                mRotationAnim.setFloatValues(startAngle, endAngle);
                mRotationAnim.start();
                break;

            case INVOKING_COMPLETE:
                break;
        }
        mLastPullStatus = status;
    }

    @Override
    public void onDistanceChange(int x, int y) {
        if (y == 0) {
            return;
        }
        boolean isTop2BottomSlide = y > 0;
        float rotationAngel = 0;
        float currRotation = mIcProgress.getRotation();
        int id = getId();
        if (id == R.id.id_bottom_indicator) {
            if(mProgressDirection == PROGRESS_ROTATION_DIRECTION_RIGHT){
                rotationAngel = !isTop2BottomSlide ? currRotation + y : currRotation + y;
            }else{
                rotationAngel = !isTop2BottomSlide ? currRotation + -y : currRotation - y;
            }
        } else {
            if(mProgressDirection == PROGRESS_ROTATION_DIRECTION_RIGHT){
                rotationAngel = isTop2BottomSlide ? currRotation - y : currRotation + -y;
            }else{
                rotationAngel = isTop2BottomSlide ? currRotation + y : currRotation - -y;
            }
        }
        mIcProgress.setRotation(rotationAngel);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        Context context = getContext();
        LayoutInflater.from(context).inflate(R.layout.indicator_classic_pro, this, true);
        mIcProgress = findViewById(R.id.ic_progress_indicator_classic_pro);
        mTxtTitle = findViewById(R.id.txt_title_indicator_classic_pro);
        Drawable progressDrawable = null;
        int progressDirection = mProgressDirection;
        long progressRotationDuration = mProgressRotationDuration;
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClassicProRefreshIndicator);
            progressDrawable = typedArray.getDrawable(R.styleable.ClassicProRefreshIndicator_pro_indicator_progress_drawable);
            progressDirection = typedArray.getInt(R.styleable.ClassicProRefreshIndicator_pro_indicator_progress_rotation_direction, mProgressDirection);
            progressRotationDuration = typedArray.getInt(R.styleable.ClassicProRefreshIndicator_pro_indicator_progress_rotation_duration, (int) mProgressRotationDuration);
            typedArray.recycle();
        }
        if (null == progressDrawable) {
            progressDrawable = getResources().getDrawable(R.mipmap.icon_loading_small);
        }

        mProgressDrawable = progressDrawable;
        mProgressDirection = progressDirection;
        mProgressRotationDuration = progressRotationDuration;
        mIcProgress.setImageDrawable(mProgressDrawable);
        mRotationAnim = ObjectAnimator.ofFloat(mIcProgress, "rotation", 0, 0);
        mRotationAnim.setDuration(mProgressRotationDuration);
        mRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        mRotationAnim.setInterpolator(new LinearInterpolator());
    }
}
