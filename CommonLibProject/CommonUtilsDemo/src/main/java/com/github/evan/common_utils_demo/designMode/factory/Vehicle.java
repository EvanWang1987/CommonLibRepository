package com.github.evan.common_utils_demo.designMode.factory;

/**
 * Created by Evan on 2018/9/15.
 */

public abstract class Vehicle implements IMovable{
    protected VehicleType vehicleType;

    public Vehicle(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
