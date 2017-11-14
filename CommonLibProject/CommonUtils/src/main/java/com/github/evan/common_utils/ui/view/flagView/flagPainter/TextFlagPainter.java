package com.github.evan.common_utils.ui.view.flagView.flagPainter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.view.View;
import com.github.evan.common_utils.ui.view.flagView.FlagLocation;

/**
 * Created by Evan on 2017/11/10.
 */
public class TextFlagPainter implements IFlagPainter<Drawable, String> {
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textSize;
    private int drawablePadding;

    public TextFlagPainter(@ColorInt int textColor, int textSize, int drawablePadding) {
        if(textSize <= 0 || drawablePadding <= 0){
            throw new IllegalArgumentException("TextSize and DrawablePadding can not be less than zero!");
        }

        this.textSize = textSize;
        this.drawablePadding = drawablePadding;
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    public void draw(View view, Canvas canvas, FlagLocation location, Drawable drawable, String text) {
        final float textWidth = textSize;
        final float textHeight = mTextPaint.getTextSize();
        final int measuredHeight = view.getMeasuredHeight();
        final int measuredWidth = view.getMeasuredWidth();

        final int drawableHeight = (int) (mTextPaint.getTextSize() + drawablePadding * 2);
        final int drawableWidth = (int) (textWidth + drawablePadding * 2);

        int drawableLeft, drawableTop, drawableRight, drawableBottom;
        int textLeft, textTop, textRight, textBottom;
//        int textLeft = (int) (drawableWidth / 2 - textWidth / 2);
//        int textTop = (int) (drawableHeight / 2 - textHeight / 2);
//        int textRight = (int) (drawableWidth / 2 + textWidth / 2);
//        int textBottom = (int) (drawableHeight / 2 + textHeight / 2);

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

        textLeft = drawableLeft + (int) (drawableWidth / 2 - textWidth / 2);
        textTop = drawableTop + (int) (drawableHeight / 2 - textHeight / 2);
        textRight = drawableRight + (int) (textLeft + textWidth);
        textBottom = drawableBottom + (int) (measuredHeight - drawableHeight / 2 - textHeight / 2);
        drawable.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        drawable.draw(canvas);
        canvas.drawText(text, textLeft, textTop, mTextPaint);
    }
}
