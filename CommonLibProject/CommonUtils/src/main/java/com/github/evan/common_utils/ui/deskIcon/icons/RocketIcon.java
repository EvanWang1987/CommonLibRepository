package com.github.evan.common_utils.ui.deskIcon.icons;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.simpleImplementInterface.SaveLastValueAnimatorUpdateListener;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;

/**
 * Created by Evan on 2017/12/23.
 */
public class RocketIcon extends BaseDeskIcon {
    public static final int SHOW = 0;
    public static final int RESET = 1;
    public static final int ASS_ABOVE = 2;
    public static final int LAUNCHING = 3;
    public static final int LAUNCHED = 4;

    private ImageView mRootView;
    private AnimationDrawable mAnimationEmptyRocket;
    private AnimationDrawable mAnimationAssInRocket;
    private ObjectAnimator mShowIconAnimator;
    private ObjectAnimator mDismissIconAnimator;
    private ValueAnimator mLaunchingAnimator;
    private int mDstLaunchY = 0;




    public RocketIcon(Context context) {
        super(context);
    }

    @Override
    public void setStatus(int status) {
        if (status == RESET) {
            if (mAnimationEmptyRocket.isRunning()) {
                mAnimationEmptyRocket.stop();
            }
            if (mAnimationAssInRocket.isRunning()) {
                mAnimationAssInRocket.stop();
            }
            hidden();
            mDismissIconAnimator.start();
        } else if (status == SHOW) {
            mRootView.setImageDrawable(mAnimationEmptyRocket);
            mAnimationEmptyRocket.start();
            show();
            mShowIconAnimator.start();
        } else if (status == ASS_ABOVE) {
            if (mAnimationEmptyRocket.isRunning()) {
                mAnimationEmptyRocket.stop();
            }
            mRootView.setImageDrawable(mAnimationAssInRocket);
            mAnimationAssInRocket.start();
        } else if (status == LAUNCHING) {
            int y = getY();
            mLaunchingAnimator.setIntValues(y, 0);
            mLaunchingAnimator.addUpdateListener(new SaveLastValueAnimatorUpdateListener() {
                @Override
                public void onUpdateAnimation(ValueAnimator animator, Object lastValue) {
                    if (lastValue == null) {
                        return;
                    }
                    int value = (int) animator.getAnimatedValue();
                    int lastValueOfInt = (int) lastValue;
                    int dY = -(lastValueOfInt - value);
                    moveY(dY);

                    if(value == mDstLaunchY){
                        DeskIconManager.getInstance(getContext()).rocketLaunched();
                        mLaunchingAnimator.removeAllUpdateListeners();
                    }
                }
            });
            mLaunchingAnimator.start();
        }else if(status == LAUNCHED){
            hidden();
            moveTo(windowWidth / 2 - mRootView.getWidth() / 2, windowHeight - mRootView.getHeight());
        }
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        DeskIconConfig config = new DeskIconConfig();
        mRootView = new ImageView(getContext());
        mRootView.setImageResource(R.mipmap.empty_rocket1);
        config.setDeskIconView(mRootView);
        config.setGravity(Gravity.TOP|Gravity.LEFT);
        int height = UiUtil.measureHeight(mRootView);
        int width = UiUtil.measureWidth(mRootView);
        config.setInitX(windowWidth / 2 - width / 2);
        config.setInitY(windowHeight - height);
        mAnimationEmptyRocket = (AnimationDrawable) ResourceUtil.getDrawable(R.drawable.animation_empty_rocket);
        mAnimationAssInRocket = (AnimationDrawable) ResourceUtil.getDrawable(R.drawable.animation_ass_in_rocket);
        mShowIconAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 0f, 1f);
        mShowIconAnimator.setDuration(1000);
        mDismissIconAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 1f, 0f);
        mDismissIconAnimator.setDuration(1000);
        mLaunchingAnimator = ValueAnimator.ofInt();
        mLaunchingAnimator.setDuration(800);

        return config;
    }
}
