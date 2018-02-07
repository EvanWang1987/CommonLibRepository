package com.github.evan.common_utils.ui.view.pullable_view.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.pullable_view.PullStatus;
import com.github.evan.common_utils.utils.Logger;

/**
 * Created by Evan on 2018/2/5.
 */
public class TimeFlagIndicator extends LinearLayout implements  IIndicator{
    private ImageView mIcArrow, mIcProgress;
    private TextView mTxtTitle, mTxtDesc;



    public TimeFlagIndicator(Context context) {
        super(context);
        init();
    }

    public TimeFlagIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeFlagIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_horizontal_time_flag, this, true);
        mIcArrow = findViewById(R.id.ic_arrow_indicator_classic);
        mIcProgress = findViewById(R.id.ic_progress_indicator_classic);
        mTxtTitle = findViewById(R.id.txt_title_indicator_classic);
        mTxtDesc = findViewById(R.id.txt_desc_indicator_classic);
    }

    @Override
    public View getIndicatorView() {
        return this;
    }

    @Override
    public void onPullStatusChange(PullStatus status) {
        Logger.d("onPullStatusChange status: " + status);
    }

    @Override
    public void onDistanceChange(int x, int y) {
//        Logger.d("onDistanceChange x: " + x + ", y: " + y);
    }
}
