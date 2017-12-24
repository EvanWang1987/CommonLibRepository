package com.github.evan.common_utils.ui.deskIcon;

import android.view.View;

/**
 * Created by Evan on 2017/12/23.
 */
public interface IDeskIcon {

    View getRootView();

    void alert();

    void dismiss();

    void show();

    void hidden();

    void moveX(int dX);

    void moveY(int dY);

    void move(int dX, int dY);

    int getX();

    int getY();

    void moveTo(int x, int y);

    void setStatus(int status);

}
