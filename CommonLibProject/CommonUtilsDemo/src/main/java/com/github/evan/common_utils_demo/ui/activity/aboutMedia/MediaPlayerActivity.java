package com.github.evan.common_utils_demo.ui.activity.aboutMedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.github.evan.common_utils.bean.Mp3Information;
import com.github.evan.common_utils.bean.MusicInfo;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.FileUtil;
import com.github.evan.common_utils.utils.MediaUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.MusicAdapter;
import com.github.evan.common_utils_demo.ui.dialog.ListBottomSheetDialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerActivity extends BaseActivity {
    private ProgressDialog mScanningDialog;
    private ListBottomSheetDialogFragment mMusicListDialog;
    private List<MusicInfo> mMusicInfo = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mScanningDialog = DialogFactory.createProgressDialog(this, "", getString(R.string.scanning_media_file), ProgressDialog.STYLE_SPINNER, 0, 0);
        mMusicListDialog = new ListBottomSheetDialogFragment();
        mScanningDialog.show();
        ThreadManager.getInstance().getIOThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                String applicationDataDir = FileUtil.getApplicationDataDir();
                FileUtil.FileStatus statusOne = ResourceUtil.copyAsset2File("1.mp3", applicationDataDir + File.separator + "1.mp3", true);
                FileUtil.FileStatus statusTwo = ResourceUtil.copyAsset2File("2.mp3", applicationDataDir + File.separator + "2.mp3", true);
                FileUtil.FileStatus statusThree = ResourceUtil.copyAsset2File("3.mp3", applicationDataDir + File.separator + "3.mp3", true);
                FileUtil.FileStatus statusFour = ResourceUtil.copyAsset2File("4.mp3", applicationDataDir + File.separator + "4.mp3", true);
                FileUtil.FileStatus statusFive = ResourceUtil.copyAsset2File("5.mp3", applicationDataDir + File.separator + "5.mp3", true);

                if(statusOne == FileUtil.FileStatus.COPY_SUCCESS){
                    Mp3Information informationOne = MediaUtil.getMp3Information(applicationDataDir + File.separator + "1.mp3");
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setFilePath(applicationDataDir + File.separator + "1.mp3");
                    musicInfo.setMp3Information(informationOne);
                    mMusicInfo.add(musicInfo);
                }

                if(statusTwo == FileUtil.FileStatus.COPY_SUCCESS){
                    Mp3Information informationOne = MediaUtil.getMp3Information(applicationDataDir + File.separator + "2.mp3");
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setFilePath(applicationDataDir + File.separator + "2.mp3");
                    musicInfo.setMp3Information(informationOne);
                    mMusicInfo.add(musicInfo);
                }

                if(statusThree == FileUtil.FileStatus.COPY_SUCCESS){
                    Mp3Information informationOne = MediaUtil.getMp3Information(applicationDataDir + File.separator + "3.mp3");
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setFilePath(applicationDataDir + File.separator + "3.mp3");
                    musicInfo.setMp3Information(informationOne);
                    mMusicInfo.add(musicInfo);
                }

                if(statusFour == FileUtil.FileStatus.COPY_SUCCESS){
                    Mp3Information informationOne = MediaUtil.getMp3Information(applicationDataDir + File.separator + "4.mp3");
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setFilePath(applicationDataDir + File.separator + "4.mp3");
                    musicInfo.setMp3Information(informationOne);
                    mMusicInfo.add(musicInfo);
                }

                if(statusFive == FileUtil.FileStatus.COPY_SUCCESS){
                    Mp3Information informationOne = MediaUtil.getMp3Information(applicationDataDir + File.separator + "5.mp3");
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setFilePath(applicationDataDir + File.separator + "5.mp3");
                    musicInfo.setMp3Information(informationOne);
                    mMusicInfo.add(musicInfo);
                }
                sendEmptyMessage(LOAD_COMPLETE);
            }
        });
    }

    public List<MusicInfo> getMusicInfo(){
        return mMusicInfo;
    }

    @OnClick({R.id.btn_media_list})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_media_list:
                mMusicListDialog.show(getSupportFragmentManager(), ListBottomSheetDialogFragment.class.getSimpleName());
                mMusicListDialog.setData(mMusicInfo);
                break;
        }
    }

    @Override
    public void onHandleMessage(Message message) {
        super.onHandleMessage(message);
        switch (message.what){
            case LOAD_COMPLETE:
                mScanningDialog.dismiss();
                mMusicListDialog.show(getSupportFragmentManager(), ListBottomSheetDialogFragment.class.getSimpleName());
                break;
        }

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_media_player;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        BaseActivityConfig config = new BaseActivityConfig();
        config.isPressTwiceToExit = false;
        return config;
    }


}
