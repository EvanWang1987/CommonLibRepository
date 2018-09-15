package com.github.evan.common_utils_demo.designMode.factory;

/**
 * Created by Evan on 2018/9/15.
 */

public class BMWFactory extends AbsVehicleFactory {
    @Override
    public Vehicle createVehicle(VehicleType type) {
        return new BMWVehicle(type);
    }
}
