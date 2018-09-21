package com.github.evan.common_utils_demo.ui.activity.aboutAnimation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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

public class TweenAnimationActivity extends BaseLogCatActivity {
    private static final long ANIMATION_DURATION = 1000L;
    @BindView(R.id.ic_portrait_animation)
    ImageView mIcPortrait;


    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_tween_animation, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_tween_animation_alpha, R.id.card_object_animation_rotate, R.id.card_frame_animation_transate, R.id.card_frame_animation_scale})
    protected void onClick(View view){
        mIcPortrait.clearAnimation();
        switch (view.getId()){
            case R.id.card_tween_animation_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                alphaAnimation.setDuration(ANIMATION_DURATION);
                addLog("Create AlphaAnimation with 1 second duration. From 0.1 alpha to 1 alpha.");
                mIcPortrait.startAnimation(alphaAnimation);
                break;

            case R.id.card_object_animation_rotate:
                RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(ANIMATION_DURATION);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                addLog("Create RotateAnimation with 1 second duration. From 0 degrees to 360 degrees.");
                mIcPortrait.startAnimation(rotateAnimation);
                break;

            case R.id.card_frame_animation_transate:
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f, 0, 0, 0, 0);
                translateAnimation.setDuration(ANIMATION_DURATION);
                addLog("Create TranslateAnimation with 1 second duration. From view's left to its right.");
                mIcPortrait.startAnimation(translateAnimation);
                break;

            case R.id.card_frame_animation_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1f, 0.0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);
                addLog("Create ScaleAnimation with 1 second duration. From 0 pivot to 1 pivot.");
                mIcPortrait.startAnimation(scaleAnimation);
                break;
        }
    }
}
