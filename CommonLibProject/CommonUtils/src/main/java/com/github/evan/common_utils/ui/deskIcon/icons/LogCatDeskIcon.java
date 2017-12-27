package com.github.evan.common_utils.ui.deskIcon.icons;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.view.LogCatView;
import com.github.evan.common_utils.utils.DensityUtil;

/**
 * Created by Evan on 2017/12/24.
 */
public class LogCatDeskIcon extends BaseDeskIcon implements View.OnClickListener, View.OnTouchListener {
    private int mDownX, mDownY, mLastX, mLastY;


    public LogCatDeskIcon(Context context) {
        super(context);
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        LogCatView logCatView = new LogCatView(getContext());
        logCatView.setOnTouchListener(this);
        logCatView.setOnCloseClickListener(this);
        DeskIconConfig config = new DeskIconConfig();
        config.setDeskIconView(logCatView);
        config.setInitX(0);
        config.setInitY(0);
        config.setWidth(DensityUtil.dp2px(220));
        config.setHeight(DensityUtil.dp2px(180));
        config.setGravity(Gravity.LEFT|Gravity.TOP);
        return config;
    }

    public void addLog(CharSequence log){
        LogCatView logCatView = (LogCatView) getRootView();
        logCatView.addLog(log);
    }

    public CharSequence getAllLog(){
        LogCatView logCatView = (LogCatView) getRootView();
        return logCatView.getAllLog();
    }

    @Override
    public void setStatus(int status) {

    }

    @Override
    public void onClick(View v) {
        DeskIconManager.getInstance(getContext()).dismissLogCatIcon();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if(actionMasked == MotionEvent.ACTION_DOWN){
            mDownX = (int) event.getRawX();
            mDownY = (int) event.getRawY();
            mLastX = mDownX;
            mLastY = mDownY;
        }else if(actionMasked == MotionEvent.ACTION_MOVE){
            int currentX = (int) event.getRawX();
            int currentY = (int) event.getRawY();
            int offsetX = currentX - mLastX;
            int offsetY = currentY - mLastY;
            mLastX = currentX;
            mLastY = currentY;

            int dstX = getX() + offsetX;
            int dstY = getY() + offsetY;


            if(dstX <= 0 || dstX >= windowWidth - getRootView().getWidth()){
                offsetX = 0;
            }

            if(dstY <= 0 || dstY >= windowHeight - getRootView().getHeight()){
                offsetY = 0;
            }

            move(offsetX, offsetY);
        }else if(actionMasked == MotionEvent.ACTION_UP || actionMasked == MotionEvent.ACTION_CANCEL){
            mDownX = 0;
            mDownY = 0;
            mLastX = 0;
            mLastY = 0;
        }
        return true;
    }
}
