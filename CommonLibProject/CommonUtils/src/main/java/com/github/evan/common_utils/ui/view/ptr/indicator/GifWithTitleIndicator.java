package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.GifView;
import com.github.evan.common_utils.ui.view.ptr.PtrStatus;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2017/11/30.
 */
public class GifWithTitleIndicator extends BaseIndicator {
    private TextView mTxtTitle, mTxtDesc;
    private ImageView mIcDrawable;
    private GifView mGifView;

    public GifWithTitleIndicator(Context context) {
        super(context);
    }

    public GifWithTitleIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GifWithTitleIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.indicator_gif_with_title, null);
        mTxtTitle = root.findViewById(R.id.txt_title_indicator_git_with_title);
        mTxtDesc = root.findViewById(R.id.txt_desc_indicator_git_with_title);
        mIcDrawable = root.findViewById(R.id.ic_drawable_indicator_git_with_title);
        mGifView = root.findViewById(R.id.gif_indicator_git_with_title);
        mGifView.pause();
        return root;
    }

    @Override
    public void onPtrStatusChange(PtrStatus status) {
        handleViewsWithPtrStatus(status);
    }

    @Override
    public void onPullDownOffsetChange(int offsetYFromDown, int offsetYFromLastMoved) {
        float scaleX = mGifView.getScaleX();
        float scaleY = mGifView.getScaleY();
        float alpha = mGifView.getAlpha();

        boolean isTop2BottomMove = offsetYFromLastMoved >= 0;
        if (isTop2BottomMove) {
            scaleX += 0.01f;
            scaleY += 0.01f;
            alpha += 0.01f;

            if (scaleX > 1) {
                scaleX = 1;
            }

            if (scaleY > 1) {
                scaleY = 1;
            }

            if (alpha > 1) {
                alpha = 1;
            }
        } else {
            scaleX -= 0.01f;
            scaleY -= 0.01f;
            alpha -= 0.01f;

            if (scaleX < 0) {
                scaleX = 0;
            }

            if (scaleY < 0) {
                scaleY = 0;
            }

            if (alpha < 0) {
                alpha = 0;
            }
        }

        mGifView.setScaleX(scaleX);
        mGifView.setScaleY(scaleY);
        mGifView.setAlpha(alpha);
    }

    private void handleViewsWithPtrStatus(PtrStatus ptrStatus) {
        switch (ptrStatus) {
            case IDLE:
                mGifView.setPivotX(0f);
                mGifView.setPivotY(mGifView.getHeight());
                mGifView.pause();
                mGifView.setScaleX(0f);
                mGifView.setScaleY(0f);
                mGifView.setAlpha(0f);
                break;

            case START_PULL:
                mTxtDesc.setText(R.string.pulling);
                mIcDrawable.setVisibility(VISIBLE);
                break;

            case PULLING:
                mTxtDesc.setText(R.string.pulling);
                mIcDrawable.setVisibility(VISIBLE);
                break;

            case RELEASE_TO_REFRESH:
                mTxtDesc.setText(R.string.release_to_refresh);
                mIcDrawable.setVisibility(INVISIBLE);
//                mGifView.setScaleX(1);
//                mGifView.setScaleY(1);
//                mGifView.setAlpha(1);
                break;

            case REFRESHING:
                mTxtDesc.setText(R.string.refreshing);
                mIcDrawable.setVisibility(INVISIBLE);
                mGifView.play();
                break;

            case REFRESHED:
                break;

        }
    }
}
