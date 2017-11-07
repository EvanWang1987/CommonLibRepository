package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/7.
 */
public class PasswordEditText extends LinearLayout {
    private LimitEditText mLimitEditText;
    private ImageButton mBtnClear;
    private ImageView mImgLeftIcon;
    private CheckBox mBtnDisplayPwd;
    private boolean mIsShowClearButton = true;
    private boolean mIsShowDisplayPwdButton = true;
    private boolean mIsShowLeftIcon = true;
    private Drawable mClearButtonDrawable, mDisplayPwdDrawable, mLeftIcon;

    public PasswordEditText(Context context) {
        this(context, null, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr){
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setWeightSum(3);
        this.setGravity(Gravity.CENTER_VERTICAL);
        mLimitEditText = new LimitEditText(context);
        mLimitEditText.setMaxLines(1);
        mLimitEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mLimitEditText.setIsLimitInputCount(false);
        mBtnClear = new ImageButton(context);
        mBtnDisplayPwd = new CheckBox(context);
        mBtnDisplayPwd.setBackgroundColor(ResourceUtil.getColor(R.color.alpha));
        mBtnDisplayPwd.setText("");
        mImgLeftIcon = new ImageView(context);
        if(null != attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
            mIsShowClearButton = typedArray.getBoolean(R.styleable.PasswordEditText_isShowClearButton, mIsShowClearButton);
            mIsShowDisplayPwdButton = typedArray.getBoolean(R.styleable.PasswordEditText_isShowDisplayPwdButton, mIsShowDisplayPwdButton);
            mIsShowLeftIcon = typedArray.getBoolean(R.styleable.PasswordEditText_leftIcon, mIsShowLeftIcon);
            mClearButtonDrawable = typedArray.getDrawable(R.styleable.PasswordEditText_clearButtonDrawable);
            mDisplayPwdDrawable = typedArray.getDrawable(R.styleable.PasswordEditText_displayPwdButtonDrawable);
            mLeftIcon = typedArray.getDrawable(R.styleable.PasswordEditText_leftIcon);
            typedArray.recycle();
        }
        mClearButtonDrawable = null == mClearButtonDrawable ? ResourceUtil.getDrawable(R.drawable.selector_button_clear) : mClearButtonDrawable;
        mDisplayPwdDrawable = null == mDisplayPwdDrawable ? ResourceUtil.getDrawable(R.drawable.selector_password_visibility) : mDisplayPwdDrawable;
        mLeftIcon = null == mLeftIcon ? ResourceUtil.getDrawable(R.mipmap.ic_account_dark) : mDisplayPwdDrawable;
        mBtnClear.setImageDrawable(mClearButtonDrawable);
        mBtnDisplayPwd.setButtonDrawable(mDisplayPwdDrawable);

        LinearLayout.LayoutParams leftIconParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams clearButtonParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams displayPwdParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(mImgLeftIcon, leftIconParams);
        this.addView(mLimitEditText, editTextParams);
        this.addView(mBtnClear, clearButtonParams);
        this.addView(mBtnDisplayPwd, displayPwdParams);
        mImgLeftIcon.setVisibility(mIsShowLeftIcon ? VISIBLE : GONE);
        mBtnClear.setVisibility(mIsShowClearButton ? VISIBLE : GONE);
        mBtnDisplayPwd.setVisibility(mIsShowDisplayPwdButton ? VISIBLE : GONE);
    }

    public boolean isShowClearButton() {
        return mIsShowClearButton;
    }

    public void setIsShowClearButton(boolean isShowClearButton) {
        this.mIsShowClearButton = isShowClearButton;
    }

    public boolean isShowDisplayPwdButton() {
        return mIsShowDisplayPwdButton;
    }

    public void setIsShowDisplayPwdButton(boolean isShowDisplayPwdButton) {
        this.mIsShowDisplayPwdButton = isShowDisplayPwdButton;
    }

    public boolean isShowLeftIcon() {
        return mIsShowLeftIcon;
    }

    public void setIsShowLeftIcon(boolean isShowLeftIcon) {
        this.mIsShowLeftIcon = isShowLeftIcon;
    }

    public Drawable getClearButtonDrawable() {
        return mClearButtonDrawable;
    }

    public void setClearButtonDrawable(Drawable clearButtonDrawable) {
        this.mClearButtonDrawable = clearButtonDrawable;
    }

    public Drawable getDisplayPwdDrawable() {
        return mDisplayPwdDrawable;
    }

    public void setDisplayPwdDrawable(Drawable displayPwdDrawable) {
        this.mDisplayPwdDrawable = displayPwdDrawable;
    }

    public Drawable getLeftIcon() {
        return mLeftIcon;
    }

    public void setLeftIcon(Drawable leftIcon) {
        this.mLeftIcon = leftIcon;
    }

    public CharSequence getText(){
        return mLimitEditText.getText();
    }

    public void setHint(CharSequence hint){
        mLimitEditText.setHint(hint);
    }

    public void setHint(@StringRes int resId){
        mLimitEditText.setHint(resId);
    }




}
