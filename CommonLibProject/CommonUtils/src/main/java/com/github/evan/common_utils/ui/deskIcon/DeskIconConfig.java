package com.github.evan.common_utils.ui.deskIcon;

import android.view.View;

/**
 * Created by Evan on 2017/12/23.
 */
public class DeskIconConfig {
    private View deskIconView;
    private int initX;
    private int initY;
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

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}
