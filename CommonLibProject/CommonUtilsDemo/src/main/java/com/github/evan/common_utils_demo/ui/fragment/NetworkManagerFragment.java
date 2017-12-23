package com.github.evan.common_utils_demo.ui.fragment;

import android.graphics.PixelFormat;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.evan.common_utils.manager.netManager.NetManager;
import com.github.evan.common_utils.manager.netManager.WifiSignalObserver;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils.utils.UiUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/22.
 */

public class NetworkManagerFragment extends BaseFragment implements WifiSignalObserver, View.OnTouchListener {
    private WindowManager mWindowManager;
    private ImageView mDragView;
    private WindowManager.LayoutParams mParams;
    private int mDownX, mDownY, mLastX, mLastY;
    private int mDragViewWidth;
    private int mDragViewHeight;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWindowManager = getActivity().getWindowManager();
        mRoot = inflater.inflate(R.layout.fragment_network_manager, null);
        ButterKnife.bind(this, mRoot);
        return mRoot;
    }


    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_start_observe_wifi_signal, R.id.card_stop_observe_wifi_signal})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_start_observe_wifi_signal:
                mDragView = new ImageView(getContext());
                mDragView.setImageResource(R.mipmap.ic_wifi_signal_middle);
                mDragView.setOnTouchListener(this);
                mParams = new WindowManager.LayoutParams();
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//                mParams.gravity = Gravity.LEFT | Gravity.TOP;
                mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
                mParams.format = PixelFormat.RGBA_8888;
                mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
                mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//窗口的宽和高
                mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                mDragViewWidth = UiUtil.measureWidth(mDragView);
                mDragViewHeight = UiUtil.measureHeight(mDragView);
                mParams.x = mRoot.getWidth() / 2;
                mParams.y = mRoot.getHeight() / 2;
                mWindowManager.addView(mDragView, mParams);
                NetManager.getInstance(getContext()).startObserveWifiSignalStrength(this);
                break;

            case R.id.card_stop_observe_wifi_signal:
                NetManager.getInstance(getContext()).shutDownObserveWifiSignal();
                mWindowManager.removeView(mDragView);
                break;
        }
    }

    @Override
    public void onObserveWifiSignalStrength(WifiInfo wifiInfo, int signalStrengthLevel) {
        if(null == wifiInfo || signalStrengthLevel == -1){
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_none);
            return;
        }
        String ssId = wifiInfo.getSSID();
        ToastUtil.showToastWithShortDuration("Wifi ssId: " + ssId + ", 信号强度: " + signalStrengthLevel);
        if (signalStrengthLevel >= 0 && signalStrengthLevel < 300) {
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_min);
        } else if (signalStrengthLevel >= 300 && signalStrengthLevel < 600) {
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_middle);
        } else if (signalStrengthLevel >= 600 && signalStrengthLevel < 900) {
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_strong);
        } else if (signalStrengthLevel >= 900 && signalStrengthLevel <= 1200) {
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_max);
        } else {
            mDragView.setImageResource(R.mipmap.ic_wifi_signal_none);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMasked = event.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mDownX = (int) event.getRawX();
            mDownY = (int) event.getRawY();
            mLastX = mDownX;
            mLastY = mDownY;
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            int currentX = (int) event.getRawX();
            int currentY = (int) event.getRawY();
            int offsetX = currentX - mLastX;
            int offsetY = currentY - mLastY;
            mLastX = currentX;
            mLastY = currentY;
            int x = mParams.x;
            int y = mParams.y;
            int dstX = x + offsetX;
            int dstY = y + offsetY;



            if(dstX <= -mRoot.getWidth() / 2){
                dstX = -mRoot.getWidth() / 2;
            }
            if(dstX >= mRoot.getWidth() / 2){
                dstX = mRoot.getWidth() / 2;
            }

            if(dstY <= -mRoot.getHeight() / 2){
                dstY = -mRoot.getHeight() / 2;
            }

            if(dstY >= mRoot.getHeight() / 2){
                dstY = mRoot.getHeight() / 2;
            }

            mParams.x = dstX;
            mParams.y = dstY;
            mWindowManager.updateViewLayout(mDragView, mParams);
        }
        return true;
    }
}
