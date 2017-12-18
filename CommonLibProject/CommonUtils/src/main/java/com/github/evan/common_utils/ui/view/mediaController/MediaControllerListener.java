package com.github.evan.common_utils.ui.view.mediaController;

import android.view.ScaleGestureDetector;

/**
 * Created by Evan on 2017/12/14.
 */

public interface MediaControllerListener {
    public static final int VIEW_ID_BACK_BUTTON = 1;
    public static final int VIEW_ID_DAN_MARK = 2;
    public static final int VIEW_ID_SHARE_BUTTON = 3;
    public static final int VIEW_ID_MORE_BUTTON = 4;
    public static final int VIEW_ID_LOCK_SCREEN = 5;
    public static final int VIEW_ID_SCREEN_SNAP_SHOT_BUTTON = 6;
    public static final int VIEW_ID_PLAY_TOGGLE = 7;
    public static final int VIEW_ID_VIDEO_DEFINITION_BUTTON = 8;
    public static final int VIEW_ID_MUTE_TOGGLE = 9;
    public static final int VIEW_ID_FULL_SCREEN_TOGGLE = 10;
    public static final int VIEW_ID_NEXT_BUTTON = 11;
    public static final int VIEW_ID_REPLAY_BUTTON = 11;

    void onButtonClicked(int id);

    void onToggleChecked(int id, boolean isChecked);

    void onSeekBarSlide(int progress, int maxProgress);

    void onGestureBegin();

    void onGestureEnd();

    void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent);

    void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY, int downPositionAtParent);

    void onSingleTap();

    void onDoubleTap();

    void onScale(float scaleFactor, int state, ScaleGestureDetector detector);

    void onLongPress();

}
