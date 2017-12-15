package com.github.evan.common_utils.ui.view.mediaController;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.PopupMenuCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.bean.BatteryInfo;
import com.github.evan.common_utils.gesture.CommonGestures;
import com.github.evan.common_utils.ui.view.BatteryView;
import com.github.evan.common_utils.ui.view.TimeView;
import com.github.evan.common_utils.utils.BatteryUtil;
import com.github.evan.common_utils.utils.BrightnessUtil;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.Logger;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/14.
 */
public class CommonMediaController extends BaseMediaController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ViewGroup mSlideControllerLayout, mTitleLayout, mBottomLayout;
    private ImageView mImgSlideIcon;
    private ImageButton mBtnBack, mBtnShare, mBtnMore, mBtnNext, mBtnSnapShot;
    private CheckBox mToggleDanMark, mToggleLockScreen, mTogglePlay, mToggleMute, mToggleFullScreen;
    private TextView mTxtTitle, mTxtDefinition, mTxtPosition, mTxtDuration, mTxtSlideIcon;
    private TimeView mTimeView;
    private BatteryView mBatteryView;
    private SeekBar mSeekBar;
    private ObjectAnimator mTitleShowAnim, mBottomShowAnim, mTitleDismissAnim, mBottomDismissAnim, mLockerShowAnim, mLockerDismissAnim, mScreenShotShowAnim, mScreenShotDismissAnim;
    private AnimatorSet mShowControllersAnim, mDismissControllerAnim;
    private boolean isControllersShowed = true;
    private long mVideoDuration;
    private boolean isHorSlideAtFirst = false;
    private boolean isVerSlideAtFirst = false;

    public CommonMediaController(@NonNull Context context) {
        super(context);
        init();
    }

    public CommonMediaController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonMediaController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        mBatteryView.stopMonitoringBattery();
        mTimeView.stopAutoRefreshTime();
        super.onDetachedFromWindow();
    }

    @Override
    public void showControllers() {
        if (!isControllersShowed) {
            mTitleShowAnim.setPropertyName("translationY");
            mTitleShowAnim.setFloatValues(-mTitleLayout.getHeight(), 0);
            mBottomShowAnim.setPropertyName("translationY");
            mBottomShowAnim.setFloatValues(mBottomLayout.getHeight(), 0);
            MarginLayoutParams lps = (MarginLayoutParams) mToggleLockScreen.getLayoutParams();
            mLockerShowAnim.setPropertyName("translationX");
            mLockerShowAnim.setFloatValues(-(mToggleLockScreen.getWidth() + lps.leftMargin), 0);
            MarginLayoutParams snapLps = (MarginLayoutParams) mBtnSnapShot.getLayoutParams();
            mScreenShotShowAnim.setPropertyName("translationX");
            mScreenShotShowAnim.setFloatValues(mBtnSnapShot.getWidth() + snapLps.leftMargin, 0);
            mTimeView.refreshCurrentTime();
            mShowControllersAnim.start();
            isControllersShowed = true;
        }
    }

    @Override
    public void hideControllers() {
        if (isControllersShowed) {
            mTitleDismissAnim.setPropertyName("translationY");
            mTitleDismissAnim.setFloatValues(0, -mTitleLayout.getHeight());
            mBottomDismissAnim.setPropertyName("translationY");
            mBottomDismissAnim.setFloatValues(0, mBottomLayout.getHeight());
            MarginLayoutParams lps = (MarginLayoutParams) mToggleLockScreen.getLayoutParams();
            mLockerDismissAnim.setPropertyName("translationX");
            mLockerDismissAnim.setFloatValues(0, -(mToggleLockScreen.getWidth() + lps.leftMargin));
            MarginLayoutParams snapLps = (MarginLayoutParams) mBtnSnapShot.getLayoutParams();
            mScreenShotDismissAnim.setPropertyName("translationX");
            mScreenShotDismissAnim.setFloatValues(0, mBtnSnapShot.getWidth() + snapLps.rightMargin);
            mDismissControllerAnim.start();
            isControllersShowed = false;
        }
    }

    @Override
    public void setCurrentPosition(long position) {
        if (mVideoDuration < 0) {
            throw new IllegalArgumentException("You should set duration first.");
        }

        if (position < 0) {
            throw new IllegalArgumentException("Position can not be less than 0.");
        }


        int positionPerProgress = (int) (mVideoDuration / mSeekBar.getMax());
        int progress = (int) (position / positionPerProgress);
        mSeekBar.setProgress(progress);
        mTxtPosition.setText(DateUtil.time2String(position, DateUtil.HH_mm_ss, DateUtil.mm_ss));
    }

    @Override
    public void setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration can not be less than 0.");
        }
        mVideoDuration = duration;
        mTxtDuration.setText(DateUtil.time2String(duration, DateUtil.HH_mm_ss, DateUtil.mm_ss));
    }

    @Override
    public void setPlayToggleChecked(boolean isChecked) {
        mTogglePlay.setChecked(isChecked);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTxtTitle.setText(title);
    }

    public boolean isControllersShowed() {
        return isControllersShowed;
    }

    @Override
    public void onGestureBegin() {
        super.onGestureBegin();
    }

    @Override
    public void onGestureEnd() {
        mSlideControllerLayout.setVisibility(GONE);
        isHorSlideAtFirst = false;
        isVerSlideAtFirst = false;
        super.onGestureEnd();
    }

    @Override
    public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        if(!isHorSlideAtFirst && !isVerSlideAtFirst){
            isHorSlideAtFirst = true;
        }

        if(isVerSlideAtFirst){
            onVerticalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
            return;
        }

        Logger.d("onHorizontalSlide");
        mSlideControllerLayout.setVisibility(VISIBLE);
        mImgSlideIcon.setImageResource(R.mipmap.ic_light_show);

        super.onHorizontalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
    }

    @Override
    public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        if(!isHorSlideAtFirst && !isVerSlideAtFirst){
            isVerSlideAtFirst = true;
        }

        if(isHorSlideAtFirst){
            onHorizontalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
            return;
        }

        mSlideControllerLayout.setVisibility(VISIBLE);
        boolean isDownAtLeft = downPositionAtParent == CommonGestures.LEFT_AT_PARENT;
        mImgSlideIcon.setImageResource(isDownAtLeft ? R.mipmap.ic_vol_show : R.mipmap.ic_light_show);
        if(isDownAtLeft){
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, verticalSlidePercent <= 0 ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }else{
            Activity activity = (Activity) getContext();
            float activityBrightness = BrightnessUtil.getActivityBrightness(activity);
            if(activityBrightness == -1){
                activityBrightness = BrightnessUtil.getSystemBrightness(getContext()) / 255;
            }

            activityBrightness += -verticalSlidePercent;
            if(activityBrightness > 1){
                activityBrightness = 1;
            }

            if(activityBrightness < 0){
                activityBrightness = 0;
            }
            BrightnessUtil.setActivityBrightness(activity, activityBrightness);
        }
        super.onVerticalSlide(horizontalSlidePercent, verticalSlidePercent, distanceX, distanceY, downPositionAtParent);
    }

    @Override
    public void onSingleTap() {
        super.onSingleTap();
    }

    @Override
    public void onDoubleTap() {
        super.onDoubleTap();
    }

    @Override
    public void onScale(float scaleFactor, int state, ScaleGestureDetector detector) {
        super.onScale(scaleFactor, state, detector);
    }

    @Override
    public void onLongPress() {
        super.onLongPress();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.media_controller_common, this, true);
        mTitleLayout = findViewById(R.id.title_common_media_controller);
        mBottomLayout = findViewById(R.id.bottom_common_media_controller);

        mBtnBack = findViewById(R.id.btn_back_common_media_controller);
        mBtnShare = findViewById(R.id.btn_share_title_common_media_controller);
        mToggleDanMark = findViewById(R.id.toggle_danmark_common_media_controller);
        mBtnMore = findViewById(R.id.btn_more_common_media_controller);
        mBatteryView = findViewById(R.id.battery_view_common_media_controller);
        mTimeView = findViewById(R.id.time_view_common_media_controller);
        mTxtTitle = findViewById(R.id.txt_title_common_media_controller);

        mTogglePlay = findViewById(R.id.toggle_play_bottom_common_media_controller);
        mBtnNext = findViewById(R.id.btn_next_common_media_controller);
        mTxtPosition = findViewById(R.id.txt_current_position_bottom_common_media_controller);
        mTxtDuration = findViewById(R.id.txt_duration_bottom_common_media_controller);
        mSeekBar = findViewById(R.id.seek_bar_bottom_common_media_controller);
        mTxtDefinition = findViewById(R.id.txt_definition_bottom_common_media_controller);
        mToggleMute = findViewById(R.id.toggle_mute_bottom_common_media_controller);
        mToggleLockScreen = findViewById(R.id.toggle_lock_screen_common_media_controller);
        mBtnSnapShot = findViewById(R.id.btn_screen_shot_common_media_controller);
        mSlideControllerLayout = findViewById(R.id.slide_controller_layout_common_media_controller);
        mImgSlideIcon = findViewById(R.id.ic_image_common_media_controller);
        mTxtSlideIcon = findViewById(R.id.ic_text_common_media_controller);
        mToggleFullScreen = findViewById(R.id.toggle_full_screen_bottom_common_media_controller);
        mBatteryView.startMonitoringBattery();
        mTimeView.refreshCurrentTime();
        mTimeView.startAutoRefreshTime(1, TimeUnit.SECONDS);

        mBtnBack.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnMore.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mTxtDefinition.setOnClickListener(this);
        mBtnSnapShot.setOnClickListener(this);

        mToggleDanMark.setOnCheckedChangeListener(this);
        mToggleLockScreen.setOnCheckedChangeListener(this);
        mTogglePlay.setOnCheckedChangeListener(this);
        mToggleMute.setOnCheckedChangeListener(this);
        mToggleFullScreen.setOnCheckedChangeListener(this);

        mTitleShowAnim = ObjectAnimator.ofFloat(mTitleLayout, "translationY", -mTitleLayout.getHeight(), 0);
        mTitleDismissAnim = ObjectAnimator.ofFloat(mTitleLayout, "translationY", 0, -mTitleLayout.getHeight());
        mBottomShowAnim = ObjectAnimator.ofFloat(mBottomLayout, "translationY", mTitleLayout.getHeight(), 0);
        mBottomDismissAnim = ObjectAnimator.ofFloat(mBottomLayout, "translationY", 0, mTitleLayout.getHeight());
        MarginLayoutParams lps = (MarginLayoutParams) mToggleLockScreen.getLayoutParams();
        mLockerShowAnim = ObjectAnimator.ofFloat(mToggleLockScreen, "translationX", -mToggleLockScreen.getWidth() + lps.leftMargin, 0);
        mLockerDismissAnim = ObjectAnimator.ofFloat(mToggleLockScreen, "translationX", -mToggleLockScreen.getWidth() + lps.leftMargin, 0);
        MarginLayoutParams snapLps = (MarginLayoutParams) mBtnSnapShot.getLayoutParams();
        mScreenShotShowAnim = ObjectAnimator.ofFloat(mBtnSnapShot, "translationX", mBtnSnapShot.getWidth() + snapLps.leftMargin, 0);
        mScreenShotDismissAnim = ObjectAnimator.ofFloat(mBtnSnapShot, "translationX", 0, mBtnSnapShot.getWidth() + snapLps.leftMargin);

        mShowControllersAnim = new AnimatorSet();
        mShowControllersAnim.setDuration(300);
        mShowControllersAnim.playTogether(mTitleShowAnim, mBottomShowAnim, mLockerShowAnim, mScreenShotShowAnim);
        mDismissControllerAnim = new AnimatorSet();
        mDismissControllerAnim.setDuration(300);
        mDismissControllerAnim.playTogether(mTitleDismissAnim, mBottomDismissAnim, mLockerDismissAnim, mScreenShotDismissAnim);
    }

    @Override
    public void onClick(View v) {
        int id = -1;
        int i = v.getId();
        if (i == R.id.btn_back_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_BACK_BUTTON;
        } else if (i == R.id.btn_share_title_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_SHARE_BUTTON;
        } else if (i == R.id.btn_more_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_MORE_BUTTON;
        } else if (i == R.id.btn_next_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_NEXT_BUTTON;
        } else if (i == R.id.btn_screen_shot_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_SCREEN_SNAP_SHOT_BUTTON;
        }

        if (null != mListener) {
            mListener.onButtonClicked(id);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = -1;
        int i = buttonView.getId();
        if (i == R.id.toggle_danmark_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_DAN_MARK;
        } else if (i == R.id.toggle_lock_screen_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_LOCK_SCREEN;
        } else if (i == R.id.toggle_mute_bottom_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_MUTE_TOGGLE;
            if (isChecked) {
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            } else {
                mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            }
        } else if (i == R.id.toggle_play_bottom_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_PLAY_TOGGLE;
        } else if (i == R.id.toggle_full_screen_bottom_common_media_controller) {
            id = MediaControllerListener.VIEW_ID_FULL_SCREEN_TOGGLE;
        }

        if (null != mListener) {
            mListener.onToggleChecked(id, isChecked);
        }
    }
}
