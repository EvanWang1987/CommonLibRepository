package com.github.evan.common_utils_demo.designMode.factory;

import com.github.evan.common_utils_demo.event.VehicleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/15.
 */

public class BMWVehicle extends Vehicle {

    public BMWVehicle(VehicleType vehicleType) {
        super(vehicleType);
    }

    @Override
    public void move() {
        EventBus.getDefault().post(new VehicleEvent("BMW move....."));
    }
}
