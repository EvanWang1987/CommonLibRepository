package com.github.evan.common_utils.utils;

import android.view.Menu;

import java.lang.reflect.Method;

/**
 * Created by Evan on 2017/11/9.
 */

public class UiUtil {

    /**
     * 设置ToolBar上弹出按钮菜单时默认显示icon
     * @param menu
     */
    public static void setPopupToolBarMenuWithIcon(Menu menu){
        if (null != menu && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
