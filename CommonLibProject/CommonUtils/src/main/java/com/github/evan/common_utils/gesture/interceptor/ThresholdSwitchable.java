package com.github.evan.common_utils.gesture.interceptor;

/**
 * Created by Evan on 2017/11/25.
 */

public interface ThresholdSwitchable {

    boolean isArriveTouchEventThreshold(InterceptMode interceptMode, TouchEventDirection xDirection, TouchEventDirection yDirection);

}
