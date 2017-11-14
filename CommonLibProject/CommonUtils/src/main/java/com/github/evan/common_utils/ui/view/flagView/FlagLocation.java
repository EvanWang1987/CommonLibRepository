package com.github.evan.common_utils.ui.view.flagView;

/**
 * Created by Evan on 2017/11/10.
 */

public enum  FlagLocation {
    UNKNOWN(-1), LEFT_TOP(0), LEFT_BOTTOM(1), RIGHT_TOP(2), RIGHT_BOTTOM(3);

    public int value;

    FlagLocation(int value){
        this.value = value;
    }

    public static FlagLocation valueOf(int value){
        FlagLocation returnValue = UNKNOWN;
        for (int i = 0; i < values().length; i++) {
            if(value == values()[i].value){
                returnValue = values()[i];
            }
        }
        return returnValue;
    }


}
