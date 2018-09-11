package com.github.evan.common_utils_demo.event;

/**
 * Created by Evan on 2018/9/11.
 */

public class IntentServiceProgressEvent {
    private int mProgress;

    public IntentServiceProgressEvent(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }
}
