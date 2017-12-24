package com.github.evan.common_utils.ui.deskIcon.icons;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.simpleImplementInterface.SaveLastValueAnimatorUpdateListener;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.UiUtil;

/**
 * Created by Evan on 2017/12/23.
 */
public class AssIcon extends BaseDeskIcon implements View.OnTouchListener {
    public static final int RESET_ICON_TO_SCREEN_LEFT = 0;
    public static final int RESET_ICON_TO_SCREEN_RIGHT = 1;
    public static final int RESET_ICON_TO_SCREEN_LEFT_WITH_CENTER_VERTICAL = 2;
    public static final int RESET_ICON_TO_SCREEN_RIGHT_WITH_CENTER_VERTICAL = 3;
    public static final int DRAGGING = 4;
    public static final int IN_ROCKET = 5;
    public static final int LAUNCHED = 6;


    private ImageView mRootView;
    private ValueAnimator mResetAnim;
    private AnimationDrawable mRunBackLeftAnim, mRunBackRightAnim;
    private int mDownX;
    private int mDownY;
    private int mLastX;
    private int mLastY;

    public AssIcon(Context context) {
        super(context);
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        DeskIconConfig config = new DeskIconConfig();
        mRootView = new ImageView(getContext());
        mRootView.setImageResource(R.mipmap.ass_right);
        mRootView.setOnTouchListener(this);
        config.setDeskIconView(mRootView);
        config.setGravity(Gravity.TOP | Gravity.LEFT);
        int width = UiUtil.measureWidth(mRootView);
        int height = UiUtil.measureHeight(mRootView);
        config.setInitX(windowWidth - width);
        config.setInitY(windowHeight / 2 - height / 2);
        mResetAnim = ValueAnimator.ofInt();
        mResetAnim.setDuration(500);
        mResetAnim.setInterpolator(new AccelerateInterpolator());
        mRunBackLeftAnim = (AnimationDrawable) ResourceUtil.getDrawable(R.drawable.animation_ass_run_left);
        mRunBackRightAnim = (AnimationDrawable) ResourceUtil.getDrawable(R.drawable.animation_ass_run_right);
        DeskIconManager.getInstance().addIcon(this);
        return config;
    }

    @Override
    public void setStatus(int status) {
        switch (status) {
            case RESET_ICON_TO_SCREEN_LEFT:
                mRootView.setImageDrawable(mRunBackLeftAnim);
                int x = getX();
                mResetAnim.setIntValues(x, 0);
                mResetAnim.addUpdateListener(new SaveLastValueAnimatorUpdateListener() {
                    @Override
                    public void onUpdateAnimation(ValueAnimator animator, Object lastValue) {
                        int value = (int) animator.getAnimatedValue();
                        if (lastValue == null) {
                            return;
                        }
                        int dst = -((int) lastValue - value);
                        moveX(dst);
                        if (value == 0) {
                            mRunBackLeftAnim.stop();
                            mRootView.setImageResource(R.mipmap.ass_left);
                            mResetAnim.removeAllUpdateListeners();
                        }
                    }
                });

                AnimationDrawable runBackLeftAnim = (AnimationDrawable) mRootView.getDrawable();
                runBackLeftAnim.start();
                mResetAnim.start();

                break;

            case RESET_ICON_TO_SCREEN_RIGHT:
                mRootView.setImageDrawable(mRunBackRightAnim);

                int currentX = getX();
                mResetAnim.setIntValues(currentX, windowWidth - mRootView.getWidth());
                mResetAnim.addUpdateListener(new SaveLastValueAnimatorUpdateListener() {
                    @Override
                    public void onUpdateAnimation(ValueAnimator animator, Object lastValue) {
                        if (lastValue == null) {
                            return;
                        }
                        int value = (int) animator.getAnimatedValue();
                        moveX(value - (int) lastValue);
                        if (value == windowWidth - mRootView.getWidth()) {
                            mRunBackRightAnim.stop();
                            mRootView.setImageResource(R.mipmap.ass_right);
                            mResetAnim.removeAllUpdateListeners();
                        }
                    }
                });

                AnimationDrawable runBackRightDrawable = (AnimationDrawable) mRootView.getDrawable();
                runBackRightDrawable.start();
                mResetAnim.start();
                break;

            case RESET_ICON_TO_SCREEN_LEFT_WITH_CENTER_VERTICAL:
                hidden();
                mRootView.setImageResource(R.mipmap.ass_left);
                moveTo(0, windowHeight / 2 - mRootView.getHeight() / 2);
                break;

            case RESET_ICON_TO_SCREEN_RIGHT_WITH_CENTER_VERTICAL:
                hidden();
                mRootView.setImageResource(R.mipmap.ass_right);
                moveTo(windowWidth - mRootView.getWidth(), windowHeight / 2 - mRootView.getHeight() / 2);
                break;

            case DRAGGING:
                mRootView.setImageResource(R.mipmap.ass);
                break;

            case IN_ROCKET:
                mRootView.setVisibility(View.INVISIBLE);
                break;

            case LAUNCHED:
                show();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getRawX();
            mDownY = (int) event.getRawY();
            mLastX = mDownX;
            mLastY = mDownY;
            setStatus(DRAGGING);
            DeskIconManager.getInstance().notifyLaunchBaseAndRocketShow();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getRawX();
            int currentY = (int) event.getRawY();
            int offsetX = currentX - mLastX;
            int offsetY = currentY - mLastY;
            mLastX = currentX;
            mLastY = currentY;

            WindowManager.LayoutParams params = getLayoutParams();

            int x = params.x;
            int y = params.y;
            int dstX = x + offsetX;
            int dstY = y + offsetY;


            if (dstX <= 0) {
                offsetX = 0;
            }

            if (dstX >= windowWidth - mRootView.getWidth()) {
                offsetX = 0;
            }

            if (dstY <= 0) {
                offsetY = 0;
            }

            if (dstY >= windowHeight - mRootView.getHeight()) {
                offsetY = 0;
            }

            move(offsetX, offsetY);
            if (DeskIconManager.getInstance().isAboveLaunchBase(getX(), getY())) {
                DeskIconManager.getInstance().notifyLaunchBaseAndRocketInvoke();
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            if (DeskIconManager.getInstance().isAboveLaunchBase(getX(), getY())) {
                int x = getLayoutParams().x;
                boolean isAtWindowLeft = x <= 0;
                setStatus(isAtWindowLeft ? RESET_ICON_TO_SCREEN_LEFT_WITH_CENTER_VERTICAL : RESET_ICON_TO_SCREEN_RIGHT_WITH_CENTER_VERTICAL);
                DeskIconManager.getInstance().launchRocket();
            } else {
                int x = getLayoutParams().x;
                boolean isAtWindowLeft = x <= windowWidth / 2;
                setStatus(isAtWindowLeft ? RESET_ICON_TO_SCREEN_LEFT : RESET_ICON_TO_SCREEN_RIGHT);
                DeskIconManager.getInstance().assRested();
            }
        }
        return true;
    }
}
