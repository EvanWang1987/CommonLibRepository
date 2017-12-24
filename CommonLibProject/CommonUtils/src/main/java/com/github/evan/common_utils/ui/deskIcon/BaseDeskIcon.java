package com.github.evan.common_utils.ui.deskIcon;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

import com.github.evan.common_utils.utils.DensityUtil;

/**
 * Created by Evan on 2017/12/23.
 */
public abstract class BaseDeskIcon implements IDeskIcon {
    public abstract DeskIconConfig onCreateDeskIconConfig();

    private Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mDeskIconView;
    protected int windowHeight = (int) (DensityUtil.getRealScreenHeightOfPx() - DensityUtil.getStatusBarHeight());
    protected int windowWidth = DensityUtil.getRealScreenWidthOfPx();

    public BaseDeskIcon(Context context) {
        mContext = context;
        DeskIconConfig deskIconConfig = onCreateDeskIconConfig();
        if(mContext == null || deskIconConfig == null){
            throw new IllegalArgumentException("Context or DeskIconConfig can not be null.");
        }
        this.mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mDeskIconView = deskIconConfig.getDeskIconView();
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.x = deskIconConfig.getInitX();
        mLayoutParams.y = deskIconConfig.getInitY();
        if(deskIconConfig.getGravity() != -1){
            mLayoutParams.gravity = deskIconConfig.getGravity();
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public View getRootView() {
        return mDeskIconView;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    @Override
    public void alert() {
        mWindowManager.addView(mDeskIconView, mLayoutParams);
    }

    @Override
    public void dismiss() {
        mWindowManager.removeView(mDeskIconView);
    }

    @Override
    public void show() {
        mDeskIconView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidden() {
        mDeskIconView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void moveX(int dX) {
        mLayoutParams.x += dX;
        mWindowManager.updateViewLayout(mDeskIconView, mLayoutParams);
    }

    @Override
    public void moveY(int dY) {
        mLayoutParams.y += dY;
        mWindowManager.updateViewLayout(mDeskIconView, mLayoutParams);
    }

    @Override
    public void move(int dX, int dY) {
        mLayoutParams.x += dX;
        mLayoutParams.y += dY;
        mWindowManager.updateViewLayout(mDeskIconView, mLayoutParams);
    }

    @Override
    public int getX() {
        return mLayoutParams.x;
    }

    @Override
    public int getY() {
        return mLayoutParams.y;
    }

    @Override
    public void moveTo(int x, int y) {
        mLayoutParams.x = x;
        mLayoutParams.y = y;
        mWindowManager.updateViewLayout(mDeskIconView, mLayoutParams);
    }

    public void release(){
        dismiss();
        mContext = null;
    }
}
