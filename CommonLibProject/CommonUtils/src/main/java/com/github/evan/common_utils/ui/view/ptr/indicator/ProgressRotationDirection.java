package com.github.evan.common_utils.ui.view.ptr.indicator;

/**
 * Created by Evan on 2017/11/30.
 */

public enum  ProgressRotationDirection {
    CLOCKWISE(0), ANTI_CLOCKWISE(1);

    public int value;

    ProgressRotationDirection(int value) {
        this.value = value;
    }

    public static ProgressRotationDirection valueOf(int value){
        ProgressRotationDirection returnValue = CLOCKWISE;
        ProgressRotationDirection[] values = values();
        int N = values.length;
        for (int i = 0; i < N; i++) {
            ProgressRotationDirection temp = values[i];
            if(temp.value == value){
                returnValue = temp;
                break;
            }
        }

        return returnValue;
    }
}
