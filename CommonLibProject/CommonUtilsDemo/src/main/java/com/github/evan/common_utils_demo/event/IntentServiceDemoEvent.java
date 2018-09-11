package com.github.evan.common_utils_demo.event;

/**
 * Created by Evan on 2018/9/11.
 */

public class IntentServiceDemoEvent {
    private String mMessage;

    public IntentServiceDemoEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
