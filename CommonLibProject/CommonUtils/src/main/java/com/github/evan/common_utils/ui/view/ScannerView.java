package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.github.evan.common_utils.R;

/**
 * Created by Evan on 2017/12/17.
 */
public class ScannerView extends View {
    private int mRimColor = Color.parseColor("#00FF00");
    private int mHollowRectangleColor = Color.parseColor("#FF0000");
    private float mRimLong = 30;
    private float mRimWidth = 10;
    private float mHorizontalRimSpace = 100;
    private float mVerticalRimSpace = 100;
    private Rect mHollowRectangle = new Rect();
    private float mHollowRectangleStrokeWidth = 5;
    private Drawable mScannerLine;
    private int mScannerLineTop;
    private Paint mRimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mHollowRectanglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ScannerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ScannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScannerView);
            mRimColor = typedArray.getColor(R.styleable.ScannerView_rim_color, mRimColor);
            mRimLong = typedArray.getDimension(R.styleable.ScannerView_rim_long, mRimLong);
            mRimWidth = typedArray.getDimension(R.styleable.ScannerView_rim_width, mRimWidth);
            mHorizontalRimSpace = typedArray.getDimension(R.styleable.ScannerView_horizontal_rim_space, mHorizontalRimSpace);
            mVerticalRimSpace = typedArray.getDimension(R.styleable.ScannerView_vertical_rim_space, mVerticalRimSpace);
            mHollowRectangleStrokeWidth = typedArray.getDimension(R.styleable.ScannerView_hollow_rectangle_stroke_width, mHollowRectangleStrokeWidth);
            mHollowRectangleColor = typedArray.getColor(R.styleable.ScannerView_hollow_rectangle_stroke_color, mHollowRectangleColor);
            Drawable drawable = typedArray.getDrawable(R.styleable.ScannerView_scanner_line);
            if(drawable == null){
                drawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.img_scanner_bar));
            }
            mScannerLine = drawable;
            typedArray.recycle();
        }
        if(mScannerLine == null){
            mScannerLine = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.img_scanner_bar));
        }
        mRimPaint.setStyle(Paint.Style.FILL);
        mRimPaint.setColor(mRimColor);
        mRimPaint.setStrokeWidth(mRimWidth);
        mHollowRectanglePaint.setStyle(Paint.Style.STROKE);
        mHollowRectanglePaint.setColor(mHollowRectangleColor);
        mHollowRectanglePaint.setStrokeWidth(mHollowRectangleStrokeWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHollowRectangle.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //空心矩形
        canvas.drawRect(mHollowRectangle, mHollowRectanglePaint);

        //左上角轮廓
        canvas.drawLine(0, 0, mRimLong, 0, mRimPaint);
        canvas.drawLine(0, 0, 0, mRimLong, mRimPaint);
        //左下角轮廓
        canvas.drawLine(0, getHeight() - mRimLong, 0, getHeight(), mRimPaint);
        canvas.drawLine(0, getHeight(), mRimLong, getHeight(), mRimPaint);
        //右上角
        canvas.drawLine(getWidth() - mRimLong, 0, getWidth(), 0, mRimPaint);
        canvas.drawLine(getWidth(), 0, getWidth(), mRimLong, mRimPaint);
        //右下角
        canvas.drawLine(getWidth() - mRimLong, getHeight(), getWidth(), getHeight(), mRimPaint);
        canvas.drawLine(getWidth(), getHeight() - mRimLong, getWidth(), getHeight(), mRimPaint);
        drawScannerLine(canvas);
        postInvalidate();

    }

    private void drawScannerLine(Canvas canvas) {
        mScannerLineTop++;
        if (mScannerLineTop >= getHeight()) {
            mScannerLineTop = -mScannerLine.getIntrinsicHeight();
        }

        mScannerLine.setBounds(0, mScannerLineTop, getWidth(), mScannerLineTop + mScannerLine.getIntrinsicHeight());
        mScannerLine.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sourceWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sourceHeight = MeasureSpec.getSize(heightMeasureSpec);

        int dstWidth;
        int dstHeight;
        if (widthMode == MeasureSpec.EXACTLY) {
            dstWidth = sourceWidth;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int minWidth = (int) (mRimLong * 2 + mHorizontalRimSpace);
            if (minWidth < sourceWidth) {
                dstWidth = minWidth;
            } else {
                dstWidth = sourceWidth;
                mHorizontalRimSpace = sourceWidth - mRimLong * 2;
            }
        } else {
            int minWidth = (int) (mRimLong * 2 + mHorizontalRimSpace);
            dstWidth = minWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            dstHeight = sourceHeight;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int minHeight = (int) (mRimLong * 2 + mVerticalRimSpace);
            if (minHeight < sourceHeight) {
                dstHeight = minHeight;
            } else {
                dstHeight = sourceHeight;
                mVerticalRimSpace = sourceWidth - mRimLong * 2;
            }
        } else {
            int minWidth = (int) (mRimLong * 2 + mVerticalRimSpace);
            dstHeight = minWidth;
        }

        mHollowRectangle.set(0, 0, dstWidth, dstHeight);
        setMeasuredDimension(dstWidth, dstHeight);
    }
}
