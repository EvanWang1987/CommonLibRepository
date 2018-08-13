package com.github.evan.common_utils.ui.view.pullable_view.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.flagView.flagUpdater.TimeFlagUpdater;
import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.PackageUtil;
import com.github.evan.common_utils.utils.StringUtil;
import java.util.Locale;

/**
 * Created by Administrator on 2018/6/8.
 */
public class ClassicFloatingRefreshHorIndicator extends RelativeLayout implements IIndicator {
    private TextView mTxtTitle, mTxtLastUpdateTime;
    private ImageView mImgArrow, mImgProgress;
    private Drawable mArrowDrawable;
    private Drawable mProgressDrawable;
    private PullStatus mLastPullStatus;
    private TimeFlagUpdater mTimeUpdater;
    private ObjectAnimator mLeftRotateAnim, mLeftRotateBackAnim, mRightRotateAnim, mRightRotateBackArrowAnim;

    public ClassicFloatingRefreshHorIndicator(Context context) {
        super(context);
        init(null);
    }

    public ClassicFloatingRefreshHorIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClassicFloatingRefreshHorIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassicFloatingRefreshHorIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_classic_floating_hor, this, true);
        mTxtTitle = findViewById(R.id.txt_title_indicator_classic);
        mTxtLastUpdateTime = findViewById(R.id.txt_desc_indicator_classic);
        mImgArrow = findViewById(R.id.ic_arrow_classic_floating);
        mImgProgress = findViewById(R.id.ic_floating_progress);
        String timeFlag = "";

        if(null != attrs){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClassicFloatingRefreshHorIndicator);
            mProgressDrawable = typedArray.getDrawable(R.styleable.ClassicFloatingRefreshHorIndicator_floating_classic_indicator_hor_progress_drawable);
            mArrowDrawable = typedArray.getDrawable(R.styleable.ClassicFloatingRefreshHorIndicator_floating_classic_indicator_hor_arrow_drawable);
            timeFlag = typedArray.getString(R.styleable.ClassicFloatingRefreshHorIndicator_floating_classic_indicator_hor_time_flag);
            typedArray.recycle();
        }

        if(StringUtil.isNullString(timeFlag)){
            timeFlag = PackageUtil.getPackageName() + getContext().getClass().getSimpleName() + "/" + this.getClass().getSimpleName() + "/id: " + this.getId();
        }

        mTimeUpdater = new TimeFlagUpdater(timeFlag);
        mImgArrow.setImageDrawable(mArrowDrawable);
        mImgProgress.setImageDrawable(mProgressDrawable);
        mLeftRotateAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", 0f, -180f);
        mRightRotateAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", 0f, 180f);
        mLeftRotateBackAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", -180f, 0f);
        mRightRotateBackArrowAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", 180f, 0f);
        mLeftRotateAnim.setDuration(300);
        mRightRotateAnim.setDuration(300);
        mLeftRotateBackAnim.setDuration(300);
        mRightRotateBackArrowAnim.setDuration(300);
    }

    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void onPullStatusChange(PullStatus status) {
        switch (status){
            case IDLE:
                mImgArrow.setVisibility(View.VISIBLE);
                mImgProgress.setVisibility(View.INVISIBLE);
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                mImgArrow.setRotation(0f);
                Drawable progressDrawable = mImgProgress.getDrawable();
                if(null != progressDrawable && progressDrawable instanceof AnimationDrawable){
                    AnimationDrawable frameDrawable = (AnimationDrawable) progressDrawable;
                    if(frameDrawable.isRunning()){
                        frameDrawable.stop();
                    }
                }
                break;

            case START_PULL:
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                break;

            case LEFT_PULLING:
                refreshLastUpdateTime();
                if(mLastPullStatus == PullStatus.RELEASE_TO_INVOKE){
                    if(null != mLeftRotateBackAnim && !mLeftRotateBackAnim.isRunning()){
                        mLeftRotateBackAnim.start();
                    }
                }
                break;

            case RIGHT_PULLING:
                refreshLastUpdateTime();
                if(mLastPullStatus == PullStatus.RELEASE_TO_INVOKE){
                    if(null != mRightRotateBackArrowAnim && !mRightRotateBackArrowAnim.isRunning()){
                        mRightRotateBackArrowAnim.start();
                    }
                }
                break;

            case RELEASE_TO_INVOKE:
                if(mLastPullStatus == PullStatus.LEFT_PULLING){
                    if(null != mLeftRotateAnim && !mLeftRotateAnim.isRunning()){
                        mLeftRotateAnim.start();
                    }
                }else if(mLastPullStatus == PullStatus.RIGHT_PULLING){
                    if(null != mRightRotateAnim && !mRightRotateAnim.isRunning()){
                        mRightRotateAnim.start();
                    }
                }
                break;

            case INVOKING:
                mTxtTitle.setText(getResources().getString(R.string.refreshing));
                mImgArrow.setVisibility(View.INVISIBLE);
                mImgProgress.setVisibility(View.VISIBLE);
                AnimationDrawable frameDrawable = (AnimationDrawable) mImgProgress.getDrawable();
                if(null != frameDrawable && !frameDrawable.isRunning()){
                    frameDrawable.start();
                }
                break;

            case INVOKING_COMPLETE:
                mTimeUpdater.saveFlag(System.currentTimeMillis());
                break;

        }

        mLastPullStatus = status;
    }

    private void refreshLastUpdateTime() {
        String lastUpdateTime = getResources().getString(R.string.refresh_at_first_time);
        Long flagTime = mTimeUpdater.getFlag();
        if(flagTime != -1){
            lastUpdateTime = getResources().getString(R.string.last_refresh_time, DateUtil.time2String(flagTime, DateUtil.MM_dd_HH_mm, Locale.getDefault()));
        }
        mTxtLastUpdateTime.setText(lastUpdateTime);
    }

    @Override
    public void onDistanceChange(int x, int y, float dXPercentRelativeScreen, float dYPercentRelativeScreen) {

    }

    @Override
    public void onSlideOverIndicator(boolean isSlideOverIndicator) {

    }

    @Override
    public void onDistanceChangeWhenSlideOverIndicator(int x, int y, float dXPercentRelativeScreen, float dYPercentRelativeScreen) {

    }
}
