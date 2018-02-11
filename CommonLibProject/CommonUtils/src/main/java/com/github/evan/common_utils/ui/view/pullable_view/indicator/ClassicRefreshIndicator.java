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
import com.github.evan.common_utils.ui.view.flagView.flagUpdater.TimeFlagUpdater;
import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.SpUtil;
import com.github.evan.common_utils.utils.StringUtil;

import java.util.Locale;

/**
 * Created by Evan on 2018/2/8.
 */
public class ClassicRefreshIndicator extends LinearLayout implements IIndicator {
    private PullStatus mLastPullStatus = PullStatus.IDLE;
    public static final int PROGRESS_ROTATION_DIRECTION_LEFT = 1;
    public static final int PROGRESS_ROTATION_DIRECTION_RIGHT = 2;
    private ImageView mIcArrow, mIcProgress;
    private TextView mTxtTitle, mTxtDesc;
    private TimeFlagUpdater mTimeUpdater;
    private Drawable mProgressDrawable;
    private Drawable mArrowDrawable;
    private int mProgressRotationDirection = PROGRESS_ROTATION_DIRECTION_RIGHT;
    private long mProgressRotationDuration = 1000;
    private ObjectAnimator mRotationAnim;
    private ObjectAnimator mArrowUpAnim;
    private ObjectAnimator mArrowDownAnim;

    public ClassicRefreshIndicator(Context context) {
        super(context);
        init(null, 0);
    }

    public ClassicRefreshIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ClassicRefreshIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        switch (status) {
            case IDLE:
                mRotationAnim.cancel();
                mIcProgress.setRotation(0f);
                mIcArrow.setRotation(0f);
                mIcProgress.setVisibility(INVISIBLE);
                mIcArrow.setVisibility(VISIBLE);
                mLastPullStatus = PullStatus.IDLE;
                break;

            case START_PULL:
                mIcArrow.setVisibility(VISIBLE);
                mIcProgress.setVisibility(INVISIBLE);
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                Long lastTime = mTimeUpdater.getFlag();
                if (lastTime > 0) {
                    String lastTimeString = DateUtil.time2String(lastTime, DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                    mTxtDesc.setText(getResources().getString(R.string.last_refresh_time, lastTimeString));
                } else {
                    mTxtDesc.setText(getResources().getString(R.string.refresh_at_first_time));
                }
                break;

            case TOP_PULLING:
                if (mLastPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                    mArrowDownAnim.start();
                }
                break;

            case BOTTOM_PULLING:
                if (mLastPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                    mArrowDownAnim.start();
                }
                break;

            case RELEASE_TO_INVOKE:
                mTxtTitle.setText(getResources().getString(R.string.release_to_refresh));
                if (mLastPullStatus == PullStatus.TOP_PULLING || mLastPullStatus == PullStatus.BOTTOM_PULLING) {
                    mArrowUpAnim.start();
                }
                break;

            case INVOKING:
                mTxtTitle.setText(getResources().getString(R.string.refreshing));
                mIcArrow.setVisibility(INVISIBLE);
                mIcProgress.setVisibility(VISIBLE);
                mRotationAnim.start();
                break;

            case INVOKING_COMPLETE:
                mTimeUpdater.saveFlag(System.currentTimeMillis());
                break;
        }


        mLastPullStatus = status;
    }

    @Override
    public void onDistanceChange(int x, int y) {

    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        Context context = getContext();
        LayoutInflater.from(context).inflate(R.layout.indicator_classic_ptr, this, true);
        mIcArrow = findViewById(R.id.ic_arrow_indicator_classic);
        mIcProgress = findViewById(R.id.ic_progress_indicator_classic);
        mTxtTitle = findViewById(R.id.txt_title_indicator_classic);
        mTxtDesc = findViewById(R.id.txt_desc_indicator_classic);
        String timeFlag = "";
        Drawable progressDrawable = null;
        Drawable arrowDrawable = null;
        int progressDirection = mProgressRotationDirection;
        long progressRotationDuration = mProgressRotationDuration;
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClassicRefreshIndicator);
            timeFlag = typedArray.getString(R.styleable.ClassicRefreshIndicator_classic_indicator_time_flag);
            progressDrawable = typedArray.getDrawable(R.styleable.ClassicRefreshIndicator_classic_indicator_progress_drawable);
            arrowDrawable = typedArray.getDrawable(R.styleable.ClassicRefreshIndicator_classic_indicator_direction_drawable);
            progressDirection = typedArray.getInt(R.styleable.ClassicRefreshIndicator_classic_indicator_progress_rotation_direction, mProgressRotationDirection);
            progressRotationDuration = typedArray.getInt(R.styleable.ClassicRefreshIndicator_classic_indicator_progress_rotation_duration, (int) mProgressRotationDuration);
            typedArray.recycle();
        }
        if (StringUtil.isEmptyString(timeFlag, true)) {
            timeFlag = context.getPackageName() + "/" + context.getClass().getName() + "/" + this.getId();
        }
        if (null == progressDrawable) {
            progressDrawable = getResources().getDrawable(R.mipmap.icon_loading_small);
        }

        if (null == arrowDrawable) {
            arrowDrawable = getResources().getDrawable(R.mipmap.ic_arrow);
        }

        mTimeUpdater = new TimeFlagUpdater(timeFlag);
        mProgressDrawable = progressDrawable;
        mArrowDrawable = arrowDrawable;
        mProgressRotationDirection = progressDirection;
        mProgressRotationDuration = progressRotationDuration;
        mIcArrow.setImageDrawable(mArrowDrawable);
        mIcProgress.setImageDrawable(mProgressDrawable);
        float startAngle = 0;
        float endAngle = mProgressRotationDirection == PROGRESS_ROTATION_DIRECTION_RIGHT ? 360f : -360f;
        mRotationAnim = ObjectAnimator.ofFloat(mIcProgress, "rotation", startAngle, endAngle);
        mRotationAnim.setDuration(mProgressRotationDuration);
        mRotationAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnim.setRepeatMode(ObjectAnimator.RESTART);
        mRotationAnim.setInterpolator(new LinearInterpolator());
        mArrowUpAnim = ObjectAnimator.ofFloat(mIcArrow, "rotation", 0, -180f);
        mArrowUpAnim.setDuration(300);
        mArrowDownAnim = ObjectAnimator.ofFloat(mIcArrow, "rotation", -180f, 0f);
        mArrowDownAnim.setDuration(300);

    }
}
