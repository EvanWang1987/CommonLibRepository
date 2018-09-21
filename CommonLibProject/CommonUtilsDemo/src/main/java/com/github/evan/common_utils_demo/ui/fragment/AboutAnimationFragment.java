package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutAnimation.ObjectAnimatorActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutAnimation.TweenAnimationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/21.
 */

public class AboutAnimationFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_animation, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_tween_animation, R.id.card_object_animation, R.id.card_frame_animation})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_tween_animation:
                loadActivity(TweenAnimationActivity.class);
                break;

            case R.id.card_object_animation:
                loadActivity(ObjectAnimatorActivity.class);
                break;

            case R.id.card_frame_animation:

                break;

        }
    }

}
