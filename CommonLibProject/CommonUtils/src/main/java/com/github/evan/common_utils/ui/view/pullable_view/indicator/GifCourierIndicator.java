package com.github.evan.common_utils.ui.view.pullable_view.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.GifView;
import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;

/**
 * Created by Administrator on 2018/6/11.
 *
 * Gif,京东快递Indicator
 *
 */
public class GifCourierIndicator extends RelativeLayout implements IIndicator {
    private GifView mGifCourier;
    private TextView mTxtTitle;
    private PullStatus mLastPullStatus;


    public GifCourierIndicator(Context context) {
        super(context);
        init(null);
    }

    public GifCourierIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GifCourierIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GifCourierIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void onPullStatusChange(PullStatus status) {
        switch (status){
            case IDLE:
                if(mGifCourier.isPlaying()){
                    mGifCourier.pause();
                }
                mTxtTitle.setText(getResources().getString(R.string.pulling));
                break;

            case START_PULL:
                mGifCourier.setScaleX(0f);
                mGifCourier.setScaleY(0f);
                mGifCourier.setAlpha(0F);
                mGifCourier.setPivotX(0f);
                int height = getHeight();
                Log.d("Evan", "height: " + height);
                mGifCourier.setPivotY(height);
                break;

            case TOP_PULLING:
                mTxtTitle.setText(getResources().getString(R.string.pulling));

                break;

            case BOTTOM_PULLING:
                mTxtTitle.setText(getResources().getString(R.string.pulling));

                break;

            case RELEASE_TO_INVOKE:
                mGifCourier.setScaleX(1f);
                mGifCourier.setScaleY(1f);
                mGifCourier.setAlpha(1f);
                mTxtTitle.setText(getResources().getString(R.string.release_to_refresh));
                break;

            case INVOKING:
                mTxtTitle.setText(getResources().getString(R.string.refreshing));
                if(!mGifCourier.isPlaying()){
                    mGifCourier.play();
                }
                break;

            case INVOKING_COMPLETE:

                break;
        }
        mLastPullStatus = status;
    }

    @Override
    public void onDistanceChange(int dX, int dY, float dXPercentRelativeParent, float dYPercentRelativeParent) {
        if(dY > 0){
            float scaleX = mGifCourier.getScaleX();
            float scaleY = mGifCourier.getScaleY();
            float alpha = mGifCourier.getAlpha();
            scaleX += 0.01;
            scaleY += 0.01;
            alpha += 0.01;

            if(scaleX > 1f){
                scaleX = 1f;
            }

            if(scaleY > 1f){
                scaleY = 1f;
            }

            if(alpha > 1f){
                alpha = 1f;
            }

            mGifCourier.setScaleX(scaleX);
            mGifCourier.setScaleY(scaleY);
            mGifCourier.setAlpha(alpha);
        }else if(dY < 0){
            float scaleX = mGifCourier.getScaleX();
            float scaleY = mGifCourier.getScaleY();
            float alpha = mGifCourier.getAlpha();
            scaleX -= 0.01;
            scaleY -= 0.01;
            alpha -= 0.01;

            if(scaleX < 0f){
                scaleX = 0f;
            }

            if(scaleY < 0f){
                scaleY = 0f;
            }

            if(alpha < 0f){
                alpha = 0f;
            }

            mGifCourier.setScaleX(scaleX);
            mGifCourier.setScaleY(scaleY);
            mGifCourier.setAlpha(alpha);
        }else{
            //Nothing to do
        }
    }

    @Override
    public void onSlideOverIndicator(boolean isSlideOverIndicator) {

    }

    @Override
    public void onDistanceChangeWhenSlideOverIndicator(int dX, int dY, float dXPercentRelativeParent, float dYPercentRelativeParent) {

    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_gif_courier, this, true);
        mGifCourier = findViewById(R.id.ic_gif_courier);
        mTxtTitle = findViewById(R.id.courier_indicator_txt_title);
    }
}
