package com.github.evan.common_utils.ui.view.ptr;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.simpleImplementInterface.SaveLastValueAnimatorUpdateListener;
import com.github.evan.common_utils.ui.view.ptr.indicator.IIndicator;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.Logger;


/**
 * Created by Evan on 2017/11/29.
 */
public class PtrFrameLayout extends ViewGroup {
    private PtrStatus mPtrStatus = PtrStatus.IDLE;
    private IIndicator mIndicator;
    private View mContent;
    private int mDownX, mDownY, mLastMovedY;
    private int mTouchSlop;
    private ValueAnimator mSpringBackAnim = ValueAnimator.ofInt();
    private PullToRefreshSwitcher mRefreshableSwitcher;
    private OnRefreshListener mRefreshListener;


    public PtrFrameLayout(Context context) {
        super(context);
        init();
    }

    public PtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PtrFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mSpringBackAnim.setDuration(500);
    }


    public void autoRefresh(boolean isWithAnimation) {
        if (isWithAnimation) {
            final ValueAnimator animator = ValueAnimator.ofInt(mIndicator.getIndicatorView().getHeight(), 0);
            animator.setDuration(800);
            animator.addUpdateListener(new SaveLastValueAnimatorUpdateListener() {
                @Override
                public void onUpdateAnimation(ValueAnimator animator, Object lastValue) {
                    int indicatorHeight = mIndicator.getIndicatorView().getHeight();
                    int currentValue = (int) animator.getAnimatedValue();
                    int lastDstValue = null == lastValue ? indicatorHeight + 1 : (int) lastValue;

                    if (currentValue < indicatorHeight && currentValue > 0 && (mPtrStatus == PtrStatus.START_PULL || mPtrStatus == PtrStatus.IDLE)) {
                        mPtrStatus = PtrStatus.PULLING;
                        mIndicator.setStatus(mPtrStatus);
                    }

                    if (currentValue == 0 && mPtrStatus == PtrStatus.PULLING) {
                        mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
                        mIndicator.setStatus(mPtrStatus);
                    }
                    mIndicator.setOffsetY(indicatorHeight - currentValue, currentValue - lastDstValue);
                    PtrFrameLayout.this.scrollTo(0, currentValue);

                    if (currentValue == 0 && mPtrStatus == PtrStatus.RELEASE_TO_REFRESH) {
                        mPtrStatus = PtrStatus.REFRESHING;
                        mIndicator.setStatus(mPtrStatus);
                        animator.removeUpdateListener(this);
                    }
                }
            });
            mPtrStatus = PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            animator.start();
        } else {
            mPtrStatus = PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.PULLING;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.REFRESHING;
            mIndicator.setStatus(mPtrStatus);
            scrollTo(0, 0);
        }
    }

    public void refreshComplete(boolean withAnimation) {
        mPtrStatus = PtrStatus.REFRESHED;
        mIndicator.setStatus(mPtrStatus);
        if (withAnimation) {
            mSpringBackAnim.setIntValues(getScrollY(), mIndicator.getIndicatorView().getHeight());
            mSpringBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    PtrFrameLayout.this.scrollTo(0, value);
                    if (value == mIndicator.getIndicatorView().getHeight()) {
                        mSpringBackAnim.removeUpdateListener(this);
                    }
                }
            });
            mSpringBackAnim.start();
        } else {
            scrollTo(0, mIndicator.getIndicatorView().getHeight());
        }
        mPtrStatus = PtrStatus.IDLE;
        mIndicator.setStatus(mPtrStatus);
    }

    public void setRefreshableSwitcher(PullToRefreshSwitcher refreshableSwitcher) {
        this.mRefreshableSwitcher = refreshableSwitcher;
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = false;
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int offsetX = Math.abs(currentX - mDownX);
            int offsetY = Math.abs(currentY - mDownY);
            boolean isVerticalSlide = offsetY > offsetX;
            boolean isArriveMinVerticalSlideSlop = offsetY >= mTouchSlop;
            boolean isTopToBottomSlide = currentY > mDownY;
            if (isVerticalSlide && isArriveMinVerticalSlideSlop && isTopToBottomSlide && mRefreshableSwitcher.checkCanPullToRefresh()) {
                isIntercept = true;
                if (mSpringBackAnim.isRunning()) {
                    mSpringBackAnim.cancel();
                }
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastMovedY = mDownY;
            boolean isRefreshing = mPtrStatus == PtrStatus.REFRESHING;
            mPtrStatus = isRefreshing ? PtrStatus.REFRESHING : PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            if (!isRefreshing) {
                callBackRefreshListener();
            }
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentY = (int) event.getY();
            int offsetYSinceDown = currentY - mDownY;
            int offsetYSinceLastMoved = currentY - mLastMovedY;
            mLastMovedY = currentY;
            int scrollY = getScrollY();

            if (mPtrStatus == PtrStatus.START_PULL) {
                if (offsetYSinceLastMoved <= 0) {
                    //禁止向上滑动
                    return true;
                } else {
                    mPtrStatus = PtrStatus.PULLING;
                    mIndicator.setStatus(mPtrStatus);
                }
            } else if (mPtrStatus == PtrStatus.IDLE) {
                if (offsetYSinceLastMoved <= 0) {
                    //禁止向上滑动
                    return true;
                } else {
                    mPtrStatus = PtrStatus.PULLING;
                    mIndicator.setStatus(mPtrStatus);
                }
            } else if (mPtrStatus == PtrStatus.PULLING) {
                if (scrollY >= mIndicator.getIndicatorView().getHeight() && offsetYSinceLastMoved < 0) {
                    //indicator已经归位 && 向上滑动
                    return true;
                }

                if (scrollY <= 0) {
                    //indicator已经被拉下来了
                    mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
                    mIndicator.setStatus(mPtrStatus);
                }

            } else if (mPtrStatus == PtrStatus.RELEASE_TO_REFRESH) {
                if (scrollY > 0 && scrollY <= mIndicator.getIndicatorView().getHeight()) {
                    mPtrStatus = PtrStatus.PULLING;
                    mIndicator.setStatus(mPtrStatus);
                }
            } else if (mPtrStatus == PtrStatus.REFRESHING) {
                if (scrollY >= 0) {
                    return true;
                }
            } else {
                //refreshed and other unknown status
            }

            mIndicator.setOffsetY(offsetYSinceDown, offsetYSinceLastMoved);
            scrollBy(0, -offsetYSinceLastMoved);
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            int scrollY = getScrollY();
            if (mPtrStatus == PtrStatus.REFRESHING || mPtrStatus == PtrStatus.PULLING || mPtrStatus == PtrStatus.RELEASE_TO_REFRESH) {
                if (mPtrStatus == PtrStatus.REFRESHING) {
                    mSpringBackAnim.setIntValues(scrollY, 0);
                    mSpringBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            PtrFrameLayout.this.scrollTo(0, value);
                            if (value == 0) {
                                mSpringBackAnim.removeUpdateListener(this);
                            }
                        }
                    });
                    mSpringBackAnim.start();
                    return true;
                }

                final boolean isPulling = mPtrStatus == PtrStatus.PULLING;
                mPtrStatus = isPulling ? PtrStatus.IDLE : PtrStatus.REFRESHING;
                mSpringBackAnim.setIntValues(scrollY, isPulling ? mIndicator.getIndicatorView().getHeight() : 0);
                mSpringBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        PtrFrameLayout.this.scrollTo(0, value);
                        if (value == (isPulling ? mIndicator.getIndicatorView().getHeight() : 0)) {
                            mSpringBackAnim.removeUpdateListener(this);
                        }
                    }
                });
                mSpringBackAnim.start();
                mIndicator.setStatus(mPtrStatus);
                if (!isPulling) {
                    callBackRefreshListener();
                }
            }

        }
        return true;
    }

    private void callBackRefreshListener() {
        if (null != mRefreshListener) {
            if (mPtrStatus == PtrStatus.START_PULL) {
                mRefreshListener.onStartPulling();
            }

            if (mPtrStatus == PtrStatus.REFRESHING) {
                mRefreshListener.onRefresh();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 2) {
            throw new IllegalArgumentException("PtrFrameLayout can hold only two children! ");
        } else if (childCount == 2) {
            View firstChild = getChildAt(0);
            View secondChild = getChildAt(1);

            if (firstChild instanceof IIndicator && secondChild instanceof IIndicator) {
                throw new IllegalArgumentException("PtrFrameLayout can hold only one Indicator! ");
            }

            if (firstChild instanceof IIndicator) {
                mIndicator = (IIndicator) firstChild;
                mContent = secondChild;
            } else {
                mIndicator = (IIndicator) secondChild;
                mContent = firstChild;
            }
        } else if (childCount == 1) {
            View firstChild = getChildAt(0);
            if (firstChild instanceof IIndicator) {
                mIndicator = (IIndicator) firstChild;
                TextView textView = new TextView(getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setGravity(Gravity.CENTER);
                textView.setText("Did you put your own layout in PtrFrameLayout? ");
                this.addView(textView);
                mContent = textView;
            } else {
                throw new IllegalArgumentException("PtrFrameLayout must contains a Indicator! ");
            }
        } else {
            throw new IllegalArgumentException("There is no children in PtrFrameLayout！");
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View indicator = mIndicator.getIndicatorView();
        View content = this.mContent;
        indicator.layout(l, t, r, indicator.getMeasuredHeight());
        MarginLayoutParams lps = (MarginLayoutParams) content.getLayoutParams();
        content.layout(l + lps.leftMargin, t + indicator.getMeasuredHeight() + lps.topMargin, r + lps.rightMargin, b - lps.bottomMargin);
        if (mPtrStatus == PtrStatus.IDLE) {
            scrollTo(0, mIndicator.getIndicatorView().getMeasuredHeight());
            mIndicator.setStatus(mPtrStatus);
        }
    }

    @Override
    protected void onMeasure(int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(parentWidthMeasureSpec);
        int heightMode = MeasureSpec.getMode(parentHeightMeasureSpec);
        int sourceWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        int sourceHeight = MeasureSpec.getSize(parentHeightMeasureSpec);
        final View indicator = mIndicator.getIndicatorView();
        final View content = mContent;

        int screenHeightSpec = MeasureSpec.makeMeasureSpec(DensityUtil.getAppScreenHeightOfPx(), MeasureSpec.AT_MOST);
        measureChild(indicator, parentWidthMeasureSpec, screenHeightSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            measureChild(content, parentWidthMeasureSpec, parentHeightMeasureSpec);
            setMeasuredDimension(sourceWidth, sourceHeight + indicator.getMeasuredHeight());
            return;
        }

        int dstWidth;
        int dstHeight;
        if (widthMode == MeasureSpec.AT_MOST) {
            measureChild(content, parentWidthMeasureSpec, parentHeightMeasureSpec);
            dstWidth = Math.min(sourceWidth, content.getMeasuredWidth());
        } else {
            dstWidth = sourceWidth;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            measureChild(content, parentWidthMeasureSpec, parentHeightMeasureSpec);
            dstHeight = Math.min(sourceHeight, content.getMeasuredHeight());
        } else {
            dstHeight = sourceHeight;
        }
        setMeasuredDimension(dstWidth, dstHeight + indicator.getMeasuredHeight());
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
        return new MarginLayoutParams(p.width, p.height);
    }
}
