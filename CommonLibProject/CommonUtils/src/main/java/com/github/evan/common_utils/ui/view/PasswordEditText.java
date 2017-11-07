package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/7.
 */
public class PasswordEditText extends LinearLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private LimitEditText mLimitEditText;
    private ImageButton mBtnClear;
    private ImageView mImgLeftIcon;
    private CheckBox mBtnDisplayPwd;
    private boolean mIsShowClearButton = true;
    private boolean mIsShowDisplayPwdButton = true;
    private boolean mIsShowLeftIcon = true;
    private Drawable mClearButtonDrawable, mDisplayPwdDrawable, mLeftIcon;
    private CompoundButton.OnCheckedChangeListener mCheckedListener;
    private OnClickListener mClickListener;



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
        mBtnClear.setOnClickListener(this);
        mBtnDisplayPwd.setOnCheckedChangeListener(this);
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

    public CompoundButton.OnCheckedChangeListener getCheckedListener() {
        return mCheckedListener;
    }

    public void setCheckedListener(CompoundButton.OnCheckedChangeListener checkedListener) {
        this.mCheckedListener = checkedListener;
    }

    public OnClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public boolean isShowClearButton() {
        return mIsShowClearButton;
    }

    public void setIsShowClearButton(boolean isShowClearButton) {
        this.mIsShowClearButton = isShowClearButton;
        mBtnClear.setVisibility(isShowClearButton ? VISIBLE : GONE);
    }

    public boolean isShowDisplayPwdButton() {
        return mIsShowDisplayPwdButton;
    }

    public void setIsShowDisplayPwdButton(boolean isShowDisplayPwdButton) {
        this.mIsShowDisplayPwdButton = isShowDisplayPwdButton;
        mBtnDisplayPwd.setVisibility(isShowDisplayPwdButton ? VISIBLE : GONE);
    }

    public boolean isShowLeftIcon() {
        return mIsShowLeftIcon;
    }

    public void setIsShowLeftIcon(boolean isShowLeftIcon) {
        this.mIsShowLeftIcon = isShowLeftIcon;
        mImgLeftIcon.setVisibility(isShowLeftIcon ? VISIBLE : GONE);
    }

    public Drawable getClearButtonDrawable() {
        return mClearButtonDrawable;
    }

    public void setClearButtonDrawable(Drawable clearButtonDrawable) {
        this.mClearButtonDrawable = clearButtonDrawable;
        mBtnClear.setImageDrawable(clearButtonDrawable);
    }

    public Drawable getDisplayPwdDrawable() {
        return mDisplayPwdDrawable;
    }

    public void setDisplayPwdDrawable(Drawable displayPwdDrawable) {
        this.mDisplayPwdDrawable = displayPwdDrawable;
        mBtnDisplayPwd.setButtonDrawable(displayPwdDrawable);
    }

    public Drawable getLeftIcon() {
        return mLeftIcon;
    }

    public void setLeftIcon(Drawable leftIcon) {
        this.mLeftIcon = leftIcon;
        mImgLeftIcon.setImageDrawable(leftIcon);
    }

    public void setText(CharSequence text){
        mLimitEditText.setText(text);
    }

    public void setText(@StringRes int  resId){
        mLimitEditText.setText(resId);
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

    public void setLines(int line){
        mLimitEditText.setLines(line);
    }

    public void setMaxLine(int maxLine){
        mLimitEditText.setMaxLines(maxLine);
    }

    public boolean isDisplayPassword(){
        return mBtnDisplayPwd.isChecked();
    }

    public void setDisplayPassword(boolean isChecked){
        mBtnDisplayPwd.setChecked(isChecked);
    }

    @Override
    public void onClick(View v) {
        if(null != mClickListener){
            mClickListener.onClick(v);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(null != mCheckedListener){
            mCheckedListener.onCheckedChanged(buttonView, isChecked);
        }
    }
}
