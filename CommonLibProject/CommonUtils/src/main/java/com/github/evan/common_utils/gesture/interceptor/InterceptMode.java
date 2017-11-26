package com.github.evan.common_utils.gesture.interceptor;

/**
 * Created by Evan on 2017/11/25.
 */
public enum  InterceptMode {
    USE_SUPER_DEFAULT(-1), ALL_BY_MYSELF(0), ALL_BY_MYSELF_BUT_THRESHOLD(1), HORIZONTAL(2), HORIZONTAL_BUT_THRESHOLD(3), VERTICAL(4), VERTICAL_BUT_THRESHOLD(5);

    public int value;

    InterceptMode(int value) {
        this.value = value;
    }

    public static InterceptMode valueOf(int value){
        InterceptMode returnValue = USE_SUPER_DEFAULT;
        InterceptMode[] values = values();
        int N = values.length;
        for (int index = 0; index < N; index++) {
            InterceptMode element = values[index];
            if(element.value == value){
                returnValue = element;
                break;
            }
        }
        return returnValue;
    }
}
