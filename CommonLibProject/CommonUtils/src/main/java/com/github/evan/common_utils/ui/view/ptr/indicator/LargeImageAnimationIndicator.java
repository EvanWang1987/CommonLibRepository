package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.ptr.PtrStatus;

/**
 * Created by Evan on 2017/11/30.
 */

public class LargeImageAnimationIndicator extends BaseIndicator {
    private static final int SUN_TOP_MARGIN = 50;
    private ImageView mIcBuildings, mIcSun;
    private ObjectAnimator mSunAnim;

    public LargeImageAnimationIndicator(Context context) {
        super(context);
    }

    public LargeImageAnimationIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LargeImageAnimationIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.indicator_large_image_animation, null);
        mIcBuildings = root.findViewById(R.id.ic_buildings_large_image_anim_indicator);
        mIcSun = root.findViewById(R.id.ic_sun_large_image_anim_indicator);
        mSunAnim = ObjectAnimator.ofFloat(mIcSun, "rotation", 0f, 360f);
        mSunAnim.setDuration(1500);
        mSunAnim.setRepeatMode(ObjectAnimator.RESTART);
        mSunAnim.setRepeatCount(ObjectAnimator.INFINITE);
        mSunAnim.setInterpolator(new LinearInterpolator());
        return root;
    }

    @Override
    public void onPtrStatusChange(PtrStatus status) {
        handleViewsWithPtrStatus(status);
    }

    @Override
    public void onPullDownOffsetChange(int offsetYFromDown, int offsetYFromLastMoved) {
        int indicatorHeight = getIndicatorView().getHeight();
        int sunHeight = mIcSun.getHeight();
        float translationY = mIcSun.getTranslationY();
        if(translationY == -(indicatorHeight - sunHeight - SUN_TOP_MARGIN)){
            return;
        }

        float rotation = mIcSun.getRotation();
        boolean isTop2BottomSlide = offsetYFromDown >= 0;
        float dstY = isTop2BottomSlide ? translationY - offsetYFromLastMoved : translationY + Math.abs(offsetYFromLastMoved);
        if(dstY <= -(indicatorHeight - sunHeight - SUN_TOP_MARGIN)){
            dstY = -(indicatorHeight - sunHeight - SUN_TOP_MARGIN);
        }
        mIcSun.setTranslationY(dstY);
        mIcSun.setRotation(rotation + offsetYFromLastMoved);
    }

    private void handleViewsWithPtrStatus(PtrStatus ptrStatus) {
        switch (ptrStatus) {
            case IDLE:
                mSunAnim.cancel();
                mIcSun.setTranslationY(mIcSun.getHeight());
                break;

            case START_PULL:
                break;

            case PULLING:
                break;

            case RELEASE_TO_REFRESH:
                int indicatorHeight = getIndicatorView().getHeight();
                int sunHeight = mIcSun.getHeight();
                mIcSun.setTranslationY(-(indicatorHeight - sunHeight - SUN_TOP_MARGIN));
                break;

            case REFRESHING:
                mSunAnim.start();
                break;

            case REFRESHED:
                break;

        }
    }
}
