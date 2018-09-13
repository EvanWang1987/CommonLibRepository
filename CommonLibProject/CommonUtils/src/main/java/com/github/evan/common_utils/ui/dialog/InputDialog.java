package com.github.evan.common_utils.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Evan on 2018/9/12.
 */
public class InputDialog extends AlertDialog {
    private static final int DEFAULT_LINES = 5;

    private EditText mEditText;

    protected InputDialog(@NonNull Context context) {
        super(context);
        mEditText = new EditText(context);
        mEditText.setLines(DEFAULT_LINES);
        setView(mEditText);
    }

    public void setEditLines(int lines){
        mEditText.setLines(lines);
    }

    public void setHint(CharSequence hint){
        mEditText.setHint(hint);
    }

    public void setText(CharSequence text){
        mEditText.setText(text);
    }

    public CharSequence getEditText(){
        return mEditText.getText().toString();
    }

    public void clearText(){
        mEditText.setText(null);
    }

}
