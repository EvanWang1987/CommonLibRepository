package com.github.evan.common_utils.ui.view.slideExitView;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.gesture.interceptor.TouchEventInterceptor;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;

/**
 * Created by Evan on 2017/12/19.
 */
public class SlideExitLayout extends ViewGroup {
    private View mContent;
    private View mDecorView;
    private SlideExitDirection mExitDirection = SlideExitDirection.LEFT_TO_RIGHT;
    private InterceptMode mInterceptMode = InterceptMode.HORIZONTAL_ONLY_LEFT_TO_RIGHT;
    private TouchEventInterceptor mInterceptor;
    private int mDownX, mDownY;
    @FloatRange(from = 0.2f, to = 0.8)
    private float mSlidingPercentWhenExit = 0.3f;
    private long mRollbackDuration = 300;
    private Interpolator mRollbackInterpolator;
    private ValueAnimator mRollbackAnimator = ValueAnimator.ofInt();
    private long mSlideExitDuration = 200;
    private Interpolator mSlideExitInterpolator;
    private ValueAnimator mSlideExitAnimator = ValueAnimator.ofInt();
    private OnSlideExitListener mExitListener;

    public SlideExitLayout(Context context) {
        super(context);
        init();
    }

    public SlideExitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideExitLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        if(mRollbackAnimator.isRunning()){
            mRollbackAnimator.cancel();
        }
        mRollbackAnimator.removeAllUpdateListeners();
        if(mSlideExitAnimator.isRunning()){
            mSlideExitAnimator.cancel();
        }
        mSlideExitAnimator.removeAllUpdateListeners();
        super.onDetachedFromWindow();
    }

    public void setSlideExitDirection(SlideExitDirection direction) {
        if (null == direction)
            return;
        mExitDirection = direction;
    }

    public void setSlidingPercentRelativeActivityWhenExit(@FloatRange(from = 0.2f, to = 0.8) float percent) {
        if (percent < 0.2f || percent > 0.8f)
            return;

        mSlidingPercentWhenExit = percent;
    }

    public void setRollbackDuration(long duration) {
        if (duration <= 0)
            return;
        mRollbackAnimator.setDuration(duration);
    }

    public void setRollbackInterplator(Interpolator interplator) {
        if (null == interplator)
            return;

        mRollbackInterpolator = interplator;
        mRollbackAnimator.setInterpolator(mRollbackInterpolator);
    }

    public SlideExitDirection getExitDirection() {
        return mExitDirection;
    }

    @FloatRange(from = 0.2f, to = 0.8)
    public float getSlidingPercentWhenExit() {
        return mSlidingPercentWhenExit;
    }

    public long getRollbackDuration() {
        return mRollbackDuration;
    }

    public Interpolator getRollbackInterpolator() {
        return mRollbackInterpolator;
    }

    public void setExitListener(OnSlideExitListener exitListener) {
        this.mExitListener = exitListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = mInterceptor.interceptTouchEvent(event, mInterceptMode, this, false);
        if(isIntercept){
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int offsetXFromDown = currentX - mDownX;
            int offsetYFromDown = currentY - mDownY;
            int scrollX = mDecorView.getScrollX();
            int scrollY = mDecorView.getScrollY();

            if (mExitDirection == SlideExitDirection.LEFT_TO_RIGHT) {
                int dstScrollX = scrollX + -(offsetXFromDown);
                if (currentX >= mDownX) {
                    mDecorView.scrollTo(dstScrollX, 0);
                } else {
                    if (dstScrollX >= 0) {
                        dstScrollX = 0;
                    }
                    mDecorView.scrollTo(dstScrollX, 0);
                }
            } else if (mExitDirection == SlideExitDirection.RIGHT_TO_LEFT) {
                int dstScrollX = scrollX + -(offsetXFromDown);
                if (currentX <= mDownX) {
                    mDecorView.scrollTo(dstScrollX, 0);
                } else {
                    if (dstScrollX <= 0) {
                        dstScrollX = 0;
                    }
                    mDecorView.scrollTo(dstScrollX, 0);
                }
            } else if (mExitDirection == SlideExitDirection.TOP_TO_BOTTOM) {
                int dstScrollY = scrollY + -(offsetYFromDown);
                if (currentY >= mDownY) {
                    mDecorView.scrollTo(0, dstScrollY);
                } else {
                    if (dstScrollY >= 0) {
                        dstScrollY = 0;
                    }
                    mDecorView.scrollTo(0, dstScrollY);
                }
            } else {
                //bottom to top
                int dstScrollY = scrollY + -(offsetYFromDown);
                if (currentY <= mDownY) {
                    mDecorView.scrollTo(0, dstScrollY);
                } else {
                    if (dstScrollY <= 0) {
                        dstScrollY = 0;
                    }
                    mDecorView.scrollTo(0, dstScrollY);
                }
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            int width = mDecorView.getWidth();
            int height = mDecorView.getHeight();
            int scrollX = mDecorView.getScrollX();
            int scrollY = mDecorView.getScrollY();
            if (mExitDirection == SlideExitDirection.LEFT_TO_RIGHT) {
                if (scrollX <= -(int) (width * mSlidingPercentWhenExit)) {
                    mSlideExitAnimator.setIntValues(scrollX, -width);
                    mSlideExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(value, 0);
                            if(value == -mDecorView.getWidth()){
                                mSlideExitAnimator.removeAllUpdateListeners();
                                if (null != mExitListener) {
                                    mExitListener.onSlideExit(mExitDirection, SlideExitLayout.this, (Activity) getContext());
                                }
                            }
                        }
                    });
                    mSlideExitAnimator.start();
                } else {
                    mRollbackAnimator.setIntValues(scrollX, 0);
                    mRollbackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(value, 0);
                            if (value == 0) {
                                mRollbackAnimator.removeAllUpdateListeners();
                            }
                        }
                    });
                    mRollbackAnimator.start();
                }
            } else if (mExitDirection == SlideExitDirection.RIGHT_TO_LEFT) {
                if (scrollX >= (width * mSlidingPercentWhenExit)) {
                    mSlideExitAnimator.setIntValues(scrollX, width);
                    mSlideExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(value, 0);
                            if(value == mDecorView.getWidth()){
                                mSlideExitAnimator.removeAllUpdateListeners();
                                if (null != mExitListener) {
                                    mExitListener.onSlideExit(mExitDirection, SlideExitLayout.this, (Activity) getContext());
                                }
                            }
                        }
                    });
                    mSlideExitAnimator.start();
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
                    mSlideExitAnimator.setIntValues(scrollY, -height);
                    mSlideExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(0, value);
                            if(value == -mDecorView.getHeight()){
                                mSlideExitAnimator.removeAllUpdateListeners();
                                if (null != mExitListener) {
                                    mExitListener.onSlideExit(mExitDirection, SlideExitLayout.this, (Activity) getContext());
                                }
                            }
                        }
                    });
                    mSlideExitAnimator.start();

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
                    mSlideExitAnimator.setIntValues(scrollY, height);
                    mSlideExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDecorView.scrollTo(0, value);
                            if(value == mDecorView.getHeight()){
                                mSlideExitAnimator.removeAllUpdateListeners();
                                if (null != mExitListener) {
                                    mExitListener.onSlideExit(mExitDirection, SlideExitLayout.this, (Activity) getContext());
                                }
                            }
                        }
                    });
                    mSlideExitAnimator.start();
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
            mDownY = 0;
        }


        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sourceWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sourceHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildWithMargins(mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);

        int contentWidth = mContent.getMeasuredWidth();
        int contentHeight = mContent.getMeasuredHeight();
        int dstWidth;
        int dstHeight;
        if (widthMode == MeasureSpec.EXACTLY) {
            dstWidth = sourceWidth;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            dstWidth = Math.min(sourceWidth, contentWidth);
        } else {
            dstWidth = sourceWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            dstHeight = sourceHeight;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            dstHeight = Math.min(sourceHeight, contentHeight);
        } else {
            dstHeight = contentHeight;
        }

        setMeasuredDimension(dstWidth, dstHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        MarginLayoutParams lps = (MarginLayoutParams) mContent.getLayoutParams();
        int leftMargin = lps.leftMargin;
        int rightMargin = lps.rightMargin;
        int topMargin = lps.topMargin;
        int bottomMargin = lps.bottomMargin;

        mContent.layout(l + paddingLeft + leftMargin, t + paddingTop + topMargin, r - paddingRight - rightMargin, b - paddingBottom - bottomMargin);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new IllegalArgumentException("SlideExitLayout can only host one child.");
        } else if (childCount == 0) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setText("Did you forget to put your own view into SlideExitLayout?");
            mContent = textView;
            return;
        }

        View childAt = getChildAt(0);
        mContent = childAt;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    private void init() {
        Activity activity = (Activity) getContext();
        mDecorView = activity.getWindow().getDecorView();
        mDecorView.setBackgroundResource(R.color.Alpha);
        mRollbackAnimator.setDuration(mRollbackDuration);
        mRollbackInterpolator = new DecelerateInterpolator();
        mRollbackAnimator.setInterpolator(mRollbackInterpolator);
        mSlideExitInterpolator = new AccelerateDecelerateInterpolator();
        mSlideExitAnimator.setDuration(mSlideExitDuration);
        mSlideExitAnimator.setInterpolator(mSlideExitInterpolator);
        InterceptMode interceptMode = null;
        switch (mExitDirection) {
            case LEFT_TO_RIGHT:
                interceptMode = InterceptMode.HORIZONTAL_ONLY_LEFT_TO_RIGHT;
                break;

            case RIGHT_TO_LEFT:
                interceptMode = InterceptMode.HORIZONTAL_ONLY_RIGHT_TO_LEFT;
                break;

            case TOP_TO_BOTTOM:
                interceptMode = InterceptMode.VERTICAL_ONLY_TOP_TO_BOTTOM;
                break;

            case BOTTOM_TO_TOP:
                interceptMode = InterceptMode.VERTICAL_ONLY_BOTTOM_TO_TOP;
                break;
        }
        mInterceptMode = interceptMode;
        mInterceptor = new TouchEventInterceptor(getContext());
    }
}
