package com.github.evan.common_utils.ui.view.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.CommonGestures;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/8.
 */
public class ExtraButtonEditText extends LimitEditText implements CommonGestures.TouchListener {
    public static final int EXTRA_BUTTON_NONE = 0;
    public static final int EXTRA_BUTTON_LEFT = 1;
    public static final int EXTRA_BUTTON_RIGHT = 2;

    public interface OnExtraButtonClickListener{
        /**
         * 左右按钮点击回调
         * @param v
         * @param whichButton {@Link ExtraButtonEditText#EXTRA_BUTTON_NONE, ExtraButtonEditText#EXTRA_BUTTON_LEFT, ExtraButtonEditText#EXTRA_BUTTON_RIGHT}
         */
        void onExtraButtonClick(View v, int whichButton);
    }


    private boolean mIsShowLeftButton = true;
    private boolean mIsShowRightButton = true;
    private boolean mIsAutoClearTextWhenClickLeftButton = false;
    private boolean mIsAutoClearTextWhenClickRightButton = false;
    private Drawable mLeftButtonDrawable, mRightButtonDrawable;
    private CommonGestures mGestures;
    private OnExtraButtonClickListener mClickListener;
    private boolean mIsEventAtLeftDrawable;
    private boolean mIsEventAtRightDrawable;

    public ExtraButtonEditText(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public ExtraButtonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public ExtraButtonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    public boolean isShowLeftButton() {
        return mIsShowLeftButton;
    }

    public void setShowLeftButton(boolean isShowLeftButton) {
        refreshDrawable(mLeftButtonDrawable, EXTRA_BUTTON_LEFT, isShowLeftButton);
    }

    public boolean isShowRightButton() {
        return mIsShowRightButton;
    }

    public void setShowRightButton(boolean isShowRightButton) {
        refreshDrawable(mRightButtonDrawable, EXTRA_BUTTON_RIGHT, isShowRightButton);
    }

    public boolean isAutoClearTextWhenClickLeftButton() {
        return mIsAutoClearTextWhenClickLeftButton;
    }

    public void setAutoClearTextWhenClickLeftButton(boolean isAutoClearTextWhenClickLeftButton) {
        this.mIsAutoClearTextWhenClickLeftButton = isAutoClearTextWhenClickLeftButton;
    }

    public boolean isAutoClearTextWhenClickRightButton() {
        return mIsAutoClearTextWhenClickRightButton;
    }

    public void setAutoClearTextWhenClickRightButton(boolean isAutoClearTextWhenClickRightButton) {
        this.mIsAutoClearTextWhenClickRightButton = isAutoClearTextWhenClickRightButton;
    }

    public Drawable getLeftButtonDrawable() {
        return mLeftButtonDrawable;
    }

    public void setLeftButtonDrawable(Drawable leftButtonDrawable) {
        refreshDrawable(leftButtonDrawable, EXTRA_BUTTON_LEFT, mIsShowLeftButton);
    }

    public Drawable getRightButtonDrawable() {
        return mRightButtonDrawable;
    }

    public void setRightButtonDrawable(Drawable rightButtonDrawable) {
        refreshDrawable(rightButtonDrawable, EXTRA_BUTTON_RIGHT, mIsShowRightButton);
    }

    public OnExtraButtonClickListener getClickListener() {
        return mClickListener;
    }

    public void setExtraButtonClickListener(OnExtraButtonClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    private void refreshDrawable(Drawable drawable, int whichButton, boolean isShow){
        boolean isShowLeftButton = whichButton == EXTRA_BUTTON_LEFT ? isShow : mIsShowLeftButton;
        boolean isShowRightButton = whichButton == EXTRA_BUTTON_RIGHT ? isShow : mIsShowRightButton;
        Drawable leftDrawable = whichButton == EXTRA_BUTTON_LEFT ? drawable : mLeftButtonDrawable;
        Drawable rightDrawable = whichButton == EXTRA_BUTTON_RIGHT ? drawable : mRightButtonDrawable;
        mIsShowLeftButton = isShowLeftButton;
        mIsShowRightButton = isShowRightButton;
        mLeftButtonDrawable = leftDrawable;
        mRightButtonDrawable = rightDrawable;
        mLeftButtonDrawable.setBounds(0, 0, mLeftButtonDrawable.getIntrinsicWidth(), mLeftButtonDrawable.getIntrinsicHeight());
        mRightButtonDrawable.setBounds(0, 0, mRightButtonDrawable.getIntrinsicWidth(), mRightButtonDrawable.getIntrinsicHeight());
        setCompoundDrawables((isShowLeftButton ? mLeftButtonDrawable : null), null, (isShowRightButton ? mRightButtonDrawable : null), null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        final int leftDrawableWidth = mLeftButtonDrawable.getIntrinsicWidth();
        final int leftDrawableHeight = mLeftButtonDrawable.getIntrinsicHeight();
        final int rightDrawableWidth = mRightButtonDrawable.getIntrinsicWidth();
        final int rightDrawableHeight = mRightButtonDrawable.getIntrinsicHeight();

        final int measuredWidth = getMeasuredWidth();
        final int measuredHeight = getMeasuredHeight();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        final int leftDrawableXInView = paddingLeft;
        final int leftDrawableX2InView = leftDrawableXInView + leftDrawableWidth;
        final int leftDrawableYInView = (measuredHeight - paddingTop - paddingBottom) / 2 - leftDrawableHeight / 2;
        final int leftDrawableY2InView = leftDrawableYInView + leftDrawableHeight;

        final int rightDrawableXInView = measuredWidth - paddingRight - rightDrawableWidth + paddingLeft;
        final int rightDrawableX2InView = rightDrawableXInView + rightDrawableWidth;
        final int rightDrawableYInView = (measuredHeight - paddingTop - paddingBottom) / 2 - rightDrawableHeight / 2;
        final int rightDrawableY2InView = rightDrawableYInView + rightDrawableHeight;

        mIsEventAtLeftDrawable = x >= leftDrawableXInView && x <= leftDrawableX2InView && y >= leftDrawableYInView && y <= leftDrawableY2InView;
        mIsEventAtRightDrawable = x >= rightDrawableXInView && x <= rightDrawableX2InView && y >= rightDrawableYInView && y <= rightDrawableY2InView;
        return  mIsEventAtLeftDrawable || mIsEventAtRightDrawable ? mGestures.onTouchEvent(event) : super.onTouchEvent(event);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtraButtonEditText);
            mIsShowLeftButton = typedArray.getBoolean(R.styleable.ExtraButtonEditText_isShowLeftButton, mIsShowLeftButton);
            mIsShowRightButton = typedArray.getBoolean(R.styleable.ExtraButtonEditText_isShowRightButton, mIsShowRightButton);
            mLeftButtonDrawable = typedArray.getDrawable(R.styleable.ExtraButtonEditText_leftButtonDrawableRes);
            mRightButtonDrawable = typedArray.getDrawable(R.styleable.ExtraButtonEditText_rightButtonDrawableRes);
            mIsAutoClearTextWhenClickLeftButton = typedArray.getBoolean(R.styleable.ExtraButtonEditText_isAutoClearTextWhenClickLeftButton, mIsAutoClearTextWhenClickLeftButton);
            mIsAutoClearTextWhenClickRightButton = typedArray.getBoolean(R.styleable.ExtraButtonEditText_isAutoClearTextWhenClickRightButton, mIsAutoClearTextWhenClickRightButton);
            typedArray.recycle();
        }

        mLeftButtonDrawable = null == mLeftButtonDrawable ? ResourceUtil.getDrawable(R.mipmap.ic_cancel_dark) : mLeftButtonDrawable;
        mRightButtonDrawable = null == mRightButtonDrawable ? ResourceUtil.getDrawable(R.mipmap.ic_cancel_dark) : mRightButtonDrawable;
        setCompoundDrawablePadding(DensityUtil.dp2px(5));
        mLeftButtonDrawable.setBounds(0, 0, mLeftButtonDrawable.getIntrinsicWidth(), mLeftButtonDrawable.getIntrinsicHeight());
        mRightButtonDrawable.setBounds(0, 0, mRightButtonDrawable.getIntrinsicWidth(), mRightButtonDrawable.getIntrinsicHeight());
        setCompoundDrawables(mLeftButtonDrawable, null, mRightButtonDrawable, null);
//        ViewGroup viewGroup = (ViewGroup) getParent();    //得不到parent
//        mGestures = new CommonGestures(context, viewGroup.getMeasuredWidth(), viewGroup.getMeasuredHeight());
        mGestures = new CommonGestures(context, DensityUtil.getRealScreenWidthOfPx(), DensityUtil.getRealScreenHeightOfPx());
        mGestures.setTouchListener(this, true);
    }

    @Override
    public void onGestureBegin() {
    }

    @Override
    public void onGestureEnd() {
    }

    @Override
    public void onHorizontalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY) {
    }

    @Override
    public void onVerticalSlide(float horizontalSlidePercent, float verticalSlidePercent, float distanceX, float distanceY) {
    }

    @Override
    public void onSingleTap() {
        if(mIsEventAtLeftDrawable){
            if(mIsAutoClearTextWhenClickLeftButton){
                ExtraButtonEditText.this.setText("");
            }
        }

        if(mIsEventAtRightDrawable){
            if(mIsAutoClearTextWhenClickRightButton){
                ExtraButtonEditText.this.setText("");
            }
        }

        if(null != mClickListener){
            int whichButton = mIsEventAtLeftDrawable && !mIsEventAtRightDrawable ? EXTRA_BUTTON_LEFT : !mIsEventAtLeftDrawable && mIsEventAtRightDrawable ? EXTRA_BUTTON_RIGHT : EXTRA_BUTTON_NONE;
            mClickListener.onExtraButtonClick(this, whichButton);
        }

        mIsEventAtLeftDrawable = false;
        mIsEventAtRightDrawable = false;
    }

    @Override
    public void onDoubleTap() {
    }

    @Override
    public void onScale(float scaleFactor, int state, ScaleGestureDetector detector) {
    }

    @Override
    public void onLongPress() {
    }
}
