package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.activity.BaseFragmentActivity;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.fragment.switchFragment.DiscoveryFragment;
import com.github.evan.common_utils_demo.ui.fragment.switchFragment.SecurityFragment;
import com.github.evan.common_utils_demo.ui.fragment.switchFragment.SnapFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Evan on 2018/9/10.
 */
public class SwitchFragmentActivity extends BaseFragmentActivity {
    @BindView(R.id.bottom_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_btn_discovery)
    RadioButton mBtnDiscovery;
    @BindView(R.id.radio_btn_security)
    RadioButton mBtnSecurity;
    @BindView(R.id.radio_btn_snap)
    RadioButton mBtnSnap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mRadioGroup.check(mBtnDiscovery.getId());
    }

    @Override
    public void switchFragment(String tagWhichBeShowing) {
        super.switchFragment(tagWhichBeShowing);
        if(tagWhichBeShowing.equals(DiscoveryFragment.class.getName())){
            mBtnDiscovery.setChecked(true);
        }else if(tagWhichBeShowing.equals(SecurityFragment.class.getName())){
            mBtnSecurity.setChecked(true);
        }else{
            mBtnSnap.setChecked(true);
        }
    }

    @OnCheckedChanged({R.id.radio_btn_discovery, R.id.radio_btn_security, R.id.radio_btn_snap})
    void onCheckedChange(CompoundButton button){
        if(button.isChecked()){
            switch (button.getId()){
                case R.id.radio_btn_discovery:
                    switchFragment(DiscoveryFragment.class.getName());
                    break;

                case R.id.radio_btn_security:
                    switchFragment(SecurityFragment.class.getName());
                    break;

                case R.id.radio_btn_snap:
                    switchFragment(SnapFragment.class.getName());
                    break;
            }
        }
    }

    @Override
    protected List<String> getFragmentName() {
        List<String> fragmentNames = new ArrayList<>();
        fragmentNames.add(DiscoveryFragment.class.getName());
        fragmentNames.add(SecurityFragment.class.getName());
        fragmentNames.add(SnapFragment.class.getName());
        return fragmentNames;
    }

    @Override
    protected String showWhichFragmentWhenInitialized() {
        return DiscoveryFragment.class.getName();
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_switch_container;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_switch_fragment;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
