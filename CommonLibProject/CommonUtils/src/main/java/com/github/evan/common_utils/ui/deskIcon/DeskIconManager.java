package com.github.evan.common_utils.ui.deskIcon;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.icons.AssIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.DustbinIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.LaunchBaseIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.LogCatDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.RocketIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.SmokeIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.WifiSignalLevelIcon;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/12/23.
 */
public class DeskIconManager {
    private static DeskIconManager mInstance = null;

    private Context mContext;
    private AssIcon mAssIcon;
    private LaunchBaseIcon mLaunchBaseIcon;
    private RocketIcon mRocketIcon;
    private int launchCount = 0;
    private SoundPool mSoundPool;
    private int mYahooSound;
    private int mYellSound;
    private boolean mIsRocketPrepared = false;
    private SmokeIcon mSmokeIcon;
    private DustbinIcon mDustbinIcon;
    private AlertDialog mConfirmCloseRocketDialog;
    private LogCatDeskIcon mLogCatDeskIcon;
    private boolean mIsLogCatIconShowing;
    private WifiSignalLevelIcon mWifiSignalIcon;
    private boolean mIsWifiSignalIconShowing;

    public static DeskIconManager getInstance(Context context) {
        if (null == mInstance) {
            synchronized (DeskIconManager.class) {
                mInstance = new DeskIconManager(context);
            }
        }
        return mInstance;
    }

    private DeskIconManager(Context context) {
        mContext = context;
    }

    public void prepareRocket() {
        mAssIcon = new AssIcon(mContext);
        mLaunchBaseIcon = new LaunchBaseIcon(mContext);
        mRocketIcon = new RocketIcon(mContext);
        mSmokeIcon = new SmokeIcon(mContext);
        mDustbinIcon = new DustbinIcon(mContext);

        mLaunchBaseIcon.alert();
        mLaunchBaseIcon.hidden();
        mRocketIcon.alert();
        mRocketIcon.hidden();
        mSmokeIcon.alert();
        mSmokeIcon.hidden();
        mDustbinIcon.alert();
        mDustbinIcon.hidden();
        mAssIcon.alert();

        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mYellSound = mSoundPool.load(mContext, R.raw.yell, 1);
        mYahooSound = mSoundPool.load(mContext, R.raw.yahoo, 1);
        mIsRocketPrepared = true;
    }

    public boolean isRocketPrepared(){
        return mIsRocketPrepared;
    }

    public void releaseRocket(){
        if(null != mAssIcon)    mAssIcon.release();
        if(null != mAssIcon)    mLaunchBaseIcon.release();
        if(null != mAssIcon)    mRocketIcon.release();
        if(null != mAssIcon)    mSmokeIcon.release();
        if(null != mAssIcon)    mDustbinIcon.release();
        mIsRocketPrepared = false;
    }

    public void showLogCatIcon(){
        if(mIsLogCatIconShowing){
           return;
        }

        if(null == mLogCatDeskIcon){
            mLogCatDeskIcon = new LogCatDeskIcon(mContext);
        }

        mLogCatDeskIcon.alert();
        mIsLogCatIconShowing = true;
    }

    public boolean isLogCatIconShowing(){
        return mIsLogCatIconShowing;
    }

    public void addLog(CharSequence log){
        mLogCatDeskIcon.addLog(log);
    }

    public CharSequence getAllLog(){
        return mLogCatDeskIcon.getAllLog();
    }

    public void dismissLogCatIcon(){
        if(mIsLogCatIconShowing){
            mLogCatDeskIcon.dismiss();
            mLogCatDeskIcon.release();
        }
    }

    public void showWifiSignalIcon(){
        if(mIsWifiSignalIconShowing){
            return;
        }

        if(null == mWifiSignalIcon){
            mWifiSignalIcon = new WifiSignalLevelIcon(mContext);
        }
        mWifiSignalIcon.alert();
        mIsWifiSignalIconShowing = true;
    }

    public boolean isWifiSignalIconShowing(){
        return mIsWifiSignalIconShowing;
    }

    public void dismissWifiSignalIcon(){
        if(mIsLogCatIconShowing){
            mWifiSignalIcon.dismiss();
            mWifiSignalIcon.release();
        }
    }

    public void setWifiSignalIconStatus(int status){
        if(null == mWifiSignalIcon){
            return;
        }
        mWifiSignalIcon.setStatus(status);
    }



    public void release() {
        if(null != mAssIcon)    mAssIcon.release();
        if(null != mAssIcon)    mLaunchBaseIcon.release();
        if(null != mAssIcon)    mRocketIcon.release();
        if(null != mAssIcon)    mSmokeIcon.release();
        if(null != mAssIcon)    mDustbinIcon.release();
        mContext = null;
        mInstance = null;
        mIsRocketPrepared = false;
    }

    public void resetAllDeskIcons(){
        mAssIcon.setStatus(AssIcon.RESET_ICON_TO_SCREEN_RIGHT_WITH_CENTER_VERTICAL);
        mDustbinIcon.setStatus(DustbinIcon.RESET);
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.RESET);
        mRocketIcon.setStatus(RocketIcon.RESET);
        mSmokeIcon.setStatus(SmokeIcon.RESET);
    }

    public boolean isAboveLaunchBase(int x, int y) {
        int launchBaseY = mRocketIcon.getY();
        return y >= launchBaseY;
    }

    public boolean isAboveDustbin(int x, int y){
        int dustbinRight = mDustbinIcon.getRootView().getWidth();
        int dustbinBottom = mDustbinIcon.getRootView().getHeight();

        return x <= dustbinRight && y <= dustbinBottom;
    }

    public void showCloseDeskIconConfirmDialog(){
        if(null == mConfirmCloseRocketDialog){
            String title = ResourceUtil.getString(R.string.notice);
            String message = ResourceUtil.getString(R.string.confirm_to_close_rocket);
            String confirm = ResourceUtil.getString(R.string.confirm);
            String cancel = ResourceUtil.getString(R.string.cancel);
            AlertDialog dialog = DialogFactory.createDesignMessageDialog(mContext, -1, title, message);
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    release();
                    dialog.dismiss();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAssIcon.setStatus(AssIcon.SHOW);
                    dialog.dismiss();
                }
            });
            mConfirmCloseRocketDialog = dialog;
        }

        if(!mConfirmCloseRocketDialog.isShowing()){
            mConfirmCloseRocketDialog.show();
        }

    }

    public void notifyLaunchBaseAndRocketShow() {
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.SHOW);
        mRocketIcon.setStatus(RocketIcon.SHOW);
    }

    public void notifyDustbinShow(){
        mDustbinIcon.setStatus(DustbinIcon.SHOW);
    }

    public void notifyAssAboveDustbin(){
        mDustbinIcon.setStatus(DustbinIcon.ASS_ABOVE);
    }

    public void notifyDustbinDismiss(){
        mDustbinIcon.setStatus(DustbinIcon.RESET);
    }

    public void notifyLaunchBaseAndRocketInvoke() {
        mLaunchBaseIcon.setStatus(LaunchBaseIcon.ASS_ABOVE);
        mRocketIcon.setStatus(RocketIcon.ASS_ABOVE);
        mDustbinIcon.setStatus(DustbinIcon.RESET);
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
