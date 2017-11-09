package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/8.
 */
public class PasswordEditText extends ExtraButtonEditText implements ExtraButtonEditText.OnExtraButtonClickListener {
    public interface OnPasswordVisibilityCheckedChangeListener {
        void onPasswordVisibilityCheckedChange(PasswordEditText v, boolean isChecked);
    }

    private boolean mIsPwdVisibilityChecked = false;
    private Drawable mPwdVisibilityDrawable;
    private OnPasswordVisibilityCheckedChangeListener mCheckedChangeListener;

    public PasswordEditText(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }


    public boolean isPwdVisibilityChecked() {
        return mIsPwdVisibilityChecked;
    }

    public void setPwdVisibilityChecked(boolean isPwdVisibilityChecked) {
        this.mIsPwdVisibilityChecked = isPwdVisibilityChecked;
    }

    public Drawable getPwdVisibilityDrawable() {
        return mPwdVisibilityDrawable;
    }

    public void setPwdVisibilityDrawable(Drawable pwdVisibilityDrawable) {
        this.mPwdVisibilityDrawable = pwdVisibilityDrawable;
    }

    public OnPasswordVisibilityCheckedChangeListener getCheckedChangeListener() {
        return mCheckedChangeListener;
    }

    public void setPasswordVisibilityCheckedChangeListener(OnPasswordVisibilityCheckedChangeListener l) {
        this.mCheckedChangeListener = l;
    }

    @Override
    public void onExtraButtonClick(View v, int whichButton) {
        if (whichButton == EXTRA_BUTTON_RIGHT) {
            mIsPwdVisibilityChecked = !mIsPwdVisibilityChecked;
            mPwdVisibilityDrawable.setState(new int[]{mIsPwdVisibilityChecked ? android.R.attr.state_checked : android.R.attr.state_checkable});
            setInputType(convertInputType(mIsPwdVisibilityChecked));
            setSelection(getText().toString().length());
            if (null != mCheckedChangeListener) {
                mCheckedChangeListener.onPasswordVisibilityCheckedChange((PasswordEditText) v, mIsPwdVisibilityChecked);
            }
        }
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
            mIsPwdVisibilityChecked = typedArray.getBoolean(R.styleable.PasswordEditText_passwordVisibilityChecked, mIsPwdVisibilityChecked);
            mPwdVisibilityDrawable = typedArray.getDrawable(R.styleable.PasswordEditText_passwordVisibilityDrawable);
            typedArray.recycle();
        }

        mPwdVisibilityDrawable = null == mPwdVisibilityDrawable ? ResourceUtil.getDrawable(R.drawable.selector_password_visibility) : mPwdVisibilityDrawable;
        setMaxLines(1);
        setShowLeftButton(false);
        setShowRightButton(true);
        setAutoClearTextWhenClickLeftButton(false);
        setAutoClearTextWhenClickRightButton(false);
        setRightButtonDrawable(mPwdVisibilityDrawable);
        setExtraButtonClickListener(this);
        setInputType(convertInputType(mIsPwdVisibilityChecked));
    }

    private int convertInputType(boolean isPlain){
        return isPlain ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }

}
