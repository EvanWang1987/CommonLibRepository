package com.github.evan.common_utils.event;

/**
 * Created by Evan on 2018/9/10.
 */

public class ActivityLifeCycleEvent {
    private String mMessage;

    public ActivityLifeCycleEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
