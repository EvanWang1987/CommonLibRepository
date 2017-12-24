package com.github.evan.common_utils.ui.deskIcon;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.icons.AssIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.LaunchBaseIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.RocketIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.SmokeIcon;

/**
 * Created by Evan on 2017/12/23.
 */
public class DeskIconManager {
    private AssIcon mAssIcon;
    private LaunchBaseIcon mLaunchBaseIcon;
    private RocketIcon mRocketIcon;
    private int launchCount = 0;
    private SoundPool mSoundPool;
    private int mYahooSound;
    private int mYellSound;

    private static DeskIconManager mInstance = null;
    private SmokeIcon mSmokeIcon;

    public static DeskIconManager getInstance() {
        if (null == mInstance) {
            synchronized (DeskIconManager.class) {
                mInstance = new DeskIconManager();
            }
        }
        return mInstance;
    }

    private DeskIconManager() {

    }

    public void prepare(Context context) {
        mAssIcon = new AssIcon(context);
        mLaunchBaseIcon = new LaunchBaseIcon(context);
        mRocketIcon = new RocketIcon(context);
        mSmokeIcon = new SmokeIcon(context);

        mLaunchBaseIcon.alert();
        mLaunchBaseIcon.hidden();
        mRocketIcon.alert();
        mRocketIcon.hidden();
        mSmokeIcon.alert();
        mSmokeIcon.hidden();
        mAssIcon.alert();

        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mYellSound = mSoundPool.load(context, R.raw.yell, 1);
        mYahooSound = mSoundPool.load(context, R.raw.yahoo, 1);
    }

    public void release() {
        mAssIcon.release();
        mLaunchBaseIcon.release();
        mRocketIcon.release();
    }

    public void addIcon(BaseDeskIcon deskIcon) {
        if (deskIcon instanceof AssIcon) {
            mAssIcon = (AssIcon) deskIcon;
        } else if (deskIcon instanceof LaunchBaseIcon) {
            mLaunchBaseIcon = (LaunchBaseIcon) deskIcon;
        } else if (deskIcon instanceof RocketIcon) {
            mRocketIcon = (RocketIcon) deskIcon;
        }
    }

    public boolean isAboveLaunchBase(int x, int y) {
        int launchBaseY = mRocketIcon.getY();
        return y >= launchBaseY;
    }

    public void notifyLaunchBaseAndRocketShow() {
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.SHOW);
        mRocketIcon.setStatus(RocketIcon.SHOW);
    }

    public void notifyLaunchBaseAndRocketInvoke() {
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.ASS_ABOVE);
        mRocketIcon.setStatus(RocketIcon.ASS_ABOVE);
    }

    public void assRested() {
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.RESET);
        mRocketIcon.setStatus(RocketIcon.RESET);
    }

    public void launchRocket() {
        mRocketIcon.setStatus(RocketIcon.LAUNCHING);
        mSmokeIcon.setStatus(SmokeIcon.LAUNCHING);
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.RESET);
        launchCount++;
        boolean isYellSound = launchCount % 3 == 0;
        mSoundPool.play(isYellSound ? mYellSound : mYahooSound, 1f, 1f, 1, 0, 1f);
    }

    public void rocketLaunched() {
        mRocketIcon.setStatus(RocketIcon.LAUNCHED);
        mAssIcon.setStatus(AssIcon.LAUNCHED);
        mSmokeIcon.setStatus(SmokeIcon.RESET);
    }

}
