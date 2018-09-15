package com.github.evan.common_utils_demo.ui.activity.aboutDesignMode;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.designMode.factory.Car;
import com.github.evan.common_utils_demo.designMode.factory.SimpleCarFactory;
import com.github.evan.common_utils_demo.designMode.factory.Vehicle;
import com.github.evan.common_utils_demo.designMode.factory.VehicleFactory;
import com.github.evan.common_utils_demo.designMode.factory.VehicleType;
import com.github.evan.common_utils_demo.event.CarEvent;
import com.github.evan.common_utils_demo.event.VehicleEvent;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/15.
 */

public class AboutFactoryActivity extends BaseLogCatActivity {
    private AlertDialog mChoiceDialog;
    private String[] mArray;
    private String mSelectedItem;
    private VehicleFactory mVehicleFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArray = ResourceUtil.getStringArray(R.array.vehicle_factory_item);
        mChoiceDialog = DialogFactory.createMDSingleChoiceDialog(this, mArray, getString(R.string.warning), getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectedItem = mArray[which];
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VehicleType type = null;
                if(mSelectedItem.equals(getString(R.string.benz))){
                    type = VehicleType.BENZ;
                }else if(mSelectedItem.equals(getString(R.string.bmw))){
                    type = VehicleType.BMW;
                }else{
                    type = VehicleType.AUDI;
                }
                Vehicle vehicle = mVehicleFactory.createVehicle(type);
                addLog("创建Vehicle, type: " + vehicle.getVehicleType());
                vehicle.move();
            }
        });
        mVehicleFactory = new VehicleFactory();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onReceiveEvent(CarEvent event){
        addLog(event.getmMessage());
    }

    @Subscribe
    public void onReceiveVehicleEvent(VehicleEvent event){
        addLog(event.getmMessage());
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_factory_design, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_simple_factory, R.id.card_abstract_factory})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_simple_factory:
                Car car = SimpleCarFactory.createCar();
                addLog("SimpleCarFactory.createCar()");
                car.move();
                break;

            case R.id.card_abstract_factory:
                mChoiceDialog.show();
                break;

        }
    }
}
