package com.github.evan.common_utils.ui.deskIcon.icons;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;

/**
 * Created by Evan on 2017/12/23.
 */
public class LaunchBaseIcon extends BaseDeskIcon {
    public static final int ASS_ABOVE = 0;
    public static final int LAUNCHING = 1;
    public static final int RESET = 2;
    public static final int SHOW = 3;

    private FrameLayout mLaunchBaseLayout;
    private ImageView mLaunchBase, mLaunchBaseAnim;
    private Vibrator mVibrator;
    private boolean mIsVibrated;
    private AnimationDrawable mDrawable;
    private ObjectAnimator mShowIconAnimator;
    private ObjectAnimator mDismissIconAnimator;

    public LaunchBaseIcon(Context context) {
        super(context);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void setStatus(int status) {
        if(status == ASS_ABOVE){
            if(!mIsVibrated && mVibrator.hasVibrator()){
                mVibrator.vibrate(200);
                mIsVibrated = true;
            }
            mLaunchBaseAnim.setVisibility(View.VISIBLE);
            mDrawable.start();
        }else if(status == SHOW){
            show();
            mShowIconAnimator.start();
        }else if(status == RESET){
            mIsVibrated = false;
            if(mDrawable.isRunning()){
                mDrawable.stop();
            }
            mDismissIconAnimator.start();
        }else if(status == LAUNCHING){

        }
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        DeskIconConfig config = new DeskIconConfig();
        mLaunchBaseLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_launch_base, null);
        mLaunchBase = mLaunchBaseLayout.findViewById(R.id.launch_base);
        mLaunchBaseAnim = mLaunchBaseLayout.findViewById(R.id.launch_base_animation);
        mDrawable = (AnimationDrawable) mLaunchBaseAnim.getDrawable();
        config.setDeskIconView(mLaunchBaseLayout);
        config.setGravity(Gravity.TOP | Gravity.LEFT);
        int height = UiUtil.measureHeight(mLaunchBase);
        int width = UiUtil.measureWidth(mLaunchBase);
        config.setInitX(windowWidth / 2 - width / 2);
        config.setInitY(windowHeight - height);
        mShowIconAnimator = ObjectAnimator.ofFloat(mLaunchBaseLayout, "alpha", 0f, 1f);
        mShowIconAnimator.setDuration(1000);
        mDismissIconAnimator = ObjectAnimator.ofFloat(mLaunchBaseLayout, "alpha", 1f, 0f);
        mDismissIconAnimator.setDuration(1000);
        return config;
    }
}
