package com.github.evan.common_utils_demo.ui.activity.aboutAnimation;

import android.graphics.drawable.AnimationDrawable;
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
 * Created by Evan on 2018/9/23.
 */

public class FrameAnimationActivity extends BaseLogCatActivity {

    @BindView(R.id.ic_frame_animation)
    ImageView mIcFrameAnimation;

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_frame_animation, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void onDestroy() {
        AnimationDrawable animationDrawable = (AnimationDrawable) mIcFrameAnimation.getDrawable();
        animationDrawable.stop();
        super.onDestroy();
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_frame_animation})
    protected void onClick(View view){
        AnimationDrawable drawable = (AnimationDrawable) mIcFrameAnimation.getDrawable();
        drawable.start();
//        Drawable[] drawables = new Drawable[]{new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_carrier_0)), new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_carrier_1)), new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_carrier_2)), new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_carrier_3))};
//        LayerDrawable layerDrawable = new LayerDrawable(drawables);
//        mIcFrameAnimation.setImageDrawable(layerDrawable);
    }
}
