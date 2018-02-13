package com.github.evan.common_utils.utils;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Evan on 2017/11/9.
 */

public class UiUtil {

    public static boolean addViewInner(ViewGroup parent, View child, int index, boolean preventRequestLayout){
        if(null == parent || null == child){
            return false;
        }

        try {
            Class<?> superclass = parent.getClass().getSuperclass();
            Method addViewInnerMethod = superclass.getDeclaredMethod("addViewInner", View.class, int.class, ViewGroup.LayoutParams.class, boolean.class);
            addViewInnerMethod.setAccessible(true);
            addViewInnerMethod.invoke(parent, child, index, child.getLayoutParams(), preventRequestLayout);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeViewInternal(ViewGroup parent, View view){
        if(null == view){
            return false;
        }

        if(parent != view.getParent()){
            return false;
        }

        try {
            Class<ViewGroup> superclass = (Class<ViewGroup>) parent.getClass().getSuperclass();
            Method removeViewInternalMethod = superclass.getDeclaredMethod("removeViewInternal", View.class);
            removeViewInternalMethod.setAccessible(true);
            removeViewInternalMethod.invoke(parent, view);
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public static int measureWidth(View view){
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);
        return view.getMeasuredWidth();
    }

    public static int measureHeight(View view){
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);
        return view.getMeasuredHeight();
    }

}
