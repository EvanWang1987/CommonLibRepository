package com.github.evan.common_utils_demo.runnable;

/**
 * Created by Evan on 2017/12/28.
 */

public enum Operate {
    ADD(0), DELETE(1), SET(2), GET(3), GET_SIZE(4);

    Operate(int value) {
        this.value = value;
    }

    public int value;

    public static Operate valueOf(int value){
        Operate returnValue = ADD;
        Operate[] values = values();
        for (int i = 0; i < values.length; i++) {
            Operate temp = values[i];
            if(temp.value == value){
                returnValue =  temp;
                break;
            }
        }

        return returnValue;
    }
}
