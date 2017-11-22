package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/19.
 */
public class ScreenInformationFragment extends BaseFragment {
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
        float sourceValue = Float.valueOf(mETxtSourceValue.getText().toString());
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
