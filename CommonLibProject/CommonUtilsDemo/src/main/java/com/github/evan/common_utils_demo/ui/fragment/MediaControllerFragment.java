package com.github.evan.common_utils_demo.ui.fragment;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.mediaController.CommonMediaController;
import com.github.evan.common_utils.ui.view.mediaController.MediaControllerListener;
import com.github.evan.common_utils.utils.BrightnessUtil;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.FileUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.ScreenSnapShotUtil;
import com.github.evan.common_utils_demo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/13.
 */
public class MediaControllerFragment extends BaseFragment implements MediaControllerListener, Runnable {
    @BindView(R.id.video_view_media_controller_fragment)
    VideoView mVideoView;
    @BindView(R.id.common_media_controller)
    CommonMediaController mMediaController;
    private AlertDialog mDesignMessageDialog;
    private ScheduledThreadPoolExecutor mExecutor;
    private String mVideoFilePath = FileUtil.getApplicationDataDir() + "/demo.mp4";
    private SoundPool mSoundPool;
    private int mScreenShotSound;
    private int mSystemBrightness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_media_controller, null);
        ButterKnife.bind(this, root);
        mMediaController.setMediaControllerListener(this);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mScreenShotSound = mSoundPool.load(getContext(), com.github.evan.common_utils.R.raw.screen_shot_voice_effect, 1);
        return root;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            int systemBrightness = BrightnessUtil.getSystemBrightness(getContext());
            if(systemBrightness != mSystemBrightness){
                mSystemBrightness = systemBrightness;
                return;
            }
            float brightness = 255 / mSystemBrightness;
            BrightnessUtil.setActivityBrightness(getActivity(), brightness);
        } else {
            mSystemBrightness = BrightnessUtil.getSystemBrightness(getContext());
        }
    }

    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            mDesignMessageDialog.dismiss();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mMediaController.setTitle(ResourceUtil.getString(R.string.common_media_controller_title));
                    mMediaController.setDuration(mp.getDuration());
                    mMediaController.setCurrentPosition(0);
                    mMediaController.hideControllers();
                    mMediaController.setPlayToggleChecked(true);
                    startUpdatePositionTimer();
                }
            });
            mVideoView.setVideoPath(mVideoFilePath);
        }
    }

    @Override
    protected void loadData() {
        String title = ResourceUtil.getString(R.string.warning);
        String message = ResourceUtil.getString(R.string.releasing_video_file);
        mDesignMessageDialog = DialogFactory.createDesignMessageDialog(getContext(), -1, title, message);
        mDesignMessageDialog.setCancelable(false);
        mDesignMessageDialog.setCanceledOnTouchOutside(false);
        mDesignMessageDialog.show();
        ThreadPoolExecutor ioThreadPool = ThreadManager.getInstance().getIOThreadPool();
        ioThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                FileUtil.FileStatus fileStatus = ResourceUtil.copyAsset2File("demo.mp4", mVideoFilePath, true);
                boolean isCopiedSuccess = fileStatus == FileUtil.FileStatus.COPY_SUCCESS;
                sendEmptyMessage(isCopiedSuccess ? LOAD_COMPLETE : UNKNOW_ERROR);
            }
        });

    }

    @Override
    public void onButtonClicked(int id) {
        if (id == MediaControllerListener.VIEW_ID_SCREEN_SNAP_SHOT_BUTTON) {
            ThreadManager.getInstance().getIOThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = ScreenSnapShotUtil.catchVideoSnapShot(mVideoFilePath, mVideoView.getCurrentPosition() * 1000);
                        File file = new File(FileUtil.getApplicationDataDir(), "screen_snap_shot_" + DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault()) + ".png");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.close();
                        bitmap.recycle();
                        mSoundPool.play(mScreenShotSound, 1f, 1f, 1, 0, 1f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Override
    public void onToggleChecked(int id, boolean isChecked) {
    }

    @Override
    public void onSeekBarSlide(int progress, int maxProgress) {

    }

    @Override
    public void onGestureBegin() {
    }

    @Override
    public void onGestureEnd() {
    }

    @Override
    public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
    }

    @Override
    public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
    }

    @Override
    public void onSingleTap() {
        if (mMediaController.isControllersShowed()) {
            mMediaController.hideControllers();
        } else {
            mMediaController.showControllers();
        }
    }

    @Override
    public void onDoubleTap() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    @Override
    public void onScale(float scaleFactor, int state, ScaleGestureDetector detector) {

    }

    @Override
    public void onLongPress() {

    }

    private void startUpdatePositionTimer() {
        mExecutor = new ScheduledThreadPoolExecutor(2);
        mExecutor.scheduleAtFixedRate(this, 0, 1000l, TimeUnit.MILLISECONDS);
    }

    private void stopUpdatePositionTimer() {
        if (null != mExecutor) {
            if (!mExecutor.isShutdown()) {
                mExecutor.shutdownNow();
            }
            mExecutor = null;
        }
    }

    @Override
    public void run() {
        Logger.d("timer run");
        mMediaController.setCurrentPosition(mVideoView.getCurrentPosition());
    }
}
