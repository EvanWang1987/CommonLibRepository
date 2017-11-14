package com.github.evan.common_utils.ui.view.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Toast;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/11/7.
 */
public class LimitEditText extends AppCompatEditText implements TextWatcher {
    private List<TextWatcher> mOuterWatchers;
    private boolean mIsLimitInputCount = false;
    private int mLimitInputCount = 48;
    private boolean mIsShowInputTotalCount = false;
    private boolean mIsToastIfOverRangingLimitCount = false;
    private String mToastText;
    private @ColorInt int mTotalInputCountTextColor = 0;
    private Paint mShowInputtedCountPaint;

    public LimitEditText(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitEditText);
            mIsLimitInputCount = typedArray.getBoolean(R.styleable.LimitEditText_isLimitInputCount, mIsLimitInputCount);
            mLimitInputCount = typedArray.getInt(R.styleable.LimitEditText_limitCount, mLimitInputCount);
            mIsShowInputTotalCount = typedArray.getBoolean(R.styleable.LimitEditText_isShowTotalInputCount, mIsShowInputTotalCount);
            mIsToastIfOverRangingLimitCount = typedArray.getBoolean(R.styleable.LimitEditText_isToastIfOverRangingLimitCount, mIsToastIfOverRangingLimitCount);
            mToastText = typedArray.getString(R.styleable.LimitEditText_toastText);
            mToastText = StringUtil.isEmptyString(mToastText, true) ? ResourceUtil.getString(R.string.limit_text_notify_text_when_over_rang_limit_count, mLimitInputCount) : mToastText;
            mTotalInputCountTextColor = typedArray.getColor(R.styleable.LimitEditText_totalInputCountTextColor, ResourceUtil.getColor(R.color.DarkGray));
            typedArray.recycle();
        }
        mShowInputtedCountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShowInputtedCountPaint.setColor(mTotalInputCountTextColor);
        super.addTextChangedListener(this);
    }

    @Override
    public final void addTextChangedListener(TextWatcher watcher) {
        if (null == mOuterWatchers) {
            mOuterWatchers = new ArrayList<>();
        }

        if (!mOuterWatchers.contains(watcher)) {
            mOuterWatchers.add(watcher);
        }
    }

    @Override
    public final void removeTextChangedListener(TextWatcher watcher) {
        if (null != mOuterWatchers && mOuterWatchers.contains(watcher)) {
            mOuterWatchers.remove(watcher);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsLimitInputCount) {
            if (mIsShowInputTotalCount) {
                final int measuredWidth = getMeasuredWidth();
                final int measuredHeight = getMeasuredHeight();
                final int paddingLeft = getPaddingLeft();
                final int paddingRight = getPaddingRight();
                final int paddingTop = getPaddingTop();
                final int inputTextLength = getText().toString().length();
                final int totalInputLength = mLimitInputCount;
                final String displayString = inputTextLength + " / " + totalInputLength;
                mShowInputtedCountPaint.setColor(mTotalInputCountTextColor);
                mShowInputtedCountPaint.setTextSize(this.getTextSize());
                final float textWidth = mShowInputtedCountPaint.measureText(displayString);
                final float x = measuredWidth - textWidth - paddingLeft - paddingRight;
                final float y = measuredHeight - this.getTextSize() + paddingTop / 2;
                canvas.drawText(displayString, x, y, mShowInputtedCountPaint);
            }
        }
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Logger.d("beforeTextChanged");
        if (mIsLimitInputCount) {
            if (start + after > mLimitInputCount) {
                if (mIsToastIfOverRangingLimitCount) {
                    Toast.makeText(getContext(), mToastText, Toast.LENGTH_SHORT).show();
                }
                this.setText(s);
                this.setSelection(s.length());
                invalidate();
                return;
            }
        }
        if (null != mOuterWatchers) {
            int size = mOuterWatchers.size();
            for (int i = 0; i < size; i++) {
                TextWatcher textWatcher = mOuterWatchers.get(i);
                textWatcher.beforeTextChanged(s, start, count, after);
            }
        }
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        Logger.d("onTextChanged");
        if (null != mOuterWatchers) {
            int size = mOuterWatchers.size();
            for (int i = 0; i < size; i++) {
                TextWatcher textWatcher = mOuterWatchers.get(i);
                textWatcher.onTextChanged(s, start, before, count);
            }
        }
    }

    @Override
    public final void afterTextChanged(Editable s) {
        Logger.d("afterTextChanged");
        if (null != mOuterWatchers) {
            int size = mOuterWatchers.size();
            for (int i = 0; i < size; i++) {
                TextWatcher textWatcher = mOuterWatchers.get(i);
                textWatcher.afterTextChanged(s);
            }
        }
    }

    public boolean isLimitInputCount() {
        return mIsLimitInputCount;
    }

    public void setLimitInputCount(boolean isLimitInputCount) {
        this.mIsLimitInputCount = isLimitInputCount;
        postInvalidate();
    }

    public int getLimitInputCount() {
        return mLimitInputCount;
    }

    public void setLimitInputCount(int limitInputCount) {
        this.mLimitInputCount = limitInputCount;
        postInvalidate();
    }

    public boolean isShowInputTotalCount() {
        return mIsShowInputTotalCount;
    }

    public void setShowInputTotalCount(boolean isShowInputTotalCount) {
        this.mIsShowInputTotalCount = isShowInputTotalCount;
        postInvalidate();
    }

    public boolean isToastIfOverRangingLimitCount() {
        return mIsToastIfOverRangingLimitCount;
    }

    public void setToastIfOverRangingLimitCount(boolean isToastIfOverRangingLimitCount) {
        this.mIsToastIfOverRangingLimitCount = isToastIfOverRangingLimitCount;
    }

    public String getToastText() {
        return mToastText;
    }

    public void setToastText(String toastText) {
        this.mToastText = toastText;
    }

    public int getTotalInputCountTextColor() {
        return mTotalInputCountTextColor;
    }

    public void setTotalInputCountTextColor(@ColorRes int totalInputCountTextColor) {
        this.mTotalInputCountTextColor = ResourceUtil.getColor(totalInputCountTextColor);
        postInvalidate();
    }

    public Paint getShowInputtedCountPaint() {
        return mShowInputtedCountPaint;
    }

    public void setShowInputtedCountPaint(Paint showInputtedCountPaint) {
        this.mShowInputtedCountPaint = showInputtedCountPaint;
    }
}
