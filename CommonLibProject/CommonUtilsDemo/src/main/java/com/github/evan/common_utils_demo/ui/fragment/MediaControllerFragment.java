package com.github.evan.common_utils_demo.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.mediaController.CommonMediaController;
import com.github.evan.common_utils.ui.view.mediaController.MediaControllerListener;
import com.github.evan.common_utils.utils.FileUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import java.sql.Time;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_media_controller, null);
        ButterKnife.bind(this, root);
        mMediaController.setMediaControllerListener(this);
        return root;
    }

    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            mDesignMessageDialog.dismiss();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
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
        mDesignMessageDialog = DialogFactory.createDesignMessageDialog(getContext(), -1, "提示", "正在释放视频文件");
        mDesignMessageDialog.show();
        ThreadPoolExecutor ioThreadPool = ThreadManager.getInstance().getIOThreadPool();
        ioThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                FileUtil.FileStatus fileStatus = ResourceUtil.copyAsset2File("demo.mp4", mVideoFilePath, true);
                boolean isCopiedSuccess = fileStatus == FileUtil.FileStatus.COPY_SUCCESS;
                sendEmptyMessage(isCopiedSuccess ? LOAD_COMPLETE : UNKNOW_ERROR);
            }
        });

    }

    @Override
    public void onButtonClicked(int id) {
        Logger.d("onButtonClicked, id: " + id);
    }

    @Override
    public void onToggleChecked(int id, boolean isChecked) {
        Logger.d("onToggleChecked, id: " + id + ", isChecked: " + isChecked);
    }

    @Override
    public void onSeekBarSlide(int progress, int maxProgress) {

    }

    @Override
    public void onGestureBegin() {
        Logger.d("onGestureBegin");
    }

    @Override
    public void onGestureEnd() {
        Logger.d("onGestureEnd");
    }

    @Override
    public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        Logger.d("onHorizontalSlide, downPositionAtParent: " + downPositionAtParent + ", distanceX: " + distanceX);
    }

    @Override
    public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent) {
        Logger.d("onVerticalSlide, downPositionAtParent: " + downPositionAtParent);
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
        if(mVideoView.isPlaying()){
            mVideoView.pause();
        }else{
            mVideoView.start();
        }
    }

    @Override
    public void onScale(float scaleFactor, int state, ScaleGestureDetector detector) {

    }

    @Override
    public void onLongPress() {

    }

    private void startUpdatePositionTimer(){
        mExecutor = new ScheduledThreadPoolExecutor(2);
        mExecutor.scheduleAtFixedRate(this, 0, 1000l, TimeUnit.MILLISECONDS);
    }

    private void stopUpdatePositionTimer(){
        if(null != mExecutor){
            if(!mExecutor.isShutdown()){
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
