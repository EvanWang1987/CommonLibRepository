package com.github.evan.common_utils.ui.view.flagView.flagPainter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.github.evan.common_utils.ui.view.flagView.FlagLocation;

/**
 * Created by Evan on 2017/11/10.
 */
public class DrawableFlagPainter implements IFlagPainter<Drawable,Void> {

    public DrawableFlagPainter() {
    }

    @Override
    public void draw(View view, Canvas canvas, FlagLocation location, Drawable drawable, Void aVoid) {
        final int measuredHeight = view.getMeasuredHeight();
        final int measuredWidth = view.getMeasuredWidth();

        final int drawableHeight = drawable.getMinimumHeight();
        final int drawableWidth = drawable.getMinimumWidth();

        int drawableLeft, drawableTop, drawableRight, drawableBottom;

        switch (location){
            case LEFT_TOP:
                drawableLeft = 0;
                drawableRight = drawableWidth;
                drawableTop = 0;
                drawableBottom = drawableHeight;
                break;

            case LEFT_BOTTOM:
                drawableLeft = 0;
                drawableRight = drawableWidth;
                drawableTop = measuredHeight - drawableHeight;
                drawableBottom = measuredHeight;
                break;

            case RIGHT_BOTTOM:
                drawableLeft = measuredWidth - drawableWidth;
                drawableRight = measuredWidth;
                drawableTop = measuredHeight - drawableHeight;
                drawableBottom = measuredHeight;
                break;

            default:
            case UNKNOWN:
            case RIGHT_TOP:
                drawableLeft = measuredWidth - drawableWidth;
                drawableRight = measuredWidth;
                drawableTop = 0;
                drawableBottom = drawableHeight;
                break;
        }

        drawable.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        drawable.draw(canvas);
    }

}
