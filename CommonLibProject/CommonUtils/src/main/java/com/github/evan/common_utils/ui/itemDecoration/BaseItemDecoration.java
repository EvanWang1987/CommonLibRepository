package com.github.evan.common_utils.ui.itemDecoration;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/23.
 */

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration{
    protected Drawable mDecorationDrawable;
    protected int mDecorationSpace;

    public BaseItemDecoration(@ColorRes int decorationColor, int decorationSpace){
        this(new ColorDrawable(ResourceUtil.getColor(decorationColor)), decorationSpace);
    }

    public BaseItemDecoration(Drawable decorationDrawable, int decorationSpace) {
        this.mDecorationDrawable = decorationDrawable;
        this.mDecorationSpace = decorationSpace;
        if(mDecorationSpace < 0){
            int intrinsicHeight = mDecorationDrawable.getIntrinsicHeight();
            mDecorationSpace = intrinsicHeight;
            if(intrinsicHeight < 0){
                mDecorationSpace = DensityUtil.dp2px(5);
            }
        }
    }

    public Drawable getDecorationDrawable() {
        return mDecorationDrawable;
    }

    public void setDecorationDrawable(Drawable decorationDrawable) {
        this.mDecorationDrawable = decorationDrawable;
    }

    public int getDecorationSpace() {
        return mDecorationSpace;
    }

    public void setDecorationSpace(int decorationSpace) {
        this.mDecorationSpace = decorationSpace;
    }

    protected boolean isVerticalLayoutManager(RecyclerView recyclerView){
        if(isLinearLayoutManager(recyclerView)){
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            return layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
        }
        return false;
    }

    protected boolean isHorizontalLayoutManager(RecyclerView recyclerView){
        if(isLinearLayoutManager(recyclerView)){
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            return layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL;
        }
        return false;
    }

    protected boolean isLinearLayoutManager(RecyclerView recyclerView){
        return null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof LinearLayoutManager;
    }

    protected boolean isGridLayoutManager(RecyclerView recyclerView){
        return null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof GridLayoutManager;
    }

    protected boolean isStaggredLayoutManager(RecyclerView recyclerView){
        return null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager;
    }

}
