package com.github.evan.common_utils.ui.activity.slideExitActivity;

/**
 * Created by Evan on 2017/12/18.
 */

public enum  SlideExitDirection {
    LEFT_TO_RIGHT(0), RIGHT_TO_LEFT(1), TOP_TO_BOTTOM(2), BOTTOM_TO_TOP(3);

    public int value;

    SlideExitDirection(int value) {
        this.value = value;
    }

    public static SlideExitDirection valueOf(int value){
        SlideExitDirection returnValue = LEFT_TO_RIGHT;
        SlideExitDirection[] values = values();
        for (int i = 0; i < values.length; i++) {
            SlideExitDirection temp = values[i];
            if(temp.value == value){
                returnValue = temp;
                break;
            }
        }

        return returnValue;
    }
}
