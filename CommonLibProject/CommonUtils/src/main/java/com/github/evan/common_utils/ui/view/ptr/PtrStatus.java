package com.github.evan.common_utils.ui.view.ptr;

/**
 * Created by Evan on 2017/11/29.
 */
public enum PtrStatus {
    IDLE(0), START_PULL(1), PULLING(2), RELEASE_TO_REFRESH(3), REFRESHING(4), REFRESHED(5);

    public int value;

    PtrStatus(int value) {
        this.value = value;
    }

    public static PtrStatus valueOf(int value){
        PtrStatus returnValue = IDLE;
        PtrStatus[] values = values();
        int N = values.length;
        for (int i = 0; i < N; i++) {
            PtrStatus temp = values[i];
            if(temp.value == value){
                returnValue = temp;
                break;
            }
        }

        return returnValue;
    }
}
