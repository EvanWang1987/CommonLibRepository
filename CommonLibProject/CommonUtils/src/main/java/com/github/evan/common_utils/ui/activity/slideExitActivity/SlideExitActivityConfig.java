package com.github.evan.common_utils.ui.activity.slideExitActivity;

import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Created by Evan on 2017/12/25.
 */

public class SlideExitActivityConfig {
    private SlideExitDirection exitDirection;
    private @FloatRange(from = 0.2, to = 0.8) float slidingPercentRelativeActivityWhenNotExit;
    private long exitDuration;
    private long rollBackDuration;
    private @ColorInt int backgroundColor;

    public SlideExitActivityConfig() {
    }

    public SlideExitDirection getExitDirection() {
        return exitDirection;
    }

    public void setExitDirection(SlideExitDirection exitDirection) {
        this.exitDirection = exitDirection;
    }

    public float getSlidingPercentRelativeActivityWhenNotExit() {
        return slidingPercentRelativeActivityWhenNotExit;
    }

    public void setSlidingPercentRelativeActivityWhenNotExit(float slidingPercentRelativeActivityWhenNotExit) {
        this.slidingPercentRelativeActivityWhenNotExit = slidingPercentRelativeActivityWhenNotExit;
    }

    public long getExitDuration() {
        return exitDuration;
    }

    public void setExitDuration(long exitDuration) {
        this.exitDuration = exitDuration;
    }

    public long getRollBackDuration() {
        return rollBackDuration;
    }

    public void setRollBackDuration(long rollBackDuration) {
        this.rollBackDuration = rollBackDuration;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
