package com.github.evan.common_utils.ui.view.ptr.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.flagView.flagUpdater.TimeFlagUpdater;
import com.github.evan.common_utils.utils.StringUtil;

/**
 * Created by Evan on 2017/11/30.
 */
public abstract class TimeFlagIndicator extends BaseIndicator {
    private String mFlagName;
    private TimeFlagUpdater mTimeFlagUpdater;


    public TimeFlagIndicator(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimeFlagIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimeFlagIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int style){
        if(null != attrs){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeFlagIndicator);
            String string = typedArray.getString(R.styleable.TimeFlagIndicator_update_time_flag);
            if(StringUtil.isEmpty(string)){
                string = StringUtil.toStringWithoutHashCode(context) + " / " + this.getClass().getName();
            }
            mFlagName = string;
            typedArray.recycle();
        }
        mTimeFlagUpdater = new TimeFlagUpdater(mFlagName);
    }

    protected void saveUpdateTime(long time){
        mTimeFlagUpdater.saveFlag(time);
    }

    protected long getLastUpdateTime(){
        return mTimeFlagUpdater.getFlag();
    }

}
