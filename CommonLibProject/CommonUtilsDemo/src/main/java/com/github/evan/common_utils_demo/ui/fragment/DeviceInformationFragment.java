package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.evan.common_utils.manager.ProcessManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.DeviceUtil;
import com.github.evan.common_utils.utils.FileUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils.utils.UnitConvertUil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/19.
 */
public class DeviceInformationFragment extends BaseFragment {
    @BindView(R.id.txt_device_brand)
    TextView mTxtDeviceBrand;
    @BindView(R.id.txt_device_manufacturer)
    TextView mTxtDeviceManufacturer;
    @BindView(R.id.current_device_model)
    TextView mTxtDeviceModel;
    @BindView(R.id.current_device_os_version)
    TextView mTxtDeviceOsVersion;
    @BindView(R.id.current_device_os_code_build_version)
    TextView mTxtDeviceOsCodeBuildVersion;
    @BindView(R.id.current_device_api_level)
    TextView mTxtDeviceApiLevel;
    @BindView(R.id.current_device_radio_version)
    TextView mTxtDeviceRadioVersion;
    @BindView(R.id.current_device_sdcard)
    TextView mTxtDeviceSdcard;
    @BindView(R.id.current_device_ram)
    TextView mTxtDeviceRam;
    @BindView(R.id.txt_screen_real_width)
    TextView mTxtScreenRealWidth;
    @BindView(R.id.txt_screen_real_height)
    TextView mTxtScreenRealHeight;
    @BindView(R.id.txt_screen_resolution)
    TextView mTxtScreenResolution;
    @BindView(R.id.txt_screen_size)
    TextView mTxtScreenSize;
    @BindView(R.id.txt_screen_status_bar_height)
    TextView mTxtScreenStatusBarHeight;
    @BindView(R.id.txt_screen_xdpi)
    TextView mTxtScreenXDpi;
    @BindView(R.id.txt_screen_ydpi)
    TextView mTxtScreenYDpi;
    @BindView(R.id.txt_screen_dpi)
    TextView mTxtScreenDpi;
    @BindView(R.id.txt_screen_density)
    TextView mTxtScreenDensity;
    @BindView(R.id.txt_screen_scale_density)
    TextView mTxtScreenScaleDensity;
    @BindView(R.id.etxt_input_convert_source_value)
    EditText mETxtSourceValue;
    @BindView(R.id.txt_convert_result)
    TextView mTxtConvertedResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_screen_information, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String deviceBrand = DeviceUtil.getDeviceBrand();                   //设备品牌
        String deviceManufacturer = DeviceUtil.getDeviceManufacturer();     //制造商
        String deviceModel = DeviceUtil.getDeviceModel();                   //设备型号
        String osVersionCode = DeviceUtil.getOsVersionCode();               //系统版本号
        String osCodeBuildVersion = DeviceUtil.getOsCodeBuildVersionName();               //系统代码编译版本
        int apiLevel = DeviceUtil.getApiLevel();                            //API Level
        String radioVersion = DeviceUtil.getRadioVersion();                 //基带版本
        String sdcardSize = FileUtil.getSdcardSize();
        String sdcardFreeSize = FileUtil.getSdcardFreeSize();
        String totalRamWithUnit = ProcessManager.getInstance().getTotalRamWithUnit();
        String availableRamWithUnit = ProcessManager.getInstance().getAvailableRamWithUnit();


        int realScreenWidthOfPx = DensityUtil.getRealScreenWidthOfPx();
        int realScreenHeightOfPx = DensityUtil.getRealScreenHeightOfPx();
        String resolutionString = realScreenWidthOfPx + " * " + realScreenHeightOfPx;
        float screenSize = DensityUtil.getScreenSize();
        float statusBarHeight = DensityUtil.getStatusBarHeight();
        float xDpi = DensityUtil.getXDpi();
        float yDpi = DensityUtil.getYDpi();
        int screenDpi = DensityUtil.getScreenDpi();
        float density = DensityUtil.getDensity();
        float scaleDensity = DensityUtil.getScaleDensity();

        mTxtDeviceBrand.setText(getString(R.string.current_device_brand, deviceBrand));
        mTxtDeviceManufacturer.setText(getString(R.string.current_device_manufacturer, deviceManufacturer));
        mTxtDeviceModel.setText(getString(R.string.current_device_model, deviceModel));
        mTxtDeviceOsVersion.setText(getString(R.string.current_device_os_version, osVersionCode));
        mTxtDeviceOsCodeBuildVersion.setText(getString(R.string.current_device_os_code_build_version, osCodeBuildVersion));
        mTxtDeviceApiLevel.setText(getString(R.string.current_device_api_level, apiLevel + ""));
        mTxtDeviceRadioVersion.setText(getString(R.string.current_device_radio_version, radioVersion));
        mTxtDeviceSdcard.setText(getString(R.string.current_device_sdcard, StringUtil.isEmptyString(sdcardSize, true) || StringUtil.isEmptyString(sdcardFreeSize, true) ? getString(R.string.un_available_sdcard) : sdcardFreeSize + " / " + sdcardSize));
        mTxtDeviceRam.setText(getString(R.string.current_device_ram, StringUtil.isEmptyString(totalRamWithUnit, true) || StringUtil.isEmptyString(availableRamWithUnit, true) ? getString(R.string.un_available_ram) : availableRamWithUnit + " / " + totalRamWithUnit));


        mTxtScreenRealWidth.setText(ResourceUtil.getString(R.string.current_device_screen_real_width, realScreenWidthOfPx));
        mTxtScreenRealHeight.setText(ResourceUtil.getString(R.string.current_device_screen_real_height, realScreenHeightOfPx));
        mTxtScreenResolution.setText(ResourceUtil.getString(R.string.current_device_screen_resolution, resolutionString));

        mTxtScreenSize.setText(ResourceUtil.getString(R.string.current_device_screen_size, screenSize + ""));
        mTxtScreenStatusBarHeight.setText(ResourceUtil.getString(R.string.current_device_system_status_bar_height, statusBarHeight + ""));

        mTxtScreenXDpi.setText(ResourceUtil.getString(R.string.current_device_screen_xdpi, xDpi + ""));
        mTxtScreenYDpi.setText(ResourceUtil.getString(R.string.current_device_screen_ydpi, yDpi + ""));
        mTxtScreenDpi.setText(ResourceUtil.getString(R.string.current_device_screen_dpi, screenDpi));
        mTxtScreenDensity.setText(ResourceUtil.getString(R.string.current_device_screen_density, density + ""));
        mTxtScreenScaleDensity.setText(ResourceUtil.getString(R.string.current_device_screen_scale_density, scaleDensity + ""));
    }

    @OnClick({R.id.btn_dp_to_px, R.id.btn_sp_to_px, R.id.btn_px_to_dp, R.id.btn_px_to_sp})
    void onClick(View view){
        float sourceValue = 0;
        try {
            sourceValue = Float.valueOf(mETxtSourceValue.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtil.showToastWithShortDuration(R.string.please_input_legitimate_integer_or_decimal);
            return;
        }
        float convertedValue = 0f;
        String convertedResult = "";

        switch (view.getId()){
            case R.id.btn_dp_to_px:
                convertedValue = DensityUtil.dp2px(sourceValue);
                convertedResult = sourceValue + " dp = " + convertedValue + " px";
                break;

            case R.id.btn_sp_to_px:
                convertedValue = DensityUtil.sp2px(sourceValue);
                convertedResult = sourceValue + " sp = " + convertedValue + " px";
                break;

            case R.id.btn_px_to_dp:
                convertedValue = DensityUtil.px2dp(sourceValue);
                convertedResult = sourceValue + " px = " + convertedValue + " dp";
                break;

            case R.id.btn_px_to_sp:
                convertedValue = DensityUtil.px2sp(sourceValue);
                convertedResult = sourceValue + " px = " + convertedValue + " sp";
                break;
        }

        mTxtConvertedResult.setText(convertedResult);
    }

    @Override
    protected void loadData() {

    }
}
