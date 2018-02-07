package com.github.evan.common_utils.ui.view.pullable_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.IIndicator;
import com.github.evan.common_utils.utils.DensityUtil;

/**
 * Created by Evan on 2018/2/5.
 */
public class PullLayout extends ViewGroup implements IPullable {
    private PullChecker mPullChecker;
    private IIndicator mFirstIndicator, mSecondIndicator;
    private View mContentView;
    private PullStatus mPullStatus = PullStatus.IDLE;
    private PullDirection mPullDirection = PullDirection.TOP_TO_BOTTOM;
    private IndicatorDisplayMode mIndicatorDisplayMode = IndicatorDisplayMode.SCROLL_WITH_CONTENT;
    private int mDownX, mDownY, mLastX, mLastY, mPreviewX, mPreviewY;
    private int mTouchSlop;
    private
    @FloatRange(from = 0.2f, to = 1.2f)
    float mInvokeDemarcationPercent = 1f;
    private OverScroller mScroller = new OverScroller(getContext());
    private boolean mCanScrollOverstepIndicator = true;
    private boolean mIsInvokingFromTop = false;
    private boolean mIsInvokingFromBottom = false;
    private boolean mIsInvokingFromLeft = false;
    private boolean mIsInvokingFromRight = false;

    public PullLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        mTouchSlop = ViewConfiguration.get(getContext().getApplicationContext()).getScaledTouchSlop();
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PullLayout);
            int anInt = typedArray.getInt(R.styleable.PullLayout_pull_direction, PullDirection.TOP_TO_BOTTOM.value);
            mPullDirection = PullDirection.valueOf(anInt);
            int anotherInt = typedArray.getInt(R.styleable.PullLayout_indicator_display_mode, IndicatorDisplayMode.SCROLL_WITH_CONTENT.value);
            mIndicatorDisplayMode = IndicatorDisplayMode.valueOf(anotherInt);
            typedArray.recycle();
        }
    }

    public void invokeComplete() {
        if (mPullStatus == PullStatus.INVOKING) {
            int scrollY = getScrollY();
            int scrollX = getScrollX();
            int startX = 0;
            int startY = 0;
            int dx = 0;
            int dy = 0;

            switch (mPullDirection) {
                case TOP_TO_BOTTOM:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }
                    startY = scrollY;
                    dy = Math.abs(scrollY);
                    mScroller.startScroll(0, startY, 0, dy, 800);
                    postInvalidate();
                    break;

                case BOTTOM_TO_TOP:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }

                    startY = scrollY;
                    dy = -scrollY;
                    mScroller.startScroll(0, startY, 0, dy, 800);
                    postInvalidate();
                    break;

                case BOTH_TOP_BOTTOM:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }

                    if (mIsInvokingFromTop) {
                        startY = scrollY;
                        dy = Math.abs(scrollY);
                        mScroller.startScroll(0, startY, 0, dy, 800);
                        postInvalidate();
                    } else if (mIsInvokingFromBottom) {
                        startY = scrollY;
                        dy = -scrollY;
                        mScroller.startScroll(0, startY, 0, dy, 800);
                        postInvalidate();
                    }

                    mIsInvokingFromTop = false;
                    mIsInvokingFromBottom = false;
                    break;

                case LEFT_TO_RIGHT:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }

                    startX = scrollX;
                    dx = Math.abs(scrollX);
                    mScroller.startScroll(startX, 0, dx, 0, 800);
                    postInvalidate();

                    break;

                case RIGHT_TO_LEFT:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }

                    startX = scrollX;
                    dx = -scrollX;
                    mScroller.startScroll(startX, 0, dx, 0, 800);
                    postInvalidate();

                    break;

                case BOTH_LEFT_RIGHT:
                    if (mPullStatus == PullStatus.IDLE || mPullStatus == PullStatus.START_PULL) {
                        break;
                    }

                    if (mIsInvokingFromLeft) {
                        startX = scrollX;
                        dx = Math.abs(scrollX);
                        mScroller.startScroll(startX, 0, dx, 0, 800);
                        postInvalidate();
                    } else if (mIsInvokingFromRight) {
                        startX = scrollX;
                        dx = -scrollX;
                        mScroller.startScroll(startX, 0, dx, 0, 800);
                        postInvalidate();
                    }

                    mIsInvokingFromLeft = false;
                    mIsInvokingFromRight = false;
                    break;
            }
            mPullStatus = PullStatus.IDLE;
            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
            if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM || mPullDirection == PullDirection.BOTH_LEFT_RIGHT) {
                mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
            }
        }
    }

    public PullDirection getPullDirection() {
        return mPullDirection;
    }

    public void setPullDirection(PullDirection pullDirection) {
        this.mPullDirection = pullDirection;
    }

    public PullChecker getPullChecker() {
        return mPullChecker;
    }

    public void setPullChecker(PullChecker pullChecker) {
        this.mPullChecker = pullChecker;
    }

    public boolean canScrollOverstepIndicator() {
        return mCanScrollOverstepIndicator;
    }

    public void setCanScrollOverstepIndicator(boolean canScrollOverstepIndicator) {
        this.mCanScrollOverstepIndicator = canScrollOverstepIndicator;
    }

    @Override
    public void setIndicatorDisplayMode(IndicatorDisplayMode mode) {
        if (null == mode)
            return;

        mIndicatorDisplayMode = mode;
        postInvalidate();
    }

    @Override
    public IndicatorDisplayMode getIndicatorDisplayMode() {
        return mIndicatorDisplayMode;
    }

    @Override
    public PullStatus getPullStatus() {
        return mPullStatus;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastX = mDownX;
            mLastY = mDownY;
            mPreviewX = mDownX;
            mPreviewY = mDownY;
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int disX = Math.abs(currentX - mDownX), disY = Math.abs(currentY - mDownY);
            boolean isTop2BottomSlide = currentY > mLastY;
            boolean isBottom2TopSlide = currentY <= mLastY;
            boolean isLeft2RightSlide = currentX > mLastX;
            boolean isRight2LeftSlide = currentX <= mLastX;

            if (null != mPullChecker && mPullChecker.checkCanPull(isTop2BottomSlide, isBottom2TopSlide, isLeft2RightSlide, isRight2LeftSlide)) {
                if (mPullDirection == PullDirection.TOP_TO_BOTTOM || mPullDirection == PullDirection.BOTTOM_TO_TOP || mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                    if (disY >= mTouchSlop) {
                        if (isTop2BottomSlide) {
                            mDownX = currentX;
                            mDownY = currentY - 1;
                            mLastX = currentX;
                            mLastY = currentY;
                        } else if (isBottom2TopSlide) {
                            mDownX = currentX;
                            mDownY = currentY + 1;
                            mLastX = currentX;
                            mLastY = currentY;
                        }
                        if (mPullStatus != PullStatus.INVOKING) {
                            mPullStatus = PullStatus.START_PULL;
                            mFirstIndicator.onPullStatusChange(PullStatus.START_PULL);
                            if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                                mSecondIndicator.onPullStatusChange(PullStatus.START_PULL);
                            }
                        }
                        return true;
                    }
                } else if (mPullDirection == PullDirection.LEFT_TO_RIGHT || mPullDirection == PullDirection.RIGHT_TO_LEFT || mPullDirection == PullDirection.BOTH_LEFT_RIGHT) {
                    if (disX >= mTouchSlop) {
                        if (isLeft2RightSlide) {
                            mDownX = currentX - 1;
                            mDownY = currentY;
                            mLastX = currentX;
                            mLastY = currentY;
                        } else if (isRight2LeftSlide) {
                            mDownX = currentX + 1;
                            mDownY = currentY;
                            mLastX = currentX;
                            mLastY = currentY;
                        }
                        if (mPullStatus != PullStatus.INVOKING) {
                            mPullStatus = PullStatus.START_PULL;
                            mFirstIndicator.onPullStatusChange(PullStatus.START_PULL);
                            if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                                mSecondIndicator.onPullStatusChange(PullStatus.START_PULL);
                            }
                        }
                        return true;
                    }
                }
            }
            mPreviewX = mLastX;
            mPreviewY = mLastY;
            mLastX = currentX;
            mLastY = currentY;
        } else if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_UP) {
            mDownX = 0;
            mDownY = 0;
            mLastX = 0;
            mLastY = 0;
            mPreviewX = 0;
            mPreviewY = 0;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastX = mDownX;
            mLastY = mDownY;
            mPreviewX = mDownX;
            mPreviewY = mDownY;
            if (mPullStatus == PullStatus.IDLE) {
                mPullStatus = PullStatus.START_PULL;
                mFirstIndicator.onPullStatusChange(PullStatus.START_PULL);
                if (mPullDirection == PullDirection.BOTH_LEFT_RIGHT || mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                    mSecondIndicator.onPullStatusChange(PullStatus.START_PULL);
                }
            }
            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int disX = currentX - mDownX, disY = currentY - mDownY;
            int offsetX = currentX - mLastX, offsetY = currentY - mLastY;
            int scrollX = getScrollX(), scrollY = getScrollY();
            int dstScrollX = scrollX + (-offsetX), dstScrollY = scrollY + (-offsetY);
            boolean isTop2BottomSlide = currentY > mLastY;
            boolean isBottom2TopSlide = currentY <= mLastY;
            boolean isLeft2RightSlide = currentX > mLastX;
            boolean isRight2LeftSlide = currentX <= mLastX;

            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
            switch (mPullDirection) {
                case TOP_TO_BOTTOM:
                    if (isTop2BottomSlide) {
                        if (!mCanScrollOverstepIndicator && scrollY <= -mFirstIndicator.getIndicatorView().getHeight()) {
                            break;
                        }

                        if (dstScrollY < 0 && dstScrollY > -mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                mPullStatus = PullStatus.TOP_PULLING;
                                mFirstIndicator.onPullStatusChange(PullStatus.TOP_PULLING);
                            }
                        } else if (dstScrollY < 0 && dstScrollY <= -mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.TOP_PULLING) {
                                mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                            }
                        }

                        mFirstIndicator.onDistanceChange(0, offsetY);
                        scrollBy(0, -offsetY);
                    } else if (isBottom2TopSlide) {
                        if (scrollY >= 0) {
                            break;
                        } else {
                            if (dstScrollY >= 0) {
                                dstScrollY = 0;
                            }

                            if (dstScrollY < 0 && dstScrollY > -(mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent)) {
                                if (mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.TOP_PULLING;
                                    mFirstIndicator.onPullStatusChange(PullStatus.TOP_PULLING);
                                }
                            }

                            mFirstIndicator.onDistanceChange(0, offsetY);
                            scrollTo(0, dstScrollY);
                        }
                    }
                    break;

                case BOTTOM_TO_TOP:
                    if (isBottom2TopSlide) {
                        if (!mCanScrollOverstepIndicator && scrollY >= mFirstIndicator.getIndicatorView().getHeight()) {
                            break;
                        }

                        if (dstScrollY > 0 && dstScrollY < mFirstIndicator.getIndicatorView().getHeight()) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                mPullStatus = PullStatus.BOTTOM_PULLING;
                                mFirstIndicator.onPullStatusChange(PullStatus.BOTTOM_PULLING);
                            }
                        } else if (dstScrollY > 0 && dstScrollY >= mFirstIndicator.getIndicatorView().getHeight()) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.BOTTOM_PULLING) {
                                mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                            }
                        }

                        mFirstIndicator.onDistanceChange(0, offsetY);
                        scrollBy(0, -offsetY);
                    } else if (isTop2BottomSlide) {
                        if (scrollY <= 0) {
                            break;
                        } else {
                            if (dstScrollY <= 0) {
                                dstScrollY = 0;
                            }

                            if (dstScrollY > 0 && dstScrollY < mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.BOTTOM_PULLING;
                                    mFirstIndicator.onPullStatusChange(PullStatus.BOTTOM_PULLING);
                                }
                            }

                            mFirstIndicator.onDistanceChange(0, offsetY);
                            scrollTo(0, dstScrollY);
                        }
                    }
                    break;

                case BOTH_TOP_BOTTOM:
                    if (disY >= 0) {
                        if (isTop2BottomSlide) {
                            if (mPullStatus == PullStatus.INVOKING && mIsInvokingFromBottom) {
                                if (dstScrollY <= 0) {
                                    scrollTo(0, 0);
                                } else {
                                    scrollBy(0, -offsetY);
                                }
                                break;
                            }

                            if (!mCanScrollOverstepIndicator && scrollY <= -mFirstIndicator.getIndicatorView().getHeight()) {
                                break;
                            }

                            if (dstScrollY < 0 && dstScrollY > -mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.TOP_PULLING;
                                    mFirstIndicator.onPullStatusChange(PullStatus.TOP_PULLING);
                                }
                            } else if (dstScrollY < 0 && dstScrollY <= -mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.TOP_PULLING) {
                                    mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                    mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                                }
                            }

                            mFirstIndicator.onDistanceChange(0, offsetY);
                            scrollBy(0, -offsetY);
                        } else if (isBottom2TopSlide) {
                            if (scrollY >= 0) {
                                break;
                            } else {
                                if (dstScrollY >= 0) {
                                    dstScrollY = 0;
                                }

                                if (dstScrollY < 0 && dstScrollY > -(mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent)) {
                                    if (mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                        mPullStatus = PullStatus.TOP_PULLING;
                                        mFirstIndicator.onPullStatusChange(PullStatus.TOP_PULLING);
                                    }
                                }

                                mFirstIndicator.onDistanceChange(0, offsetY);
                                scrollTo(0, dstScrollY);
                            }
                        }
                    } else {
                        if (isBottom2TopSlide) {
                            if (mPullStatus == PullStatus.INVOKING && mIsInvokingFromTop) {
                                if (dstScrollY >= 0) {
                                    dstScrollY = 0;
                                    scrollTo(0, dstScrollY);
                                } else {
                                    scrollBy(0, -offsetY);
                                }
                                break;
                            }

                            if (!mCanScrollOverstepIndicator && scrollY >= mSecondIndicator.getIndicatorView().getHeight()) {
                                break;
                            }

                            if (dstScrollY > 0 && dstScrollY < mSecondIndicator.getIndicatorView().getHeight()) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.BOTTOM_PULLING;
                                    mSecondIndicator.onPullStatusChange(PullStatus.BOTTOM_PULLING);
                                }
                            } else if (dstScrollY > 0 && dstScrollY >= mSecondIndicator.getIndicatorView().getHeight()) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.BOTTOM_PULLING) {
                                    mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                    mSecondIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                                }
                            }

                            mSecondIndicator.onDistanceChange(0, offsetY);
                            scrollBy(0, -offsetY);
                        } else if (isTop2BottomSlide) {
                            if (scrollY <= 0) {
                                break;
                            } else {
                                if (dstScrollY <= 0) {
                                    dstScrollY = 0;
                                }

                                if (dstScrollY > 0 && dstScrollY < mSecondIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                                    if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                        mPullStatus = PullStatus.BOTTOM_PULLING;
                                        mSecondIndicator.onPullStatusChange(PullStatus.BOTTOM_PULLING);
                                    }
                                }

                                mSecondIndicator.onDistanceChange(0, offsetY);
                                scrollTo(0, dstScrollY);
                            }
                        }
                    }
                    break;

                case LEFT_TO_RIGHT:
                    if (isLeft2RightSlide) {
                        if (!mCanScrollOverstepIndicator && scrollX <= -mFirstIndicator.getIndicatorView().getWidth()) {
                            break;
                        }

                        if (scrollX < 0 && scrollX > -mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                mPullStatus = PullStatus.LEFT_PULLING;
                                mFirstIndicator.onPullStatusChange(PullStatus.LEFT_PULLING);
                            }
                        } else if (scrollX < 0 && scrollX <= -mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.LEFT_PULLING) {
                                mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                            }
                        }

                        mFirstIndicator.onDistanceChange(offsetX, 0);
                        scrollBy(-offsetX, 0);
                    } else {
                        if (scrollX >= 0) {
                            break;
                        } else {
                            if (dstScrollX >= 0) {
                                dstScrollX = 0;
                            }
                            mFirstIndicator.onDistanceChange(offsetX, 0);
                            scrollTo(dstScrollX, 0);
                        }
                    }
                    break;

                case RIGHT_TO_LEFT:
                    if (isRight2LeftSlide) {
                        if (!mCanScrollOverstepIndicator && scrollX >= mFirstIndicator.getIndicatorView().getWidth()) {
                            break;
                        }

                        if (scrollX > 0 && scrollX < mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                mPullStatus = PullStatus.RIGHT_PULLING;
                                mFirstIndicator.onPullStatusChange(PullStatus.RIGHT_PULLING);
                            }
                        } else if (scrollX > 0 && scrollX >= mFirstIndicator.getIndicatorView().getWidth()) {
                            if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RIGHT_PULLING) {
                                mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                            }
                        }

                        mFirstIndicator.onDistanceChange(offsetX, 0);
                        scrollBy(-offsetX, 0);
                    } else {
                        if (scrollX <= 0) {
                            break;
                        } else {
                            if (dstScrollX <= 0) {
                                dstScrollX = 0;
                            }
                            mFirstIndicator.onDistanceChange(offsetX, 0);
                            scrollTo(dstScrollX, 0);
                        }
                    }
                    break;

                case BOTH_LEFT_RIGHT:
                    if (disX >= 0) {
                        if (isLeft2RightSlide) {
                            if (mPullStatus == PullStatus.INVOKING && mIsInvokingFromRight) {
                                if (dstScrollX <= 0) {
                                    scrollTo(0, 0);
                                } else {
                                    scrollBy(-offsetX, 0);
                                }
                                break;
                            }

                            if (!mCanScrollOverstepIndicator && scrollX <= -mFirstIndicator.getIndicatorView().getWidth()) {
                                break;
                            }

                            if (scrollX < 0 && scrollX > -mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.LEFT_PULLING;
                                    mFirstIndicator.onPullStatusChange(PullStatus.LEFT_PULLING);
                                }
                            } else if (scrollX < 0 && scrollX <= -mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.LEFT_PULLING) {
                                    mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                    mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                                }
                            }

                            mFirstIndicator.onDistanceChange(offsetX, 0);
                            scrollBy(-offsetX, 0);
                        } else {
                            if (scrollX >= 0) {
                                break;
                            } else {
                                if (dstScrollX >= 0) {
                                    dstScrollX = 0;
                                }
                                mFirstIndicator.onDistanceChange(offsetX, 0);
                                scrollTo(dstScrollX, 0);
                            }
                        }
                    } else {
                        if (isRight2LeftSlide) {
                            if (mPullStatus == PullStatus.INVOKING && mIsInvokingFromLeft) {
                                if (dstScrollX >= 0) {
                                    scrollTo(0, 0);
                                } else {
                                    scrollBy(-offsetX, 0);
                                }
                                break;
                            }

                            if (!mCanScrollOverstepIndicator && scrollX >= mFirstIndicator.getIndicatorView().getWidth()) {
                                break;
                            }

                            if (scrollX > 0 && scrollX < mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RELEASE_TO_INVOKE) {
                                    mPullStatus = PullStatus.RIGHT_PULLING;
                                    mFirstIndicator.onPullStatusChange(PullStatus.RIGHT_PULLING);
                                }
                            } else if (scrollX > 0 && scrollX >= mFirstIndicator.getIndicatorView().getWidth()) {
                                if (mPullStatus == PullStatus.START_PULL || mPullStatus == PullStatus.RIGHT_PULLING) {
                                    mPullStatus = PullStatus.RELEASE_TO_INVOKE;
                                    mFirstIndicator.onPullStatusChange(PullStatus.RELEASE_TO_INVOKE);
                                }
                            }

                            mFirstIndicator.onDistanceChange(offsetX, 0);
                            scrollBy(-offsetX, 0);
                        } else {
                            if (scrollX <= 0) {
                                break;
                            } else {
                                if (dstScrollX <= 0) {
                                    dstScrollX = 0;
                                }
                                mFirstIndicator.onDistanceChange(offsetX, 0);
                                scrollTo(dstScrollX, 0);
                            }
                        }
                    }
                    break;
            }

            mPreviewX = mLastX;
            mPreviewY = mLastY;
            mLastX = currentX;
            mLastY = currentY;
        } else if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_UP) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            switch (mPullDirection) {
                case TOP_TO_BOTTOM:
                    if (scrollY == 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(0, 0, 0, -mFirstIndicator.getIndicatorView().getHeight(), 800);
                            invalidate();
                            break;
                        }

                        if (mPullStatus == PullStatus.START_PULL) {
                            mPullStatus = PullStatus.IDLE;
                            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                            break;
                        }
                    }

                    if (scrollY < 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            int dy = Math.abs(scrollY) - Math.abs(mFirstIndicator.getIndicatorView().getHeight());
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }


                        if (scrollY <= -(mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent)) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            int dy = Math.abs(scrollY) - Math.abs(mFirstIndicator.getIndicatorView().getHeight());
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(0, scrollY, 0, Math.abs(scrollY), 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }
                    break;
                case BOTTOM_TO_TOP:
                    if (scrollY == 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(0, 0, 0, mFirstIndicator.getIndicatorView().getHeight(), 800);
                            invalidate();
                            break;
                        }

                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        break;
                    }

                    if (scrollY > 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            int dy = mFirstIndicator.getIndicatorView().getHeight() - scrollY;
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }


                        if (scrollY >= mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mScroller.startScroll(0, scrollY, 0, mFirstIndicator.getIndicatorView().getHeight() - scrollY, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(0, scrollY, 0, -scrollY, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }
                    break;
                case BOTH_TOP_BOTTOM:
                    if (scrollY == 0) {
                        if (mPullStatus == PullStatus.START_PULL) {
                            mPullStatus = PullStatus.IDLE;
                            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                            mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
                        } else if (mPullStatus == PullStatus.INVOKING) {
                            if (mIsInvokingFromTop) {
                                mScroller.startScroll(0, 0, 0, -mFirstIndicator.getIndicatorView().getHeight(), 800);
                                invalidate();
                            } else if (mIsInvokingFromBottom) {
                                mScroller.startScroll(0, 0, 0, mFirstIndicator.getIndicatorView().getHeight(), 800);
                                invalidate();
                            }
                        }
                        break;
                    }

                    if (scrollY < 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            int dy = Math.abs(scrollY) - Math.abs(mFirstIndicator.getIndicatorView().getHeight());
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }


                        if (scrollY <= -(mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent)) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mIsInvokingFromTop = true;
                            int dy = Math.abs(scrollY) - Math.abs(mFirstIndicator.getIndicatorView().getHeight());
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(0, scrollY, 0, Math.abs(scrollY), 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    } else if (scrollY > 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            int dy = mFirstIndicator.getIndicatorView().getHeight() - scrollY;
                            mScroller.startScroll(0, scrollY, 0, dy, 800);
                            invalidate();
                            break;
                        }

                        if (scrollY >= mFirstIndicator.getIndicatorView().getHeight() * mInvokeDemarcationPercent) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mIsInvokingFromBottom = true;
                            mScroller.startScroll(0, scrollY, 0, mFirstIndicator.getIndicatorView().getHeight() - scrollY, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(0, scrollY, 0, -scrollY, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }

                    break;

                case LEFT_TO_RIGHT:
                    if (scrollX == 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(scrollX, 0, -mFirstIndicator.getIndicatorView().getWidth(), 0, 800);
                            invalidate();
                        } else if (mPullStatus == PullStatus.START_PULL) {
                            mPullStatus = PullStatus.IDLE;
                            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        }
                        break;
                    }

                    if (scrollX < 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(scrollX, 0, -(Math.abs(mFirstIndicator.getIndicatorView().getWidth()) - Math.abs(scrollX)), 0, 800);
                            invalidate();
                            break;
                        }

                        if (scrollX <= -((mFirstIndicator.getIndicatorView().getWidth()) * mInvokeDemarcationPercent)) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mScroller.startScroll(scrollX, 0, -(Math.abs(mFirstIndicator.getIndicatorView().getWidth()) - Math.abs(scrollX)), 0, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(scrollX, 0, Math.abs(scrollX), 0, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }
                    break;
                case RIGHT_TO_LEFT:
                    if (scrollX == 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(0, 0, mFirstIndicator.getIndicatorView().getWidth(), 0, 800);
                            invalidate();
                        } else if (mPullStatus == PullStatus.START_PULL) {
                            mPullStatus = PullStatus.IDLE;
                            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        }
                        break;
                    }

                    if (scrollX > 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(scrollX, 0, Math.abs(scrollX) - Math.abs(mFirstIndicator.getIndicatorView().getWidth()), 0, 800);
                            invalidate();
                        }


                        if (scrollX >= mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mScroller.startScroll(scrollX, 0, mFirstIndicator.getIndicatorView().getWidth() - scrollX, 0, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(scrollX, 0, -scrollX, 0, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }
                    break;
                case BOTH_LEFT_RIGHT:
                    if (scrollX == 0) {
                        if (mPullStatus == PullStatus.START_PULL) {
                            mPullStatus = PullStatus.IDLE;
                            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                            mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
                        } else if (mPullStatus == PullStatus.INVOKING) {
                            if (mIsInvokingFromLeft) {
                                mScroller.startScroll(scrollX, 0, -mFirstIndicator.getIndicatorView().getWidth(), 0, 800);
                                invalidate();
                            } else if (mIsInvokingFromRight) {
                                mScroller.startScroll(scrollX, 0, mSecondIndicator.getIndicatorView().getWidth(), 0, 800);
                                invalidate();
                            }
                        }
                        break;
                    }

                    if (scrollX < 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(scrollX, 0, -(Math.abs(mFirstIndicator.getIndicatorView().getWidth()) - Math.abs(scrollX)), 0, 800);
                            invalidate();
                            break;
                        }

                        if (scrollX <= -((mFirstIndicator.getIndicatorView().getWidth()) * mInvokeDemarcationPercent)) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mIsInvokingFromLeft = true;
                            mScroller.startScroll(scrollX, 0, -(Math.abs(mFirstIndicator.getIndicatorView().getWidth()) - Math.abs(scrollX)), 0, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(scrollX, 0, Math.abs(scrollX), 0, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    } else if (scrollX > 0) {
                        if (mPullStatus == PullStatus.INVOKING) {
                            mScroller.startScroll(scrollX, 0, Math.abs(scrollX) - Math.abs(mFirstIndicator.getIndicatorView().getWidth()), 0, 800);
                            invalidate();
                        }


                        if (scrollX >= mFirstIndicator.getIndicatorView().getWidth() * mInvokeDemarcationPercent) {
                            mPullStatus = PullStatus.INVOKING;
                            mFirstIndicator.onPullStatusChange(PullStatus.INVOKING);
                            mIsInvokingFromRight = true;
                            mScroller.startScroll(scrollX, 0, mFirstIndicator.getIndicatorView().getWidth() - scrollX, 0, 800);
                            invalidate();
                            break;
                        }

                        mScroller.startScroll(scrollX, 0, -scrollX, 0, 800);
                        mPullStatus = PullStatus.IDLE;
                        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                        invalidate();
                    }
                    break;
            }

            mDownX = 0;
            mDownY = 0;
            mLastX = 0;
            mLastY = 0;
            mPreviewX = 0;
            mPreviewY = 0;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            scrollTo(currX, currY);
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (mPullDirection == PullDirection.TOP_TO_BOTTOM || mPullDirection == PullDirection.BOTTOM_TO_TOP) {
            checkSinglePullDirectionChild(childCount);
            View firstChild = getChildAt(0);
            View secondChild = getChildAt(1);
            if (firstChild instanceof IIndicator) {
                mFirstIndicator = (IIndicator) firstChild;
                mContentView = secondChild;
            } else if (secondChild instanceof IIndicator) {
                mFirstIndicator = (IIndicator) secondChild;
                mContentView = firstChild;
            } else {
                throw new IllegalArgumentException("You should put a view which implements IIndicator interface!");
            }
            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
        } else if (mPullDirection == PullDirection.LEFT_TO_RIGHT || mPullDirection == PullDirection.RIGHT_TO_LEFT) {
            checkSinglePullDirectionChild(childCount);
            View firstChild = getChildAt(0);
            View secondChild = getChildAt(1);
            if (firstChild instanceof IIndicator) {
                mFirstIndicator = (IIndicator) firstChild;
                mContentView = secondChild;
            } else if (secondChild instanceof IIndicator) {
                mFirstIndicator = (IIndicator) secondChild;
                mContentView = firstChild;
            } else {
                throw new IllegalArgumentException("You should put a view which implements IIndicator interface!");
            }
            mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
        } else {
            checkBothPullDirectionChild(childCount);
        }
    }

    private void checkSinglePullDirectionChild(int childCount) {
        if (childCount > 2) {
            throw new IllegalArgumentException("PullableViewGroup can only host two children when hold one direction!");
        } else if (childCount == 0) {
            throw new IllegalArgumentException("Where is your child view?");
        } else if (childCount == 1) {
            if (getChildAt(0) instanceof IIndicator) {
                throw new IllegalArgumentException("Where is your content view?");
            } else {
                throw new IllegalArgumentException("Where is your indicator view?");
            }
        }
    }

    private void checkBothPullDirectionChild(int childCount) {
        if (childCount > 3) {
            throw new IllegalArgumentException("PullableViewGroup can only host three children when hold two direction !");
        } else if (childCount == 0) {
            throw new IllegalArgumentException("Where is your child view?");
        } else if (childCount == 1) {
            throw new IllegalArgumentException("You should put two indicators and one content view into PullableViewGroup!");
        } else if (childCount == 2) {
            throw new IllegalArgumentException("You should put two indicators and one content view into PullableViewGroup!");
        } else {
            if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                View topIndicatorView = findViewById(R.id.id_top_indicator);
                View bottomIndicatorView = findViewById(R.id.id_bottom_indicator);
                View contentView = findViewById(R.id.id_content_view);
                if (null == topIndicatorView || !IIndicator.class.isInstance(topIndicatorView)) {
                    throw new IllegalArgumentException("Can not find your top indicator or it is not a indicator!");
                }
                if (null == bottomIndicatorView || !IIndicator.class.isInstance(bottomIndicatorView)) {
                    throw new IllegalArgumentException("Can not find your bottom indicator or it is not a indicator!");
                }
                if (null == contentView) {
                    throw new IllegalArgumentException("Can not find your content view!");
                }
                mFirstIndicator = (IIndicator) topIndicatorView;
                mSecondIndicator = (IIndicator) bottomIndicatorView;
                mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
                mContentView = contentView;
            } else if (mPullDirection == PullDirection.BOTH_LEFT_RIGHT) {
                View leftIndicatorView = findViewById(R.id.id_left_indicator);
                View rightIndicatorView = findViewById(R.id.id_right_indicator);
                View contentView = findViewById(R.id.id_content_view);
                if (null == leftIndicatorView || !IIndicator.class.isInstance(leftIndicatorView)) {
                    throw new IllegalArgumentException("Can not find your left indicator or it is not a indicator!");
                }
                if (null == rightIndicatorView || !IIndicator.class.isInstance(rightIndicatorView)) {
                    throw new IllegalArgumentException("Can not find your right indicator or it is not a indicator!");
                }
                if (null == contentView) {
                    throw new IllegalArgumentException("Can not find your content view!");
                }
                mFirstIndicator = (IIndicator) leftIndicatorView;
                mSecondIndicator = (IIndicator) rightIndicatorView;
                mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
                mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
                mContentView = contentView;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sourceWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sourceHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        final View contentView = mContentView;
        final View firstIndicatorView = mFirstIndicator.getIndicatorView();
        measureChildWithMargins(contentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        if (mPullDirection == PullDirection.TOP_TO_BOTTOM || mPullDirection == PullDirection.BOTTOM_TO_TOP || mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
            int measureSpec = MeasureSpec.makeMeasureSpec(DensityUtil.getAppScreenHeightOfPx(), MeasureSpec.UNSPECIFIED);
            if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM) {
                measureChildWithMargins(firstIndicatorView, widthMeasureSpec, 0, measureSpec, 0);
                View secondIndicator = mSecondIndicator.getIndicatorView();
                measureChildWithMargins(secondIndicator, widthMeasureSpec, 0, measureSpec, 0);
            } else {
                measureChildWithMargins(firstIndicatorView, widthMeasureSpec, 0, measureSpec, 0);
            }
        } else {
            int measureSpec = MeasureSpec.makeMeasureSpec(DensityUtil.getAppScreenWidthOfPx(), MeasureSpec.UNSPECIFIED);
            if (mPullDirection == PullDirection.BOTH_LEFT_RIGHT) {
                measureChildWithMargins(firstIndicatorView, measureSpec, 0, heightMeasureSpec, 0);
                View secondIndicatorView = mSecondIndicator.getIndicatorView();
                measureChildWithMargins(secondIndicatorView, measureSpec, 0, heightMeasureSpec, 0);
            } else {
                measureChildWithMargins(firstIndicatorView, measureSpec, 0, heightMeasureSpec, 0);
            }
        }

        int dstWidth = 0;
        int dstHeight = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            dstWidth = sourceWidth;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            dstWidth = Math.min(sourceWidth, contentView.getMeasuredWidth());
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            dstWidth = contentView.getMeasuredWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            dstHeight = sourceHeight;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            dstHeight = Math.min(sourceHeight, contentView.getMeasuredHeight());
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            dstHeight = contentView.getMeasuredHeight();
        }

        setMeasuredDimension(dstWidth, dstHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View contentView = mContentView;
        View firstIndicatorView = mFirstIndicator.getIndicatorView();
        View secondIndicatorView = null;
        MarginLayoutParams contentParams = (MarginLayoutParams) contentView.getLayoutParams();
        MarginLayoutParams firstIndicatorParams = (MarginLayoutParams) firstIndicatorView.getLayoutParams();
        MarginLayoutParams secondIndicatorParams = null;

        if (mPullDirection == PullDirection.BOTH_TOP_BOTTOM || mPullDirection == PullDirection.BOTH_LEFT_RIGHT) {
            secondIndicatorView = mSecondIndicator.getIndicatorView();
            secondIndicatorParams = (MarginLayoutParams) secondIndicatorView.getLayoutParams();
        }

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int parentLeft = l + paddingLeft;
        int parentTop = t + paddingTop;
        int parentRight = r - paddingRight;
        int parentBottom = b - paddingBottom;

        contentView.layout(parentLeft + contentParams.leftMargin, parentTop + contentParams.topMargin, parentRight - contentParams.rightMargin, parentBottom - contentParams.bottomMargin);
        switch (mPullDirection) {
            case TOP_TO_BOTTOM:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t - (firstIndicatorView.getMeasuredHeight() + firstIndicatorParams.bottomMargin), r - firstIndicatorParams.rightMargin, t - firstIndicatorParams.bottomMargin);
                } else {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, r - firstIndicatorParams.rightMargin, t + firstIndicatorParams.topMargin + firstIndicatorView.getMeasuredHeight());
                }
                break;

            case BOTTOM_TO_TOP:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, b, r - firstIndicatorParams.rightMargin, b + firstIndicatorParams.topMargin + firstIndicatorView.getMeasuredHeight());
                } else {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, b - (firstIndicatorParams.bottomMargin + firstIndicatorView.getMeasuredHeight()), r - firstIndicatorParams.rightMargin, b + firstIndicatorParams.bottomMargin);
                }
                break;

            case BOTH_TOP_BOTTOM:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t - (firstIndicatorView.getMeasuredHeight() + firstIndicatorParams.bottomMargin), r - firstIndicatorParams.rightMargin, t - firstIndicatorParams.bottomMargin);
                    secondIndicatorView.layout(l + secondIndicatorParams.leftMargin, b + secondIndicatorParams.topMargin, r + secondIndicatorParams.rightMargin, b + secondIndicatorParams.topMargin + secondIndicatorView.getMeasuredHeight());
                } else {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, r - firstIndicatorParams.rightMargin, firstIndicatorView.getMeasuredHeight());
                    secondIndicatorView.layout(l + secondIndicatorParams.leftMargin, b - secondIndicatorView.getMeasuredHeight() - secondIndicatorParams.bottomMargin, r - secondIndicatorParams.rightMargin, b - secondIndicatorParams.bottomMargin);
                }
                break;

            case LEFT_TO_RIGHT:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(l - firstIndicatorParams.rightMargin - firstIndicatorView.getMeasuredWidth(), t + firstIndicatorParams.topMargin, l - firstIndicatorParams.rightMargin, b - firstIndicatorParams.bottomMargin);
                } else {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, l + (firstIndicatorView.getMeasuredWidth() + firstIndicatorParams.leftMargin + firstIndicatorParams.rightMargin), b - firstIndicatorParams.bottomMargin);
                }
                break;

            case RIGHT_TO_LEFT:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(r + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, r + firstIndicatorParams.leftMargin + firstIndicatorView.getMeasuredWidth(), b - firstIndicatorParams.bottomMargin);
                } else {
                    firstIndicatorView.layout(r - (firstIndicatorView.getMeasuredWidth() + firstIndicatorParams.rightMargin), t - firstIndicatorParams.topMargin, r - firstIndicatorParams.rightMargin, b - firstIndicatorParams.bottomMargin);
                }
                break;

            case BOTH_LEFT_RIGHT:
                if (mIndicatorDisplayMode == IndicatorDisplayMode.SCROLL_WITH_CONTENT) {
                    firstIndicatorView.layout(l - firstIndicatorParams.rightMargin - firstIndicatorView.getMeasuredWidth(), t + firstIndicatorParams.topMargin, l - firstIndicatorParams.rightMargin, b - firstIndicatorParams.bottomMargin);
                    secondIndicatorView.layout(r + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, r + firstIndicatorParams.leftMargin + firstIndicatorView.getMeasuredWidth(), b - firstIndicatorParams.bottomMargin);
                } else {
                    firstIndicatorView.layout(l + firstIndicatorParams.leftMargin, t + firstIndicatorParams.topMargin, l + (firstIndicatorView.getMeasuredWidth() + firstIndicatorParams.leftMargin + firstIndicatorParams.rightMargin), b - firstIndicatorParams.bottomMargin);
                    secondIndicatorView.layout(r - (firstIndicatorView.getMeasuredWidth() + firstIndicatorParams.rightMargin), t - firstIndicatorParams.topMargin, r - firstIndicatorParams.rightMargin, b - firstIndicatorParams.bottomMargin);
                }
                break;
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT);
    }
}
