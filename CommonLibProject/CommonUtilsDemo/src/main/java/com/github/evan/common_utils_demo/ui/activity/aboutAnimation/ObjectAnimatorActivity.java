package com.github.evan.common_utils_demo.ui.activity.aboutAnimation;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/21.
 */

public class ObjectAnimatorActivity extends BaseLogCatActivity {
    private static final long DURATION = 500L;

    @BindView(R.id.ic_portrait_animation)
    ImageView mIcPortrait;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_object_animator, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_object_animation_alpha, R.id.card_object_animation_rotate, R.id.card_object_animation_transate, R.id.card_object_animation_scale})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_object_animation_alpha:
                ObjectAnimator alpha = ObjectAnimator.ofFloat(mIcPortrait, "alpha", 0.1f, 1f);
                alpha.setDuration(DURATION);
                alpha.start();
                break;

            case R.id.card_object_animation_rotate:
                ObjectAnimator rotate = ObjectAnimator.ofFloat(mIcPortrait, "rotation", 0f, 360f);
                rotate.setDuration(DURATION);
                rotate.start();
                break;

            case R.id.card_object_animation_transate:
                ObjectAnimator translationX = ObjectAnimator.ofFloat(mIcPortrait, "translationX", 0f, 100f);
                translationX.setDuration(DURATION);
                translationX.start();
                break;

            case R.id.card_object_animation_scale:
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(mIcPortrait, "scaleY", 0f, 1f);
                scaleY.setDuration(DURATION);
                scaleY.start();
                break;


        }
    }
}
