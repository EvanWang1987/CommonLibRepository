package com.github.evan.common_utils.event;

/**
 * Created by Evan on 2018/9/15.
 */

public class SingletonEvent {
    private String mMessage;

    public SingletonEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
