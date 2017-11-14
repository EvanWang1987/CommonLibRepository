package com.github.evan.common_utils.ui.view.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/7.
 */
public class LoginEditText extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    private ExtraButtonEditText mEditText;
    private ImageView mImgLeftIcon;
    private CheckBox mBtnDisplayPwd;
    private boolean mIsShowClearButton = true;
    private boolean mIsShowDisplayPwdButton = true;
    private boolean mIsShowLeftIcon = true;
    private Drawable mClearButtonDrawable, mDisplayPwdDrawable, mLeftIcon;
    private CompoundButton.OnCheckedChangeListener mCheckedListener;
    private OnClickListener mClickListener;



    public LoginEditText(Context context) {
        this(context, null, 0);
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr){
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setWeightSum(1);
        this.setGravity(Gravity.CENTER_VERTICAL);
        mEditText = new ExtraButtonEditText(context);
        mEditText.setMaxLines(1);
        mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEditText.setLimitInputCount(false);
        mEditText.setShowRightButton(true);
        mEditText.setAutoClearTextWhenClickRightButton(true);
        mEditText.setRightButtonDrawable(ResourceUtil.getDrawable(R.drawable.selector_button_clear));
        mBtnDisplayPwd = new CheckBox(context);
        mBtnDisplayPwd.setBackgroundColor(ResourceUtil.getColor(R.color.Alpha));
        mBtnDisplayPwd.setText("");
        mImgLeftIcon = new ImageView(context);
        mBtnDisplayPwd.setOnCheckedChangeListener(this);
        if(null != attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
            mIsShowClearButton = typedArray.getBoolean(R.styleable.LoginEditText_isShowClearButton, mIsShowClearButton);
            mIsShowDisplayPwdButton = typedArray.getBoolean(R.styleable.LoginEditText_isShowDisplayPwdButton, mIsShowDisplayPwdButton);
            mIsShowLeftIcon = typedArray.getBoolean(R.styleable.LoginEditText_isShowLeftIcon, mIsShowLeftIcon);
            mClearButtonDrawable = typedArray.getDrawable(R.styleable.LoginEditText_clearButtonDrawable);
            mDisplayPwdDrawable = typedArray.getDrawable(R.styleable.LoginEditText_displayPwdButtonDrawable);
            mLeftIcon = typedArray.getDrawable(R.styleable.LoginEditText_leftIcon);
            typedArray.recycle();
        }
        mClearButtonDrawable = null == mClearButtonDrawable ? ResourceUtil.getDrawable(R.drawable.selector_button_clear) : mClearButtonDrawable;
        mDisplayPwdDrawable = null == mDisplayPwdDrawable ? ResourceUtil.getDrawable(R.drawable.selector_password_visibility) : mDisplayPwdDrawable;
        mLeftIcon = null == mLeftIcon ? ResourceUtil.getDrawable(R.mipmap.ic_account_dark) : mLeftIcon;
        mBtnDisplayPwd.setButtonDrawable(mDisplayPwdDrawable);
        mBtnDisplayPwd.setBackgroundColor(ResourceUtil.getColor(R.color.Alpha));
        mImgLeftIcon.setImageDrawable(mLeftIcon);

        LayoutParams leftIconParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams editTextParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        LayoutParams displayPwdParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(mImgLeftIcon, leftIconParams);
        this.addView(mEditText, editTextParams);
        this.addView(mBtnDisplayPwd, displayPwdParams);
        mImgLeftIcon.setVisibility(mIsShowLeftIcon ? VISIBLE : GONE);
        mBtnDisplayPwd.setVisibility(mIsShowDisplayPwdButton ? VISIBLE : GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        mEditText.setText(text);
    }

    public void setText(@StringRes int  resId){
        mEditText.setText(resId);
    }

    public CharSequence getText(){
        return mEditText.getText();
    }

    public void setHint(CharSequence hint){
        mEditText.setHint(hint);
    }

    public void setHint(@StringRes int resId){
        mEditText.setHint(resId);
    }

    public void setLines(int line){
        mEditText.setLines(line);
    }

    public void setMaxLine(int maxLine){
        mEditText.setMaxLines(maxLine);
    }

    public boolean isDisplayPassword(){
        return mBtnDisplayPwd.isChecked();
    }

    public void setDisplayPassword(boolean isChecked){
        mBtnDisplayPwd.setChecked(isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(null != mCheckedListener){
            mCheckedListener.onCheckedChanged(buttonView, isChecked);
        }
    }
}
