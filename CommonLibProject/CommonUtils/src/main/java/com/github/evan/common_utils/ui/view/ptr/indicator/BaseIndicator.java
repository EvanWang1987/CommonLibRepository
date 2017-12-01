package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.evan.common_utils.ui.view.ptr.PtrStatus;

/**
 * Created by Evan on 2017/11/30.
 */
public abstract class BaseIndicator extends LinearLayout implements IIndicator {
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);
    public abstract void onPtrStatusChange(PtrStatus status);
    public abstract void onPullDownOffsetChange(int offsetYFromDown, int offsetYFromLastMoved);

    protected PtrStatus mPtrStatus = PtrStatus.IDLE;
    private LayoutInflater mLayoutInflater;

    public BaseIndicator(Context context) {
        super(context);
        init();
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mLayoutInflater = LayoutInflater.from(getContext());
        View subRoot = onCreateView(mLayoutInflater, this);
        if(subRoot.getParent() == null){
            this.addView(subRoot, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public final View getIndicatorView() {
        return this;
    }

    @Override
    public final void setStatus(PtrStatus status) {
        mPtrStatus = status;
        onPtrStatusChange(status);
    }

    @Override
    public final PtrStatus getStatus() {
        return mPtrStatus;
    }

    @Override
    public final void setOffsetY(int offsetFromDownPoint, int offsetFromLastMoved) {
        onPullDownOffsetChange(offsetFromDownPoint, offsetFromLastMoved);
    }
}
