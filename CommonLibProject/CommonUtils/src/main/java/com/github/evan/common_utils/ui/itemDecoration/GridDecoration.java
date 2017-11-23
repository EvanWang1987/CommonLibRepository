package com.github.evan.common_utils.ui.itemDecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Evan on 2017/11/23.
 */
public class GridDecoration extends BaseItemDecoration {
    private boolean mIsShowColumnDecoration = true;
    private boolean mIsShowRowDecoration = true;

    public GridDecoration(@ColorRes int decorationColor, int decorationSpace) {
        super(decorationColor, decorationSpace);
    }

    public GridDecoration(Drawable decorationDrawable, int decorationSpace) {
        super(decorationDrawable, decorationSpace);
    }

    public boolean isShowColumnDecoration() {
        return mIsShowColumnDecoration;
    }

    public void setShowColumnDecoration(boolean isShowColumnDecoration) {
        this.mIsShowColumnDecoration = isShowColumnDecoration;
    }

    public boolean isShowRowDecoration() {
        return mIsShowRowDecoration;
    }

    public void setShowRowDecoration(boolean isShowRowDecoration) {
        this.mIsShowRowDecoration = isShowRowDecoration;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (isGridLayoutManager(parent)) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            int childCount = parent.getAdapter().getItemCount();                    //孩子数
            int spanCount = gridLayoutManager.getSpanCount();           //列数
            int lineCount = (int) Math.ceil((double) childCount / (double) spanCount);    //行数
            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                int indexOfChild = parent.indexOfChild(view);               //孩子索引
                int childAtLine = indexOfChild / spanCount + 1;             //孩子所在行数
                int maxIndexAtLine = childAtLine * spanCount - 1;           //孩子所在这行最大索引
                boolean isFirstLineChild = indexOfChild < spanCount;
                boolean isLastLineChild = indexOfChild > spanCount * (lineCount - 1) - 1;
                boolean isFirstColumnChild = indexOfChild % spanCount == 0;
                boolean isLastColumnChild = indexOfChild == maxIndexAtLine;

                int itemLeftAtParent = view.getLeft();
                int itemTopAtParent = view.getTop();
                int itemRightAtParent = view.getRight();
                int itemBottomAtParent = view.getBottom();

                if(mIsShowColumnDecoration){
                    int verticalDecorationLeft = itemRightAtParent;
                    int verticalDecorationTop = itemTopAtParent;
                    int verticalDecorationRight = isLastColumnChild ? 0 : itemRightAtParent + mDecorationSpace;
                    int verticalDecorationBottom = itemBottomAtParent;

                    mDecorationDrawable.setBounds(verticalDecorationLeft, verticalDecorationTop, verticalDecorationRight, verticalDecorationBottom);
                    mDecorationDrawable.draw(c);
                }

                if(mIsShowRowDecoration){
                    int horizontalDecorationLeft = itemLeftAtParent;
                    int horizontalDecorationTop = itemBottomAtParent;
                    int horizontalDecorationRight = itemRightAtParent + mDecorationSpace;   //多加一个space,为了和竖向间距交汇
                    int horizontalDecorationBottom = isLastLineChild ? itemBottomAtParent : itemBottomAtParent + mDecorationSpace;

                    mDecorationDrawable.setBounds(horizontalDecorationLeft, horizontalDecorationTop, horizontalDecorationRight, horizontalDecorationBottom);
                    mDecorationDrawable.draw(c);
                }
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isGridLayoutManager(parent)) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            int childCount = parent.getAdapter().getItemCount();                    //孩子数
            int spanCount = gridLayoutManager.getSpanCount();           //列数
            int lineCount = (int) Math.ceil((double) childCount / (double) spanCount);    //行数
            int indexOfChild = parent.indexOfChild(view);               //孩子索引
            int childAtLine = indexOfChild / spanCount + 1;             //孩子所在行数
            int maxIndexAtLine = childAtLine * spanCount - 1;           //孩子所在这行最大索引
            boolean isFirstLineChild = indexOfChild < spanCount;
            boolean isLastLineChild = indexOfChild > spanCount * (lineCount - 1) - 1;
            boolean isFirstColumnChild = indexOfChild % spanCount == 0;
            boolean isLastColumnChild = indexOfChild == maxIndexAtLine;

            int left = 0;
            int top = 0;
            int right = mIsShowColumnDecoration ? isLastColumnChild ? 0 : mDecorationSpace : 0;
            int bottom = mIsShowRowDecoration ? isLastLineChild ? 0 : mDecorationSpace : 0;
            outRect.set(left, top, right, bottom);
        }
    }
}
