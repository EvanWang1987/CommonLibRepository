package com.github.evan.common_utils.ui.view.mediaController;

/**
 * Created by Evan on 2017/12/14.
 */
public interface IMediaController {
    void showControllers();

    void hideControllers();

    void setCurrentPosition(long position);

    void setDuration(long duration);

    void setPlayToggleChecked(boolean isChecked);

    void setTitle(CharSequence title);

    void resetIdle();

    void showReplayButton();

    void showPlayToggle();
}
