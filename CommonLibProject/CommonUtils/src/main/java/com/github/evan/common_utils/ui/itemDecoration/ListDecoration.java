package com.github.evan.common_utils.ui.itemDecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/22.
 */
public class ListDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDecorationDrawable;
    private int mDecorationSpace;
    private RectF mRect = new RectF();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ListDecoration(@ColorRes int decorationColor, int decorationSpace){
        this(new ColorDrawable(ResourceUtil.getColor(decorationColor)), decorationSpace);
    }

    public ListDecoration(Drawable decorationDrawable, int decorationSpace) {
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

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            boolean isLastItemView = i == childCount - 1;
            View itemView = parent.getChildAt(i);
            int itemTopAtParent = itemView.getTop();
            int itemBottomAtParent = itemView.getBottom();
            int itemLeftAtParent = itemView.getLeft();
            int itemRightAtParent = itemView.getRight();

            int top = 0;
            int left = 0;
            int right = 0;
            int bottom = 0;
            if(isVerticalLinearLayoutManager(parent)){
                top = itemBottomAtParent;
                left = itemLeftAtParent;
                right = itemRightAtParent;
                bottom = isLastItemView ? 0 : itemBottomAtParent + mDecorationSpace;
            }
            else if(isHorizontalLinearLayoutManager(parent)){
                top = itemTopAtParent;
                left = itemRightAtParent;
                right = isLastItemView ? 0 : itemRightAtParent + mDecorationSpace;
                bottom = itemBottomAtParent;
            }
            else {
                return;
            }

            mDecorationDrawable.setBounds(left, top, right, bottom);
            mDecorationDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(null != layoutManager && layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if(linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL){
//                outRect.bottom = mDecorationSpace;
                outRect.set(0, 0, 0, mDecorationSpace);
            }
            else if(linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL){
//                outRect.right = mDecorationSpace;
                outRect.set(0, 0, mDecorationSpace, 0);
            }
        }
    }

    private boolean isLinearLayoutManager(RecyclerView recyclerView){
        return null != recyclerView && null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof LinearLayoutManager;
    }

    private boolean isVerticalLinearLayoutManager(RecyclerView recyclerView){
        if(isLinearLayoutManager(recyclerView)){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            return linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
        }
        return false;
    }

    public boolean isHorizontalLinearLayoutManager(RecyclerView recyclerView){
        if(isLinearLayoutManager(recyclerView)){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            return linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL;
        }

        return false;
    }
}
