package com.github.evan.common_utils.ui.deskIcon.icons;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;
import com.github.evan.common_utils.utils.UiUtil;

/**
 * Created by Evan on 2017/12/24.
 */
public class WifiSignalLevelIcon extends BaseDeskIcon implements View.OnTouchListener {
    public static final int WIFI_SIGNAL_MAX = 5;
    public static final int WIFI_SIGNAL_STRONGER = 4;
    public static final int WIFI_SIGNAL_MIDDLE = 3;
    public static final int WIFI_SIGNAL_MIN = 2;
    public static final int WIFI_SIGNAL_NONE = -1;
    private int mDownX, mDownY, mLastX, mLastY;

    public WifiSignalLevelIcon(Context context) {
        super(context);
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        ImageView wifi = new ImageView(getContext());
        wifi.setOnTouchListener(this);
        wifi.setImageResource(R.mipmap.ic_wifi_signal_middle);
        int width = UiUtil.measureWidth(wifi);
        int height = UiUtil.measureHeight(wifi);
        DeskIconConfig config = new DeskIconConfig();
        config.setDeskIconView(wifi);
        config.setGravity(Gravity.LEFT|Gravity.TOP);
        config.setInitX(windowWidth - width);
        config.setInitY(windowHeight - height);
        return config;
    }

    @Override
    public void setStatus(int status) {
        ImageView ic = (ImageView) getRootView();
        if(status == WIFI_SIGNAL_MAX){
            ic.setImageResource(R.mipmap.ic_wifi_signal_max);
        }else if(status == WIFI_SIGNAL_STRONGER){
            ic.setImageResource(R.mipmap.ic_wifi_signal_strong);
        }else if(status == WIFI_SIGNAL_MIDDLE){
            ic.setImageResource(R.mipmap.ic_wifi_signal_middle);
        }else if(status == WIFI_SIGNAL_MIN){
            ic.setImageResource(R.mipmap.ic_wifi_signal_min);
        }else{
            ic.setImageResource(R.mipmap.ic_wifi_signal_none);
        }
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
