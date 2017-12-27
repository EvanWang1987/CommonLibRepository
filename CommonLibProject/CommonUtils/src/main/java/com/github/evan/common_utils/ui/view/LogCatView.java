package com.github.evan.common_utils.ui.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.gesture.interceptor.InterceptMode;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingScrollView;

/**
 * Created by Evan on 2017/12/23.
 */
public class LogCatView extends LinearLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private NestingScrollView mScrollView;
    private LinearLayout mInnerLayout, mTitleLayout;
    private ImageButton mBtnClose, mBtnClear;
    private CheckBox mToggleAutoScrollBottom;
    private TextView mTxtLog, mTxtTitle;
    private StringBuffer mStringBuffer = new StringBuffer();
    private View.OnClickListener mCloseListener;
    private boolean mIsAutoScrollToBottom = true;

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

    public void setCloseButtonVisibility(int visibility){
        mBtnClose.setVisibility(visibility);
    }

    public void setAutoScrollBottomToggleVisibility(int visibility){
        mToggleAutoScrollBottom.setVisibility(visibility);
    }

    public void addLog(CharSequence log){
        boolean isContainsContent = mStringBuffer.length() > 0;
        if(isContainsContent){
            mStringBuffer.append("\r\n");
        }
        mStringBuffer.append(log);
        mTxtLog.setText(mStringBuffer.toString());
        if(mIsAutoScrollToBottom){
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
    }

    public CharSequence getAllLog(){
        return mStringBuffer.toString();
    }

    public void setOnCloseClickListener(View.OnClickListener l){
        mCloseListener = l;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_close_logcat_view) {
            if(null != mCloseListener){
                mCloseListener.onClick(v);
            }
        }else if(i == R.id.btn_clear_logcat_view){
            mTxtLog.setText("");
            mStringBuffer.delete(0, mStringBuffer.length());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mIsAutoScrollToBottom = isChecked;
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_logcat, this, true);
        mTitleLayout = findViewById(R.id.title_layout_logcat_view);
        mTxtTitle = findViewById(R.id.title_logcat_view);
        mBtnClose = findViewById(R.id.btn_close_logcat_view);
        mBtnClear = findViewById(R.id.btn_clear_logcat_view);
        mToggleAutoScrollBottom = findViewById(R.id.toggle_auto_scroll_to_bottom_logcat_view);
        mTxtLog = findViewById(R.id.log_logcat_view);
        mScrollView = findViewById(R.id.scroll_view_logcat_view);
        mInnerLayout = findViewById(R.id.scroll_inner_layout_logcat_view);
        mScrollView.setInterceptMode(InterceptMode.VERTICAL);
        mBtnClose.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mToggleAutoScrollBottom.setOnCheckedChangeListener(this);
    }
}
