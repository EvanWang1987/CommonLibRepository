package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.github.evan.common_utils.utils.DateUtil;
import java.util.Locale;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2017/12/14.
 */
public class TimeView extends AppCompatTextView implements Runnable {
    private String mTimePattern = DateUtil.HH_mm;
    private ScheduledThreadPoolExecutor mExecutor;

    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startAutoRefreshTime(long interval, TimeUnit timeUnit){
        if(mExecutor != null && !mExecutor.isShutdown()){
            mExecutor.shutdownNow();
        }

        mExecutor = new ScheduledThreadPoolExecutor(1);
        mExecutor.schedule(this, interval, timeUnit);
    }

    public void stopAutoRefreshTime(){
        if(null != mExecutor){
            if(!mExecutor.isShutdown()){
                mExecutor.shutdownNow();
            }
            mExecutor = null;
        }
    }

    public void refreshCurrentTime(){
        long currentTimeMillis = System.currentTimeMillis();
        String timeString = DateUtil.time2String(currentTimeMillis, mTimePattern, Locale.getDefault());
        setText(timeString);
    }

    @Override
    public void run() {
        refreshCurrentTime();
    }
}
