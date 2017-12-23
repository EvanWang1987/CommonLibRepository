package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingScrollView;

/**
 * Created by Evan on 2017/12/23.
 */
public class LogCatView extends LinearLayout implements View.OnClickListener {
    private NestingScrollView mScrollView;
    private LinearLayout mInnerLayout, mTitleLayout;
    private ImageButton mBtnClose;
    private TextView mTxtLog, mTxtTitle;
    private StringBuffer mStringBuffer = new StringBuffer();
    private View.OnClickListener mCloseListener;

    public LogCatView(Context context) {
        super(context);
        init();
    }

    public LogCatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogCatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_logcat, this, true);
        mTitleLayout = findViewById(R.id.title_layout_logcat_view);
        mTxtTitle = findViewById(R.id.title_logcat_view);
        mBtnClose = findViewById(R.id.btn_close_logcat_view);
        mTxtLog = findViewById(R.id.log_logcat_view);
        mScrollView = findViewById(R.id.scroll_view_logcat_view);
        mInnerLayout = findViewById(R.id.scroll_inner_layout_logcat_view);
        mScrollView.setInterceptMode(InterceptMode.VERTICAL);
        mBtnClose.setOnClickListener(this);
    }

    public void addLog(CharSequence log){
        boolean isContainsContent = mStringBuffer.length() > 0;
        if(isContainsContent){
            mStringBuffer.append("\r\n");
        }
        mStringBuffer.append(log);
        mTxtLog.setText(mStringBuffer.toString());
        Handler handler = getHandler();
        if(null != handler){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(NestingScrollView.FOCUS_DOWN);
                }
            }, 500);
        }
    }

    public CharSequence getAllLog(){
        return mStringBuffer.toString();
    }

    public void setOnCloseClickListener(View.OnClickListener l){
        mCloseListener = l;
    }

    @Override
    public void onClick(View v) {
        if(null != mCloseListener){
            mCloseListener.onClick(v);
        }
    }
}
