package com.github.evan.common_utils.ui.activity.slideExitActivity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/12/18.
 */
public abstract class SlideExitActivity extends BaseActivity {
    protected abstract void onSlideExit(Activity activity);

    private SlideExitDirection mExitDirection = SlideExitDirection.LEFT_TO_RIGHT;
    private int mDownX, mLastX, mDownY, mLastY;
    @FloatRange(from = 0.2f, to = 0.8)
    private float mSlidingPercentWhenExit = 0.3f;
    private int mTouchSlop;
    private View mDecorView;
    private long mRollbackDuration = 300;
    private Interpolator mRollbackInterpolator;
    private ValueAnimator mRollbackAnimator = ValueAnimator.ofInt();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfiguration.get(this.getApplication()).getScaledTouchSlop();
        mRollbackAnimator.setDuration(mRollbackDuration);
        mRollbackInterpolator = new DecelerateInterpolator();
        mRollbackAnimator.setInterpolator(mRollbackInterpolator);
        mDecorView = getWindow().getDecorView();
    }

    @Override
    protected void onDestroy() {
        mDecorView = null;
        mRollbackAnimator.removeAllUpdateListeners();
        super.onDestroy();
    }

    protected void setSlideExitDirection(SlideExitDirection direction) {
        if (null == direction)
            return;
        mExitDirection = direction;
    }

    protected void setSlidingPercentRelativeActivityWhenExit(@FloatRange(from = 0.2f, to = 0.8) float percent) {
        if (percent < 0.2f || percent > 0.8f)
            return;

        mSlidingPercentWhenExit = percent;
    }

    protected void setRollbackDuration(long duration) {
        if (duration <= 0)
            return;
        mRollbackAnimator.setDuration(duration);
    }

    protected void setRollbackInterplator(Interpolator interplator) {
        if (null == interplator)
            return;

        mRollbackInterpolator = interplator;
        mRollbackAnimator.setInterpolator(mRollbackInterpolator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mLastX = mDownX;
            mDownY = (int) event.getY();
            mLastY = mDownY;
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int offsetXFromLast = currentX - mLastX;
            int offsetXFromDown = currentX - mDownX;
            int offsetYFromLast = currentY - mLastY;
            int offsetYFromDown = currentY - mDownY;
            mLastX = currentX;
            mLastY = currentY;
            int scrollX = mDecorView.getScrollX();
            int scrollY = mDecorView.getScrollY();

            if (mExitDirection == SlideExitDirection.LEFT_TO_RIGHT) {
                if (Math.abs(offsetXFromDown) >= mTouchSlop) {
                    int dstScrollX = scrollX + -(offsetXFromLast);
                    if (currentX >= mDownX) {
                        mDecorView.scrollTo(dstScrollX, 0);
                    } else {
                        if (dstScrollX >= 0) {
                            dstScrollX = 0;
                        }
                        mDecorView.scrollTo(dstScrollX, 0);
                    }
                }
            } else if (mExitDirection == SlideExitDirection.RIGHT_TO_LEFT) {
                if (Math.abs(offsetXFromDown) >= mTouchSlop) {
                    if (currentX <= mDownX) {
                        int dstScrollX = scrollX + -(offsetXFromLast);
                        mDecorView.scrollTo(dstScrollX, 0);
                    } else {
                        int dstScrollX = scrollX + -(offsetXFromLast);
                        if (dstScrollX <= 0) {
                            dstScrollX = 0;
                        }
                        mDecorView.scrollTo(dstScrollX, 0);
                    }
                }
            } else if (mExitDirection == SlideExitDirection.TOP_TO_BOTTOM) {
                if (Math.abs(offsetYFromDown) >= mTouchSlop) {
                    int dstScrollY = scrollY + -(offsetYFromLast);
                    if (currentY >= mDownY) {
                        mDecorView.scrollTo(0, dstScrollY);
                    } else {
                        if (dstScrollY >= 0) {
                            dstScrollY = 0;
                        }
                        mDecorView.scrollTo(0, dstScrollY);
                    }
                }
            } else {
                //bottom to top
                if (Math.abs(offsetYFromDown) >= mTouchSlop) {
                    int dstScrollY = scrollY + -(offsetYFromLast);
                    if (currentY <= mDownY) {
                        mDecorView.scrollTo(0, dstScrollY);
                    } else {
                        if (dstScrollY <= 0) {
                            dstScrollY = 0;
                        }
                        mDecorView.scrollTo(0, dstScrollY);
                    }
                }
            }

        } else if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_UP) {
            int width = mDecorView.getWidth();
            int height = mDecorView.getHeight();
            int scrollX = mDecorView.getScrollX();
            int scrollY = mDecorView.getScrollY();
            if (mExitDirection == SlideExitDirection.LEFT_TO_RIGHT) {
                if (scrollX <= -(int) (width * mSlidingPercentWhenExit)) {
                    onSlideExit(this);
                } else {
                    mRollbackAnimator.setIntValues(scrollX, 0);
                    mRollbackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(value, 0);
                            if (value == 0) {
                                mRollbackAnimator.removeUpdateListener(this);
                            }
                        }
                    });
                    mRollbackAnimator.start();
                }
            } else if (mExitDirection == SlideExitDirection.RIGHT_TO_LEFT) {
                if (scrollX >= (width * mSlidingPercentWhenExit)) {
                    onSlideExit(this);
                } else {
                    mRollbackAnimator.setIntValues(scrollX, 0);
                    mRollbackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(value, 0);
                            if (value == 0) {
                                mRollbackAnimator.removeUpdateListener(this);
                            }
                        }
                    });
                    mRollbackAnimator.start();
                }
            } else if (mExitDirection == SlideExitDirection.TOP_TO_BOTTOM) {
                if (scrollY <= -(height * mSlidingPercentWhenExit)) {
                    onSlideExit(this);
                } else {
                    mRollbackAnimator.setIntValues(scrollY, 0);
                    mRollbackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(0, value);
                            if (value == 0) {
                                mRollbackAnimator.removeUpdateListener(this);
                            }
                        }
                    });
                    mRollbackAnimator.start();
                }
            } else {
                //bottom to top
                if (scrollY >= height * mSlidingPercentWhenExit) {
                    onSlideExit(this);
                } else {
                    mRollbackAnimator.setIntValues(scrollY, 0);
                    mRollbackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(0, value);
                            if (value == 0) {
                                mRollbackAnimator.removeUpdateListener(this);
                            }
                        }
                    });
                    mRollbackAnimator.start();
                }
            }
            mDownX = 0;
            mLastX = 0;
        }
        return super.onTouchEvent(event);
    }
}
