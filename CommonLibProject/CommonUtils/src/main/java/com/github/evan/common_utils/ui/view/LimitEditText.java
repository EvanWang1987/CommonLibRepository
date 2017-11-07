package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Toast;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.DensityUtil;
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
    private boolean mIsShowClearButton = false;
    private boolean mIsShowExtraButton = false;
    private Drawable mClearInputButtonDrawable;
    private Drawable mExtraButtonDrawable;
    private boolean mIsShowInputTotalCount = false;
    private boolean mIsToastIfOverRangingLimitCount = false;
    private String mToastText;
    private int mTotalInputCountTextColor = 0;
    private float mTotalInputCountTextSize = 15;
    private Paint mShowInputtedCountPaint;

    public LimitEditText(Context context) {
        this(context, null, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr){

        if(null != attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LimitEditText);
            mIsLimitInputCount = typedArray.getBoolean(R.styleable.LimitEditText_isLimitInputCount, mIsLimitInputCount);
            mLimitInputCount = typedArray.getInt(R.styleable.LimitEditText_limitCount, mLimitInputCount);
            mIsShowClearButton = typedArray.getBoolean(R.styleable.LimitEditText_isShowClearInputButton, mIsShowClearButton);
            mIsShowExtraButton = typedArray.getBoolean(R.styleable.LimitEditText_isShowExtraButton, mIsShowExtraButton);
            mClearInputButtonDrawable = typedArray.getDrawable(R.styleable.LimitEditText_clearInputButtonDrawable);
            mExtraButtonDrawable = typedArray.getDrawable(R.styleable.LimitEditText_extraButtonDrawable);
            mIsShowInputTotalCount = typedArray.getBoolean(R.styleable.LimitEditText_isShowTotalInputCount, mIsShowInputTotalCount);
            mIsToastIfOverRangingLimitCount = typedArray.getBoolean(R.styleable.LimitEditText_isToastIfOverRangingLimitCount, mIsToastIfOverRangingLimitCount);
            mToastText = typedArray.getString(R.styleable.LimitEditText_toastText);
            mToastText = StringUtil.isEmptyString(mToastText, true) ? ResourceUtil.getString(R.string.limit_text_notify_text_when_over_rang_limit_count, mLimitInputCount) : mToastText;
            mTotalInputCountTextColor = typedArray.getColor(R.styleable.LimitEditText_totalInputCountTextColor, ResourceUtil.getColor(R.color.DarkGray));
            mTotalInputCountTextSize = typedArray.getDimension(R.styleable.LimitEditText_totalInputCountTextSize, 16);
            typedArray.recycle();
        }
        mShowInputtedCountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShowInputtedCountPaint.setColor(mTotalInputCountTextColor);
        super.addTextChangedListener(this);
    }

    @Override
    public final void addTextChangedListener(TextWatcher watcher) {
        if(null == mOuterWatchers){
            mOuterWatchers = new ArrayList<>();
        }

        if(!mOuterWatchers.contains(watcher)){
            mOuterWatchers.add(watcher);
        }
    }

    @Override
    public final void removeTextChangedListener(TextWatcher watcher) {
        if(null != mOuterWatchers && mOuterWatchers.contains(watcher)){
            mOuterWatchers.remove(watcher);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsLimitInputCount){
            if(mIsShowInputTotalCount){
                final int measuredWidth = getMeasuredWidth();
                final int measuredHeight = getMeasuredHeight();
                final int paddingTop = getPaddingTop();
                final int paddingBottom = getPaddingBottom();
                final int paddingLeft = getPaddingLeft();
                final int paddingRight = getPaddingRight();
                final int contentWidth = measuredWidth - paddingLeft - paddingRight;
                final int contentHeight = measuredHeight - paddingTop - paddingBottom;
                final int inputTextLength = getText().toString().length();
                final int totalInputLength = mLimitInputCount;
                final String displayString = inputTextLength + " / " + totalInputLength;
                final float textWidth = mShowInputtedCountPaint.measureText(displayString);
                final int fiveDp = DensityUtil.dp2px(5);
                mShowInputtedCountPaint.setTextSize(mTotalInputCountTextSize);
                final float x = contentWidth - textWidth - fiveDp;
                final float y = contentHeight - mTotalInputCountTextSize - fiveDp;
                canvas.drawText(displayString, x, y, mShowInputtedCountPaint);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(mIsLimitInputCount){
            if(after >= mLimitInputCount){
                s = s.subSequence(0, mLimitInputCount + 1);
                this.setText(s);
                if(mIsToastIfOverRangingLimitCount){
                    Toast.makeText(getContext(), mToastText, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

        int size = mOuterWatchers.size();
        for (int i = 0; i < size; i++) {
            TextWatcher textWatcher = mOuterWatchers.get(i);
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int size = mOuterWatchers.size();
        for (int i = 0; i < size; i++) {
            TextWatcher textWatcher = mOuterWatchers.get(i);
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        int size = mOuterWatchers.size();
        for (int i = 0; i < size; i++) {
            TextWatcher textWatcher = mOuterWatchers.get(i);
            textWatcher.afterTextChanged(s);
        }
    }
}
