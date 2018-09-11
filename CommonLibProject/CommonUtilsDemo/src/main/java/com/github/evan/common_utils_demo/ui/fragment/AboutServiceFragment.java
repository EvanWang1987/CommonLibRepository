package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.activity.SinglePhotoActivity;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutService.BindServiceActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutService.ServiceLifeCycleActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutService.StartServiceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/11.
 */
public class AboutServiceFragment extends BaseFragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_service, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_service_life, R.id.card_service_life_cycle, R.id.card_service_start_mode, R.id.card_service_bind_mode})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_service_life:
                Bundle bundle = new Bundle();
                bundle.putInt(SinglePhotoActivity.PHOTO_RES_ID, R.mipmap.img_service_life_cycle);
                loadActivity(SinglePhotoActivity.class, bundle, false, -1);
                break;

            case R.id.card_service_life_cycle:
                loadActivity(ServiceLifeCycleActivity.class);
                break;

            case R.id.card_service_start_mode:
                loadActivity(StartServiceActivity.class);
                break;

            case R.id.card_service_bind_mode:
                loadActivity(BindServiceActivity.class);
                break;

        }
    }
}
