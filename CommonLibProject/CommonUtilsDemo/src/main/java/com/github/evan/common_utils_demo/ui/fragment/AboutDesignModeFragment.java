package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutDesignMode.AboutFactoryActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutDesignMode.ObserverActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutDesignMode.SingletonActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/15.
 */

public class AboutDesignModeFragment extends BaseFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_design_mode, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_design_mode_singleton, R.id.card_design_mode_observer, R.id.card_design_mode_builder})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_design_mode_singleton:
                loadActivity(SingletonActivity.class);
                break;

            case R.id.card_design_mode_observer:
                loadActivity(ObserverActivity.class);
                break;

            case R.id.card_design_mode_builder:
                loadActivity(AboutFactoryActivity.class);
                break;
        }


    }
}
