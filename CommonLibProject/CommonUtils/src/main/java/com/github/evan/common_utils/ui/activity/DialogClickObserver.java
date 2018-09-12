package com.github.evan.common_utils.ui.activity;

import android.content.DialogInterface;

/**
 * Created by Evan on 2018/9/12.
 */

public interface DialogClickObserver {
    public void onDialogConfirmButtonClick(DialogInterface dialog, DialogMode mode);
    public void onDialogCancelButtonClick(DialogInterface dialog, DialogMode mode);
}
