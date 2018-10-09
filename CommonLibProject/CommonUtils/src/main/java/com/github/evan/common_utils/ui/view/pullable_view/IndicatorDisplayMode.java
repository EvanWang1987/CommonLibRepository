package com.github.evan.common_utils.ui.view.pullable_view;

/**
 * Created by Evan on 2018/2/5.
 */
public enum  IndicatorDisplayMode {

    SCROLL_WITH_CONTENT(1), UNDER_CONTENT(2);

    public int value;

    IndicatorDisplayMode(int value) {
        this.value = value;
    }

    public static IndicatorDisplayMode valueOf(int value){
        IndicatorDisplayMode returnValue = SCROLL_WITH_CONTENT;
        IndicatorDisplayMode[] values = values();
        for (int i = 0, length = values.length; i < length; i++) {
            IndicatorDisplayMode temp = values[i];
            if(temp.value == value){
                returnValue = temp;
                break;
            }
        }

        return returnValue;
    }
}
