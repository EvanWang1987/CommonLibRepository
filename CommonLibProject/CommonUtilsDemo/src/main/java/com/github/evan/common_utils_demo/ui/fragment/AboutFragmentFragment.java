package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.activity.SinglePhotoActivity;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.SwitchFragmentActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutFragment.FragmentBackStackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/27.
 */
public class AboutFragmentFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_fragment, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_fragment_life, R.id.card_fragment_back_stack, R.id.card_switch_fragment})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_fragment_life:
                Bundle bundle = new Bundle();
                bundle.putInt(SinglePhotoActivity.PHOTO_RES_ID, R.mipmap.img_fragment_life);
                loadActivity(SinglePhotoActivity.class, bundle, false, -1);
                break;

            case R.id.card_fragment_back_stack:
                loadActivity(FragmentBackStackActivity.class);
                break;

            case R.id.card_switch_fragment:
                loadActivity(SwitchFragmentActivity.class);
                break;
        }
    }
}
