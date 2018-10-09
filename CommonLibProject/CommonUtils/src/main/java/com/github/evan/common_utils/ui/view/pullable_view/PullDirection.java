package com.github.evan.common_utils.ui.view.pullable_view;

/**
 * Created by Evan on 2018/2/5.
 */

public enum PullDirection {

    UNKNOWN(-1), TOP_TO_BOTTOM(1), BOTTOM_TO_TOP(2), LEFT_TO_RIGHT(3), RIGHT_TO_LEFT(4), BOTH_TOP_BOTTOM(5), BOTH_LEFT_RIGHT(6);

    public int value;

    PullDirection(int value) {
        this.value = value;
    }

    public static PullDirection valueOf(int value){
        PullDirection returnValue = UNKNOWN;
        PullDirection[] values = values();
        for (int i = 0, length = values.length; i < length; i++) {
            PullDirection temp = values[i];
            if(temp.value == value){
                returnValue = temp;
                break;
            }
        }

        return returnValue;
    }
}
