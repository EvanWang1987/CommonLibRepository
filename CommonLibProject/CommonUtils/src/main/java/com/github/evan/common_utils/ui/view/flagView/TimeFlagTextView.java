package com.github.evan.common_utils.ui.view.flagView;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.flagView.flagPainter.DrawableFlagPainter;
import com.github.evan.common_utils.ui.view.flagView.flagUpdater.TimeFlagUpdater;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils.utils.StringUtil;

/**
 * Created by Evan on 2017/11/10.
 */
public class TimeFlagTextView extends AppCompatTextView implements IFlagView<Long>{
    private String mFlagName;
    private TimeFlagUpdater mUpdater;
    private DrawableFlagPainter mFlagPainter;
    private Drawable mFlagDrawable;
    private FlagLocation mFlagLocation = FlagLocation.RIGHT_TOP;
    private boolean mIsFlagChanged = false;

    public TimeFlagTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimeFlagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimeFlagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        if(null != attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeFlagTextView);
            mFlagName = typedArray.getString(R.styleable.TimeFlagTextView_flagName);
            mFlagDrawable = typedArray.getDrawable(R.styleable.TimeFlagTextView_flagDrawable);
            int defaultValue = FlagLocation.RIGHT_TOP.value;
            int locationValue = typedArray.getInt(R.styleable.TimeFlagTextView_flagLocation, defaultValue);
            mFlagLocation = FlagLocation.valueOf(locationValue);
            typedArray.recycle();
        }

        if(StringUtil.isEmptyString(mFlagName, true)){
            mFlagName = "Activity: " + getContext().getClass().getName() + " / " + this.getClass().getName() + " / id: " + getId() + " / currentTime: " + System.currentTimeMillis();
        }
        mFlagDrawable = null == mFlagDrawable ? ResourceUtil.getDrawable(R.drawable.shape_red_point) : mFlagDrawable;
        mUpdater = new TimeFlagUpdater(mFlagName);
        mFlagPainter = new DrawableFlagPainter();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIsFlagChanged){
            mFlagPainter.draw(this, canvas, mFlagLocation, mFlagDrawable, null);
        }
    }

    @Override
    public void updateFlagValue(Long flag) {
        mIsFlagChanged = mUpdater.isFlagChange(flag, true);
        postInvalidate();
    }

    @Override
    public Long getLastFlagValue() {
        return mUpdater.getFlag();
    }

    @Override
    public void showFlag() {
        mIsFlagChanged = true;
        postInvalidate();
    }

    @Override
    public void dismissFlag() {
        mIsFlagChanged = false;
        postInvalidate();
    }

    @Override
    public boolean isFlagShowing() {
        return mIsFlagChanged;
    }

    @Override
    public void setFlagLocation(FlagLocation location) {
        mFlagLocation = location;
    }

    @Override
    public FlagLocation getFlagLocation() {
        return mFlagLocation;
    }

    @Override
    public void setFlagDrawable(Drawable drawable) {
        mFlagDrawable = drawable;
    }

    @Override
    public void setFlagDrawable(@DrawableRes int resId) {
        mFlagDrawable = ResourceUtil.getDrawable(resId);
    }

    @Override
    public Drawable getFlagDrawable() {
        return mFlagDrawable;
    }

    @Override
    public void setFlagName() {
    }

    @Override
    public String getFlagName() {
        return null;
    }
}
