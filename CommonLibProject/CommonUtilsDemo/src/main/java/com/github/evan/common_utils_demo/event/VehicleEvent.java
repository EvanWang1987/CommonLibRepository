package com.github.evan.common_utils_demo.event;

/**
 * Created by Evan on 2018/9/15.
 */

public class VehicleEvent {
    private String mMessage;

    public VehicleEvent(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
