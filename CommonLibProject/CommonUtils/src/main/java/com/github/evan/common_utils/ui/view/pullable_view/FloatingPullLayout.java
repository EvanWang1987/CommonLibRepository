package com.github.evan.common_utils.ui.view.pullable_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.IIndicator;
import com.github.evan.common_utils.utils.UiUtil;

import static com.github.evan.common_utils.ui.view.pullable_view.PullDirection.BOTH_LEFT_RIGHT;
import static com.github.evan.common_utils.ui.view.pullable_view.PullDirection.BOTH_TOP_BOTTOM;
import static com.github.evan.common_utils.ui.view.pullable_view.PullDirection.TOP_TO_BOTTOM;

/**
 * Created by Administrator on 2018/5/30.
 */
public class FloatingPullLayout extends RelativeLayout {
    private boolean mIsInvokeFromInflateFinish = false;
    private PullLayout mPullLayout;
    private View mContentView;
    private IIndicator mFirstIndicator, mSecondIndicator;

    public FloatingPullLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public FloatingPullLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FloatingPullLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatingPullLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public boolean replaceFirstIndicator(IIndicator indicator, boolean isAutoRequestLayout) {
        if (null == indicator || null == indicator.getIndicatorView()) {
            return false;
        }

        replaceFirstIndicatorAndAttchView(indicator, isAutoRequestLayout);
        mFirstIndicator = indicator;
        return mPullLayout.replaceFirstIndicatorNotAttach(indicator);
    }

    public boolean replaceSecondIndicator(IIndicator indicator, boolean isAutoRequestLayout) {
        if (null == indicator || null == indicator.getIndicatorView()) {
            return false;
        }

        replaceSecondIndicatorAndAttchView(indicator, isAutoRequestLayout);
        mSecondIndicator = indicator;
        return mPullLayout.replaceSecondIndicatorNotAttach(indicator);
    }

    public boolean replaceContentView(View content, boolean autoRequestLayout) {
        if (null == content) {
            return false;
        }

        mContentView = content;
        boolean isSuccess = mPullLayout.replaceContentView(content, autoRequestLayout);
        if(!isSuccess){
            return false;
        }
        if(!mIsInvokeFromInflateFinish && mPullLayout.getParent() == null){
//            int index = this.getChildCount();
            this.addView(mPullLayout, -1, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            mIsInvokeFromInflateFinish = true;
        }
        return true;
    }

    public void setPullListener(PullListener pullListener) {
        mPullLayout.setPullListener(pullListener);
    }

    public PullListener getPullListener() {
        return mPullLayout.getPullListener();
    }

    public void setDamping(@FloatRange(from = 0.6f, to = 1.2f) float damping) {
        mPullLayout.setDamping(damping);
    }

    public float getDamping() {
        return mPullLayout.getDamping();
    }

    public int getScrollBackDuration() {
        return mPullLayout.getScrollBackDuration();
    }

    public void setScrollBackDuration(int scrollBackDuration) {
        mPullLayout.setScrollBackDuration(scrollBackDuration);
    }

    public float getInvokeDemarcationPercent() {
        return mPullLayout.getInvokeDemarcationPercent();
    }

    public void setInvokeDemarcationPercent(@FloatRange(from = 0.2f, to = 1.2f) float invokeDemarcationPercent) {
        mPullLayout.setInvokeDemarcationPercent(invokeDemarcationPercent);
    }

    public PullDirection getPullDirection() {
        return mPullLayout.getPullDirection();
    }

    public void setPullDirection(PullDirection pullDirection) {
        mPullLayout.setPullDirection(pullDirection);
    }

    public PullChecker getPullChecker() {
        return mPullLayout.getPullChecker();
    }

    public void setPullChecker(PullChecker pullChecker) {
        mPullLayout.setPullChecker(pullChecker);
    }

    public boolean canScrollOverstepIndicator() {
        return mPullLayout.canScrollOverstepIndicator();
    }

    public void setCanScrollOverstepIndicator(boolean canScrollOverstepIndicator) {
        mPullLayout.setCanScrollOverstepIndicator(canScrollOverstepIndicator);
    }

    public PullStatus getPullStatus() {
        return mPullLayout.getPullStatus();
    }

    public void autoInvoke(boolean isInvokeSecondIndicator, int scrollAnimationDuration) {
        mPullLayout.autoInvoke(isInvokeSecondIndicator, scrollAnimationDuration);
    }

    public void invokeComplete() {
        mPullLayout.invokeComplete();
    }

    private void replaceFirstIndicatorAndAttchView(IIndicator indicator, boolean isAutoRequestLayout) {
        if(null != mFirstIndicator && null != mFirstIndicator.getIndicatorView() && mFirstIndicator.getIndicatorView().getParent() == this){
            this.removeView(mFirstIndicator.getIndicatorView());
        }

        LayoutParams indicatorParams;
        switch (mPullLayout.getPullDirection()){
            case TOP_TO_BOTTOM:
            case BOTH_TOP_BOTTOM:
                indicatorParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;

            case BOTTOM_TO_TOP:
                indicatorParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;

            case LEFT_TO_RIGHT:
            case BOTH_LEFT_RIGHT:
                indicatorParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;

            case RIGHT_TO_LEFT:
                indicatorParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;
        }
    }

    private void replaceSecondIndicatorAndAttchView(IIndicator indicator, boolean isAutoRequestLayout) {
        if(null != mSecondIndicator && null != mSecondIndicator.getIndicatorView() && mSecondIndicator.getIndicatorView().getParent() == this){
            this.removeView(mSecondIndicator.getIndicatorView());
        }

        LayoutParams indicatorParams;
        switch (mPullLayout.getPullDirection()){
            case TOP_TO_BOTTOM:
            case BOTTOM_TO_TOP:
                break;

            case BOTH_TOP_BOTTOM:
                indicatorParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;

            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
                break;

            case BOTH_LEFT_RIGHT:
                indicatorParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                indicatorParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                indicator.getIndicatorView().setLayoutParams(indicatorParams);
                if(isAutoRequestLayout){
                    ViewGroup.LayoutParams layoutParams = indicator.getIndicatorView().getLayoutParams();
                    if(null == layoutParams){
                        super.addView(indicator.getIndicatorView(), -1, indicatorParams);
                    }else{
                        super.addView(indicator.getIndicatorView(), -1);
                    }
                }else{
                    UiUtil.addViewInner(this, indicator.getIndicatorView(), -1, false);
                }
                break;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        checkChildrenScrollWithContentMode(childCount);
        MarginLayoutParams lps = new MarginLayoutParams(mContentView.getLayoutParams());
        this.removeView(mContentView);
        mPullLayout.replaceFirstIndicatorNotAttach(mFirstIndicator);
        mPullLayout.replaceSecondIndicatorNotAttach(mSecondIndicator);
        mFirstIndicator.onPullStatusChange(PullStatus.IDLE);
        if(null != mSecondIndicator){
            mSecondIndicator.onPullStatusChange(PullStatus.IDLE);
        }
        mPullLayout.replaceContentView(mContentView, true);
        this.addView(mPullLayout, new MarginLayoutParams(lps));
        mPullLayout.bringToFront();
        mIsInvokeFromInflateFinish = true;
    }

    private void init(AttributeSet attrs){
        mPullLayout = new PullLayout(getContext());
        PullDirection pullDirection = TOP_TO_BOTTOM;
        boolean canScrollOverstepIndicator = true;
        int scrollBackDuration = 800;
        float damping = 1f;
        float invokeDemarcationRelativePercent = 1f;
        boolean isPreventHorizontalSlide = false;
        boolean isPreventVerticalSlide = false;

        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatingPullLayout);
            int anInt = typedArray.getInt(R.styleable.FloatingPullLayout_floating_pull_direction, TOP_TO_BOTTOM.value);
            pullDirection = PullDirection.valueOf(anInt);
            canScrollOverstepIndicator = typedArray.getBoolean(R.styleable.FloatingPullLayout_floating_can_scroll_overstep_indicator, canScrollOverstepIndicator);
            scrollBackDuration = typedArray.getInt(R.styleable.FloatingPullLayout_floating_invoke_complete_animation_duration, scrollBackDuration);
            damping = typedArray.getFloat(R.styleable.FloatingPullLayout_floating_damping, damping);
            invokeDemarcationRelativePercent = typedArray.getFloat(R.styleable.FloatingPullLayout_floating_invoke_demarcation_relative_indicator, invokeDemarcationRelativePercent);
            isPreventHorizontalSlide = typedArray.getBoolean(R.styleable.FloatingPullLayout_floating_is_prevent_horizontal_slide, isPreventHorizontalSlide);
            isPreventVerticalSlide = typedArray.getBoolean(R.styleable.FloatingPullLayout_floating_is_prevent_vertical_slide, isPreventVerticalSlide);
            typedArray.recycle();
        }
        if (damping < 0.6f || damping > 1.2f) {
            throw new IllegalArgumentException("Damping must be between 0.6f and 1.2f");
        }

        if (invokeDemarcationRelativePercent < 0.2f || invokeDemarcationRelativePercent > 1.2f) {
            throw new IllegalArgumentException("invoke_demarcation_relative_indicator must be between 0.2f and 1.2f");
        }

        mPullLayout.setIndicatorDisplayMode(IndicatorDisplayMode.UNDER_CONTENT);
        mPullLayout.setPullDirection(pullDirection);
        mPullLayout.setCanScrollOverstepIndicator(canScrollOverstepIndicator);
        mPullLayout.setCanScrollOverstepIndicator(canScrollOverstepIndicator);
        mPullLayout.setScrollBackDuration(scrollBackDuration);
        mPullLayout.setDamping(damping);
        mPullLayout.setInvokeDemarcationPercent(invokeDemarcationRelativePercent);
        switch (mPullLayout.getPullDirection()){
            case TOP_TO_BOTTOM:
            case BOTTOM_TO_TOP:
            case BOTH_TOP_BOTTOM:
                mPullLayout.setPreventHorizontalSlide(isPreventHorizontalSlide);
                break;

            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
            case BOTH_LEFT_RIGHT:
                mPullLayout.setPreventVerticalSlide(isPreventVerticalSlide);
                break;
        }

    }

    private void checkChildrenScrollWithContentMode(int childCount) {
        if (mPullLayout.getPullDirection() == PullDirection.TOP_TO_BOTTOM || mPullLayout.getPullDirection() == PullDirection.BOTTOM_TO_TOP) {
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
        } else if (mPullLayout.getPullDirection() == PullDirection.LEFT_TO_RIGHT || mPullLayout.getPullDirection() == PullDirection.RIGHT_TO_LEFT) {
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
            if (mPullLayout.getPullDirection() == PullDirection.BOTH_TOP_BOTTOM) {
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
            } else if (mPullLayout.getPullDirection() == PullDirection.BOTH_LEFT_RIGHT) {
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
}
