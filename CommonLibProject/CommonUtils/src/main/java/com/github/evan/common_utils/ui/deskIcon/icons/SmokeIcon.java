package com.github.evan.common_utils.ui.deskIcon.icons;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;

/**
 * Created by Evan on 2017/12/24.
 */
public class SmokeIcon extends BaseDeskIcon {
    public static final int RESET = 0;
    public static final int LAUNCHING = 1;
    public static final int LAUNCHED = 3;


    private ImageView mRootView;
    private AnimationDrawable mAnimationDrawable;

    public SmokeIcon(Context context) {
        super(context);
    }

    @Override
    public void setStatus(int status) {
        if(status == RESET){
            hidden();
            if(mAnimationDrawable.isRunning()){
                mAnimationDrawable.stop();
            }
        }else if(status == LAUNCHING){
            show();
            mAnimationDrawable.start();
        }else if(status == LAUNCHED){
            if(mAnimationDrawable.isRunning()){
                mAnimationDrawable.stop();
            }
        }
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        DeskIconConfig config = new DeskIconConfig();
        mRootView = new ImageView(getContext());
        mAnimationDrawable = (AnimationDrawable) ResourceUtil.getDrawable(R.drawable.animation_rocket_smoke);
        mRootView.setImageDrawable(mAnimationDrawable);
        config.setGravity(Gravity.LEFT | Gravity.TOP);
        int width = UiUtil.measureWidth(mRootView);
        int height = UiUtil.measureHeight(mRootView);
        config.setInitX(windowWidth / 2 - width / 2);
        config.setInitY(windowHeight - height);
        config.setDeskIconView(mRootView);
        return config;
    }
}
