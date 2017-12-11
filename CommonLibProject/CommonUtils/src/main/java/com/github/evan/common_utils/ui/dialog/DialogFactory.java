package com.github.evan.common_utils.ui.dialog;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by Evan on 2017/12/11.
 */

public class DialogFactory {

    public static android.support.v7.app.AlertDialog createDesignMessageDialog(Context context, @DrawableRes int icon, CharSequence title, CharSequence message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(icon > 0){
            builder.setIcon(icon);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public static android.app.AlertDialog createHoloMessageDialog(Context context, @DrawableRes int icon, CharSequence title, CharSequence message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        if(icon > 0){
            builder.setIcon(icon);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        android.app.AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

}
