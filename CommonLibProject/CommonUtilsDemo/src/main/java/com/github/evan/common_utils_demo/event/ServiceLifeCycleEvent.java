package com.github.evan.common_utils_demo.event;

/**
 * Created by Evan on 2018/9/10.
 */

public class ServiceLifeCycleEvent {
    private String mMessage;

    public ServiceLifeCycleEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
