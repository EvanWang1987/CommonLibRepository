package com.github.evan.common_utils.ui.view.ptr;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.simpleImplementInterface.SaveLastValueAnimatorUpdateListener;
import com.github.evan.common_utils.ui.view.ptr.indicator.IIndicator;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/12/2.
 */
public class PtrLayout extends ViewGroup {
    private PtrStatus mPtrStatus = PtrStatus.IDLE;
    private int mTouchSlop;
    private int mDownX, mDownY, mLastY;
    @FloatRange(from = 0.5f, to = 1.5f)
    private float mDumpingMultiple = 0.7f;
    @FloatRange(from = 0.7f, to = 1.5f)
    private float mReleaseLineOfIndicatorHeightMultiple = 1f;
    private PullToRefreshSwitcher mPtrSwitcher;
    private OnRefreshListener mRefreshListener;
    private int mIndicatorId = -1, mContentId = -1, mAdViewId = -1;
    private IIndicator mIndicator;
    private View mContent;
    private View mAdView;
    private boolean mIsCalledStartPullListener = false;
    private int mIndicatorHeight;
    private int mReleaseToRefreshDividingLine;
    private ValueAnimator mAutoRefreshAnim = ValueAnimator.ofInt();
    private ValueAnimator mRollBackAnim = ValueAnimator.ofInt();
    private int mRollBackToIdleDuration = 500;
    private int mRollBackToRefreshingDuration = 300;
    private int mAutoRefreshDuration = 1000;

    public PtrLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PtrLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PtrLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PtrLayout);
            mIndicatorId = typedArray.getResourceId(R.styleable.PtrLayout_indicator_id, -1);
            mContentId = typedArray.getResourceId(R.styleable.PtrLayout_content_id, -1);
            mAdViewId = typedArray.getResourceId(R.styleable.PtrLayout_ad_view_id, -1);
            float dumping = typedArray.getFloat(R.styleable.PtrLayout_slide_dumping, 0.7f);
            float releaseLineMultiple = typedArray.getFloat(R.styleable.PtrLayout_release_line_of_indicator_height_multiple, 1f);
            mRollBackToIdleDuration = typedArray.getInt(R.styleable.PtrLayout_roll_back_to_idle_duration, 500);
            mRollBackToRefreshingDuration = typedArray.getInt(R.styleable.PtrLayout_roll_back_to_refreshing_duration, 300);
            mAutoRefreshDuration = typedArray.getInt(R.styleable.PtrLayout_auto_refresh_duration, mAutoRefreshDuration);
            if (dumping >= 0.5f || dumping <= 1.5f) {
                mDumpingMultiple = dumping;
            }
            if (dumping >= 0.7f || dumping <= 1.5f) {
                mReleaseLineOfIndicatorHeightMultiple = releaseLineMultiple;
            }
            typedArray.recycle();
        }
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mAutoRefreshAnim.setDuration(mAutoRefreshDuration);
    }

    public int getIndicatorId() {
        return mIndicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.mIndicatorId = indicatorId;
    }

    public int getContentId() {
        return mContentId;
    }

    public void setContentId(int contentId) {
        this.mContentId = contentId;
    }

    public int getAdViewId() {
        return mAdViewId;
    }

    public void setAdViewId(int adViewId) {
        this.mAdViewId = adViewId;
    }

    public int getRollBackToIdleDuration() {
        return mRollBackToIdleDuration;
    }

    public void setRollBackToIdleDuration(long rollBackToIdleDuration) {
        this.mRollBackToIdleDuration = (int) rollBackToIdleDuration;
    }

    public int getRollBackToRefreshingDuration() {
        return mRollBackToRefreshingDuration;
    }

    public void setRollBackToRefreshingDuration(long rollBackToRefreshingDuration) {
        this.mRollBackToRefreshingDuration = (int) rollBackToRefreshingDuration;
    }

    public int getAutoRefreshDuration() {
        return mAutoRefreshDuration;
    }

    public void setAutoRefreshDuration(long autoRefreshDuration) {
        this.mAutoRefreshDuration = (int) autoRefreshDuration;
    }

    public float getDumpingMultiple() {
        return mDumpingMultiple;
    }

    public void setDumpingMultiple(@FloatRange(from = 0.5f, to = 1.5f) float dumpingMultiple) {
        if (dumpingMultiple >= 0.5f && dumpingMultiple <= 1.5f) {
            this.mDumpingMultiple = dumpingMultiple;
        }
    }

    public float getReleaseLineOfIndicatorHeightMultiple() {
        return mReleaseLineOfIndicatorHeightMultiple;
    }

    public void setReleaseLineOfIndicatorHeightMultiple(@FloatRange(from = 0.7f, to = 1.5f) float releaseLineOfIndicatorHeightMultiple) {
        if (releaseLineOfIndicatorHeightMultiple >= 0.7f && releaseLineOfIndicatorHeightMultiple <= 1.5f) {
            this.mReleaseLineOfIndicatorHeightMultiple = releaseLineOfIndicatorHeightMultiple;
        }
    }

    public void setPtrSwitcher(PullToRefreshSwitcher ptrSwitcher) {
        this.mPtrSwitcher = ptrSwitcher;
    }

    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    public void autoRefresh(boolean withAnimation) {
        mPtrStatus = PtrStatus.IDLE;
        mIndicator.setStatus(mPtrStatus);
        if (!withAnimation) {
            mPtrStatus = PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.PULLING;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
            mIndicator.setStatus(mPtrStatus);
            mPtrStatus = PtrStatus.REFRESHING;
            mIndicator.setStatus(mPtrStatus);
            PtrLayout.this.scrollTo(0, mReleaseToRefreshDividingLine);
        } else {
            mAutoRefreshAnim.setIntValues(0, mReleaseToRefreshDividingLine);
            mAutoRefreshAnim.addUpdateListener(new SaveLastValueAnimatorUpdateListener() {
                @Override
                public void onUpdateAnimation(ValueAnimator animator, Object lastValue) {
                    int value = (int) animator.getAnimatedValue();
                    if (value < 0 && value > mReleaseToRefreshDividingLine) {
                        if (mPtrStatus == PtrStatus.START_PULL) {
                            mPtrStatus = PtrStatus.PULLING;
                            mIndicator.setStatus(mPtrStatus);
                        }
                    }

                    int offsetFromLast = null == lastValue ? 0 : Math.abs(value) - Math.abs((int) lastValue);
                    mIndicator.setOffsetY(Math.abs(value), offsetFromLast);
                    PtrLayout.this.scrollTo(0, value);
                    if (value == mReleaseToRefreshDividingLine) {
                        mPtrStatus = PtrStatus.REFRESHING;
                        mIndicator.setStatus(mPtrStatus);
                        mAutoRefreshAnim.removeUpdateListener(this);
                    }
                }
            });
            mPtrStatus = PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            mAutoRefreshAnim.start();
        }
    }

    public void refreshComplete(boolean withAnimation) {
        mPtrStatus = PtrStatus.REFRESHED;
        mIndicator.setStatus(mPtrStatus);
        if (withAnimation) {
            mRollBackAnim.setIntValues(getScrollY(), 0);
            mRollBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    PtrLayout.this.scrollTo(0, value);
                    if (value == 0) {
                        mPtrStatus = PtrStatus.IDLE;
                        mIndicator.setStatus(mPtrStatus);
                        mRollBackAnim.removeUpdateListener(this);
                    }
                }
            });
            mRollBackAnim.start();
        } else {
            scrollTo(0, mIndicatorHeight);
            mPtrStatus = PtrStatus.IDLE;
            mIndicator.setStatus(mPtrStatus);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastY = mDownY;
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int offsetX = currentX - mDownX;
            int offsetY = currentY - mDownY;
            mLastY = currentY;
            boolean isVerticalSlide = Math.abs(offsetY) > Math.abs(offsetX);
            boolean isTop2BottomSlide = offsetY > 0;
            boolean isArriveMinVerticalSlop = offsetY >= mTouchSlop;
            if (mPtrSwitcher.checkCanPullToRefresh() && isVerticalSlide && isTop2BottomSlide && isArriveMinVerticalSlop) {
                return true;
            }
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
            mLastY = 0;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getX();
            mDownY = (int) event.getY();
            mLastY = mDownY;
            mPtrStatus = mPtrStatus == PtrStatus.REFRESHING ? PtrStatus.REFRESHING : PtrStatus.START_PULL;
            mIndicator.setStatus(mPtrStatus);
            if (mPtrStatus == PtrStatus.START_PULL && mRefreshListener != null) {
                mRefreshListener.onStartPulling();
                mIsCalledStartPullListener = true;
            }
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentY = (int) event.getY();
            int offsetY = currentY - mDownY;
            int offsetYBetweenLast = currentY - mLastY;
            mLastY = currentY;
            boolean isTop2BottomSlide = offsetYBetweenLast > 0;
            int destinationScrollY = getScrollY() + -offsetYBetweenLast;

            if (mPtrStatus == PtrStatus.IDLE) {
                if (!isTop2BottomSlide) {
                    return true;
                } else {
                    mPtrStatus = PtrStatus.START_PULL;
                    mIndicator.setStatus(mPtrStatus);
                    if (!mIsCalledStartPullListener) {
                        mRefreshListener.onStartPulling();
                        mIsCalledStartPullListener = true;
                    }
                    return true;
                }
            } else if (mPtrStatus == PtrStatus.START_PULL) {
                if (!isTop2BottomSlide) {
                    return true;
                } else {
                    if (destinationScrollY > mReleaseToRefreshDividingLine) {
                        mPtrStatus = PtrStatus.PULLING;
                        mIndicator.setStatus(mPtrStatus);
                    } else {
                        mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
                        mIndicator.setStatus(mPtrStatus);
                    }
                }
            } else if (mPtrStatus == PtrStatus.PULLING) {
                if (!isTop2BottomSlide) {
                    if (destinationScrollY > 0) {
                        offsetYBetweenLast = 0;
                    }
                } else {
                    if (destinationScrollY <= mReleaseToRefreshDividingLine) {
                        mPtrStatus = PtrStatus.RELEASE_TO_REFRESH;
                        mIndicator.setStatus(mPtrStatus);
                    }
                }
            } else if (mPtrStatus == PtrStatus.RELEASE_TO_REFRESH) {
                if (!isTop2BottomSlide) {
                    if (destinationScrollY < 0 && destinationScrollY > mReleaseToRefreshDividingLine) {
                        mPtrStatus = PtrStatus.PULLING;
                        mIndicator.setStatus(mPtrStatus);
                    }
                } else {
                    if (destinationScrollY > 0) {
                        return true;
                    }
                }
            }

            mIndicator.setOffsetY(offsetY, (int) (offsetYBetweenLast * mDumpingMultiple));
            int position = (int) (-offsetYBetweenLast * mDumpingMultiple);
            scrollBy(0, position);
        } else if (actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL) {
            mDownX = 0;
            mDownY = 0;
            mLastY = 0;
            mIsCalledStartPullListener = false;

            if (mPtrStatus == PtrStatus.RELEASE_TO_REFRESH || mPtrStatus == PtrStatus.REFRESHING) {
                //回到分界线
                if (mPtrStatus == PtrStatus.RELEASE_TO_REFRESH) {
                    mPtrStatus = PtrStatus.REFRESHING;
                    mIndicator.setStatus(mPtrStatus);
                    if (null != mRefreshListener) {
                        mRefreshListener.onRefresh();
                    }
                }
                int scrollY = getScrollY();
                mRollBackAnim.setIntValues(scrollY, mReleaseToRefreshDividingLine);
                mRollBackAnim.setDuration(mRollBackToRefreshingDuration);
                mRollBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        PtrLayout.this.scrollTo(0, value);
                        if (value == mReleaseToRefreshDividingLine) {
                            mRollBackAnim.removeUpdateListener(this);
                        }
                    }
                });
                mRollBackAnim.start();
            } else if (mPtrStatus == PtrStatus.PULLING) {
                //回到原位
                mPtrStatus = PtrStatus.IDLE;
                mIndicator.setStatus(mPtrStatus);
                int scrollY = getScrollY();
                mRollBackAnim.setIntValues(scrollY, 0);
                mRollBackAnim.setDuration(mRollBackToIdleDuration);
                mRollBackAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        PtrLayout.this.scrollTo(0, value);
                        if (value == 0) {
                            mRollBackAnim.removeUpdateListener(this);
                        }
                    }
                });
                mRollBackAnim.start();
            }
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        View indicatorView = mIndicator.getIndicatorView();
        View contentView = mContent;
        View adView = mAdView;

        MarginLayoutParams contentParams = (MarginLayoutParams) contentView.getLayoutParams();

        int contentLeft = contentParams.leftMargin + paddingLeft;
        int contentTop = contentParams.topMargin + paddingTop;
        int contentRight = r - contentParams.rightMargin - paddingRight;
        int contentBottom = b - contentParams.bottomMargin - paddingBottom;
        contentView.layout(contentLeft, contentTop, contentRight, contentBottom);

        int indicatorLeft = 0;
        int indicatorTop = -indicatorView.getMeasuredHeight();
        int indicatorRight = r;
        int indicatorBottom = t;
        indicatorView.layout(indicatorLeft, indicatorTop, indicatorRight, indicatorBottom);

        if (null != mAdView) {
            int adLeft = 0;
            int adRight = r;
            int adTop = indicatorTop + (-adView.getMeasuredHeight());
            int adBottom = indicatorTop;
            adView.layout(adLeft, adTop, adRight, adBottom);
        }

        if (mPtrStatus == PtrStatus.IDLE) {
            scrollTo(0, 0);
            mIndicatorHeight = indicatorView.getHeight();
            mReleaseToRefreshDividingLine = (int) -(mIndicatorHeight * mReleaseLineOfIndicatorHeightMultiple);
            mIndicator.setStatus(mPtrStatus);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sourceWidth = MeasureSpec.getMode(widthMeasureSpec);
        int widthMode = MeasureSpec.getSize(widthMeasureSpec);
        int sourceHeight = MeasureSpec.getMode(heightMeasureSpec);
        int heightMode = MeasureSpec.getSize(heightMeasureSpec);

        View indicatorView = mIndicator.getIndicatorView();
        View contentView = mContent;
        View adView = mAdView;

        measureChildWithMargins(indicatorView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(contentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        measureChildWithMargins(adView, widthMeasureSpec, 0, heightMeasureSpec, 0);

        int dstWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            dstWidth = sourceWidth;
        } else {
            //AT_MOST
            dstWidth = Math.min(sourceWidth, contentView.getMeasuredWidth());
        }

        int dstHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            dstHeight = sourceHeight;
        } else {
            dstHeight = Math.min(sourceHeight, contentView.getMeasuredHeight());
        }

        setMeasuredDimension(dstWidth, dstHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 3) {
            throw new IllegalArgumentException("PtrLayout can only host three children！");
        } else {
            if (childCount == 3) {
                View indicatorView = findViewById(mIndicatorId);
                View contentView = findViewById(mContentId);
                View adView = findViewById(mAdViewId);

                if (indicatorView == null) {
                    throw new IllegalArgumentException("Can not find child with id: " + mIndicatorId);
                }

                if (contentView == null) {
                    throw new IllegalArgumentException("Can not find child with id: " + mContentId);
                }

                if (adView == null) {
                    throw new IllegalArgumentException("Can not find child with id: " + mAdViewId);
                }


                if (!IIndicator.class.isInstance(indicatorView)) {
                    throw new IllegalArgumentException("The child of id " + mIndicatorId + " is not a Indicator! ");
                }

                mIndicator = (IIndicator) indicatorView;
                mContent = contentView;
                mAdView = adView;
            } else if (childCount == 2) {
                View indicatorView = findViewById(mIndicatorId);
                if (!IIndicator.class.isInstance(indicatorView)) {
                    throw new IllegalArgumentException("The child of id " + mIndicatorId + " is not a Indicator! ");
                }

                View contentView = null;
                if (mContentId != -1) {
                    contentView = findViewById(mContentId);
                }

                View adView = null;
                if (mAdViewId != -1) {
                    adView = findViewById(mAdViewId);
                }

                if (null != contentView) {
                    mContent = contentView;
                } else {
                    TextView textView = new TextView(getContext());
                    textView.setText("Did you forget to put your content into PtrLayout? ");
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    textView.setTextColor(getResources().getColor(R.color.Black));
                    mContent = textView;
                    addView(mContent);
                }

                if (null != adView) {
                    mAdView = adView;
                }
            } else if (childCount == 1) {
                View indicatorView = findViewById(mIndicatorId);
                if (!IIndicator.class.isInstance(indicatorView)) {
                    throw new IllegalArgumentException("The child of id " + mIndicatorId + " is not a Indicator! ");
                }

                TextView textView = new TextView(getContext());
                textView.setText("Did you forget to put your content into PtrLayout? ");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setTextColor(getResources().getColor(R.color.Black));
                mContent = textView;
                addView(mContent);
            }
        }
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
}
