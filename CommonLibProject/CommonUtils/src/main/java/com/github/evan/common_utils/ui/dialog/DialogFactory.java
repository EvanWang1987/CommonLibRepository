package com.github.evan.common_utils.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Evan on 2017/12/11.
 */

public class DialogFactory {

    public static ProgressDialog createProgressDialog(Context context, CharSequence title, CharSequence message, int progressStyle, int max, int progress){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setProgressStyle(progressStyle);
        dialog.setMax(max);
        dialog.setProgress(progress);
        return dialog;
    }

    public static InputDialog createMdInputDialog(Context context, CharSequence title, CharSequence hint, int inputLines){
        InputDialog inputDialog = new InputDialog(context);
        inputDialog.setTitle(title);
        inputDialog.setHint(hint);
        inputDialog.setEditLines(inputLines);
        return inputDialog;
    }

    public static android.support.v7.app.AlertDialog createDesignMessageDialog(Context context, @DrawableRes int icon, CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (icon > 0) {
            builder.setIcon(icon);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public static android.app.AlertDialog createHoloMessageDialog(Context context, @DrawableRes int icon, CharSequence title, CharSequence message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        if (icon > 0) {
            builder.setIcon(icon);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        android.app.AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    public static android.support.v7.app.AlertDialog createMDSingleChoiceDialog(Context context, String[] menuItem, CharSequence title, CharSequence positiveTitle, DialogInterface.OnClickListener clickListener, DialogInterface.OnClickListener positiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(menuItem, 0, clickListener);
        builder.setPositiveButton(positiveTitle, positiveListener);
        return builder.create();
    }

    public static android.app.AlertDialog createHoloSingleChoiceDialog(Context context, String[] menuItem, CharSequence title, CharSequence positiveTitle, DialogInterface.OnClickListener clickListener, DialogInterface.OnClickListener positiveListener) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(menuItem, 0, clickListener);
        builder.setPositiveButton(positiveTitle, positiveListener);
        return builder.create();
    }

}
