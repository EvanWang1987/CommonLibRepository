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
public class ListDecoration extends BaseItemDecoration {


    public ListDecoration(@ColorRes int decorationColor, int decorationSpace) {
        super(decorationColor, decorationSpace);
    }

    public ListDecoration(Drawable decorationDrawable, int decorationSpace) {
        super(decorationDrawable, decorationSpace);
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
            if(isVerticalLayoutManager(parent)){
                top = itemBottomAtParent;
                left = itemLeftAtParent;
                right = itemRightAtParent;
                bottom = isLastItemView ? 0 : itemBottomAtParent + mDecorationSpace;
            }
            else if(isHorizontalLayoutManager(parent)){
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
                outRect.set(0, 0, 0, mDecorationSpace);
            }
            else if(linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL){
                outRect.set(0, 0, mDecorationSpace, 0);
            }
        }
    }
}
