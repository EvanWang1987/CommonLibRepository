package com.github.evan.common_utils_demo.ui.fragment.switchFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/10.
 */

public class SecurityFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_security, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_switch_fragment_next, R.id.card_switch_fragment_previews})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_switch_fragment_next:
                getActivityProvider().switchFragment(SnapFragment.class.getName());
                break;

            case R.id.card_switch_fragment_previews:
                getActivityProvider().switchFragment(DiscoveryFragment.class.getName());
                break;

        }
    }
}
