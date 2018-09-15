package com.github.evan.common_utils_demo.designMode.factory;

import com.github.evan.common_utils_demo.event.CarEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/15.
 */

public class Car implements IMovable {

    @Override
    public void move() {
        EventBus.getDefault().post(new CarEvent("Car move..."));
    }

}
