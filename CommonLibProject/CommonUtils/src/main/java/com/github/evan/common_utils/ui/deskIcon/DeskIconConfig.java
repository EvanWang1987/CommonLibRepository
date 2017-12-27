package com.github.evan.common_utils.ui.deskIcon;

import android.view.View;
import android.view.WindowManager;

/**
 * Created by Evan on 2017/12/23.
 */
public class DeskIconConfig {
    private View deskIconView;
    private int initX = 0;
    private int initY = 0;
    private int width = WindowManager.LayoutParams.WRAP_CONTENT;
    private int height = WindowManager.LayoutParams.WRAP_CONTENT;
    private int gravity = -1;


    public DeskIconConfig() {
    }

    public View getDeskIconView() {
        return deskIconView;
    }

    public void setDeskIconView(View deskIconView) {
        this.deskIconView = deskIconView;
    }

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}
