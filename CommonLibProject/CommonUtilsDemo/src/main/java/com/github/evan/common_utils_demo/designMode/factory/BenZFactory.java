package com.github.evan.common_utils_demo.designMode.factory;

/**
 * Created by Evan on 2018/9/15.
 */

public class BenZFactory extends AbsVehicleFactory {
    @Override
    public Vehicle createVehicle(VehicleType type) {
        return new BenZVehicle(type);
    }
}
