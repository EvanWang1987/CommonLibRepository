package com.github.evan.common_utils_demo.designMode.factory;

import com.github.evan.common_utils_demo.event.VehicleEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/15.
 */

public class AudiVehicle extends Vehicle {

    public AudiVehicle(VehicleType vehicleType) {
        super(vehicleType);
    }

    @Override
    public void move() {
        EventBus.getDefault().post(new VehicleEvent("Audi move....."));
    }
}
