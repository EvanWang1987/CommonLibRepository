package com.github.evan.common_utils.event;

/**
 * Created by Evan on 2018/9/15.
 */

public class CarEvent {
    private String mMessage;

    public CarEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
