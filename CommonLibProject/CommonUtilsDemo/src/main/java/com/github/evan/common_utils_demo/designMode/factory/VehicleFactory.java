package com.github.evan.common_utils_demo.designMode.factory;

/**
 * Created by Evan on 2018/9/15.
 */

public class VehicleFactory extends AbsVehicleFactory {
    private static BenZFactory mBenZFactory;
    private static BMWFactory mBmwFactory;
    private static AudiFactory mAudiFactory;

    static {
        mBenZFactory = new BenZFactory();
        mBmwFactory = new BMWFactory();
        mAudiFactory = new AudiFactory();
    }


    @Override
    public Vehicle createVehicle(VehicleType type) {
        switch (type){
            case BENZ:
                return mBenZFactory.createVehicle(type);

            case BMW:
                return mBmwFactory.createVehicle(type);

            case AUDI:
                return mAudiFactory.createVehicle(type);
        }
        return null;
    }
}
