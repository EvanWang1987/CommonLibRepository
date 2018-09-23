package com.github.evan.common_utils.event;

/**
 * Created by Evan on 2018/9/11.
 */

public class IntentServiceAlertDialogEvent {
    private boolean mIsAlert = false;

    public IntentServiceAlertDialogEvent(boolean mIsAlert) {
        this.mIsAlert = mIsAlert;
    }

    public boolean ismIsAlert() {
        return mIsAlert;
    }

    public void setmIsAlert(boolean mIsAlert) {
        this.mIsAlert = mIsAlert;
    }
}
