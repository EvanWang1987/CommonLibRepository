package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.github.evan.common_utils.ui.view.ptr.PtrStatus;

/**
 * Created by Evan on 2017/11/29.
 */
public interface IIndicator {

    public View getIndicatorView();

    public void setStatus(PtrStatus status);

    public PtrStatus getStatus();

    public void setOffsetY(int offsetFromDownPoint, int offsetFromLastMoved);

    public void setUpdateTime();

    public void initAttributes(Context context, AttributeSet attrs, int[] styleable);

}
