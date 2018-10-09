package com.github.evan.common_utils.ui.view.pullable_view.indicator;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
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

import java.util.Locale;

/**
 * Created by Administrator on 2018/6/4.
 */
public class ClassicFloatingRefreshIndicator extends RelativeLayout implements IIndicator {
    private ImageView mImgArrow, mImgProgress;
    private TextView mTxtTitle, mTxtDesc;
    private TimeFlagUpdater mTimeUpdater;
    private Drawable mProgressDrawable, mArrowDrawable;
    private PullStatus mLastPullStatus = PullStatus.IDLE;
    private ObjectAnimator mReleaseToRefreshAnim, mRevertArrowAnim;


    public ClassicFloatingRefreshIndicator(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ClassicFloatingRefreshIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClassicFloatingRefreshIndicator(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClassicFloatingRefreshIndicator(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_classic_floating, this, true);
        mImgArrow = findViewById(R.id.ic_floating_arrow);
        mImgProgress = findViewById(R.id.ic_floating_progress);
        mTxtTitle = findViewById(R.id.txt_floating_title);
        mTxtDesc = findViewById(R.id.txt_floating_desc);
        Drawable progressDrawable = null;
        Drawable arrowDrawable = null;
        String timeFlag = "";
        if(null != attrs){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClassicFloatingRefreshIndicator);
            progressDrawable = typedArray.getDrawable(R.styleable.ClassicFloatingRefreshIndicator_floating_classic_indicator_progress_drawable);
            arrowDrawable = typedArray.getDrawable(R.styleable.ClassicFloatingRefreshIndicator_floating_classic_indicator_arrow_drawable);
            timeFlag = typedArray.getString(R.styleable.ClassicFloatingRefreshIndicator_floating_classic_indicator_time_flag);
            typedArray.recycle();
        }

        if(null == progressDrawable){
            progressDrawable = getResources().getDrawable(R.mipmap.ic_ios_refresh_arrow);
        }
        mProgressDrawable = progressDrawable;
        mImgProgress.setImageDrawable(mProgressDrawable);

        if(null == arrowDrawable){
            arrowDrawable = getResources().getDrawable(R.mipmap.ic_ios_refresh_arrow);
        }
        mArrowDrawable = arrowDrawable;
        mImgArrow.setImageDrawable(mArrowDrawable);

        if(TextUtils.isEmpty(timeFlag)){
            timeFlag = PackageUtil.getPackageName() + "/" + this.getClass().getSimpleName() + "/" + this.getId();
        }

        mTimeUpdater = new TimeFlagUpdater(timeFlag);

        mProgressDrawable = progressDrawable;
        mReleaseToRefreshAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", 0f, 180f);
        mReleaseToRefreshAnim.setDuration(300);

        mRevertArrowAnim = ObjectAnimator.ofFloat(mImgArrow, "rotation", 180f, 0f);
        mRevertArrowAnim.setDuration(300);
    }


    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void onPullStatusChange(PullStatus status) {
        Log.d("Evan", "onPullStatusChange: " + status);
        switch (status){
            case IDLE:
            case START_PULL:
                mImgArrow.setVisibility(View.VISIBLE);
                mImgProgress.setVisibility(View.INVISIBLE);
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                if(mReleaseToRefreshAnim.isRunning()){
                    mReleaseToRefreshAnim.cancel();
                }
                mImgArrow.setRotation(0f);
                Drawable progressDrawable = mImgProgress.getDrawable();
                if(null != progressDrawable && progressDrawable instanceof AnimationDrawable){
                    AnimationDrawable frameDrawable = (AnimationDrawable) progressDrawable;
                    if(frameDrawable.isRunning()){
                        frameDrawable.stop();
                    }
                }

                break;

            case TOP_PULLING:
            case BOTTOM_PULLING:
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                String lastUpdateTime = getResources().getString(R.string.refresh_at_first_time);
                Long flagTime = mTimeUpdater.getFlag();
                if(flagTime != -1){
                    lastUpdateTime = getResources().getString(R.string.last_refresh_time, DateUtil.time2String(flagTime, DateUtil.MM_dd_HH_mm, Locale.getDefault()));
                }
                mTxtDesc.setText(lastUpdateTime);
                if(mLastPullStatus == PullStatus.RELEASE_TO_INVOKE){
                    //还原箭头角度
                    mRevertArrowAnim.start();
                }
                break;

            case RELEASE_TO_INVOKE:
                mTxtTitle.setText(getResources().getString(R.string.release_to_refresh));
                mReleaseToRefreshAnim.start();
                break;

            case INVOKING:
                mTxtTitle.setText(getResources().getString(R.string.refreshing));
                mImgArrow.setVisibility(View.INVISIBLE);
                mImgProgress.setVisibility(View.VISIBLE);
                Drawable drawable = mImgProgress.getDrawable();
                if(null != drawable && drawable instanceof AnimationDrawable){
                    AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                    if(animationDrawable.isRunning()){
                        break;
                    }
                    animationDrawable.start();
                }
                break;

            case INVOKING_COMPLETE:
                mTimeUpdater.saveFlag(System.currentTimeMillis());
                break;
        }
        mLastPullStatus = status;
    }

    @Override
    public void onDistanceChange(int x, int y, float dXPercentRelativeScreen, float dYPercentRelativeScreen) {

    }

    @Override
    public void onSlideOverIndicator(boolean isSlideOverIndicator) {
        Log.d("Evan", "isSlideOverIndicator: " + isSlideOverIndicator);
    }

    @Override
    public void onDistanceChangeWhenSlideOverIndicator(int x, int y, float dXPercentRelativeScreen, float dYPercentRelativeScreen) {
        Log.d("Evan", "onDistanceChangeWhenSlideOverIndicator");
        Log.d("Evan", "x: " + x);
        Log.d("Evan", "y: " + y);
        Log.d("Evan", "dXPercentRelativeScreen: " + dXPercentRelativeScreen);
        Log.d("Evan", "dYPercentRelativeScreen: " + dYPercentRelativeScreen);
    }
}
