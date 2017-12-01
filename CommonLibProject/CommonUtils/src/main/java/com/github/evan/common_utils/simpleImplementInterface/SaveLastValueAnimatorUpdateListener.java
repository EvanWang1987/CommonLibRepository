package com.github.evan.common_utils.simpleImplementInterface;

import android.animation.ValueAnimator;

/**
 * Created by Evan on 2017/12/1.
 */
public class SaveLastValueAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
    private Object mLastValue;

    @Override
    public final void onAnimationUpdate(ValueAnimator animation) {
        onUpdateAnimation(animation, mLastValue);
        mLastValue = animation.getAnimatedValue();
    }

    public void onUpdateAnimation(ValueAnimator animator, Object lastValue){

    }
}
