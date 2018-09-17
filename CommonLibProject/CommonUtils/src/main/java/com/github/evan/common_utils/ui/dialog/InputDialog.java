package com.github.evan.common_utils.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.javaDemo.thread.singleProducerConsumer.Resource;
import com.github.evan.common_utils.utils.ResourceUtil;

import java.util.List;

/**
 * Created by Evan on 2018/9/12.
 */
public class InputDialog extends AlertDialog {
    private static final int DEFAULT_LINES = 5;

    private EditText mEditText;
    private LinearLayout mLinearLayout;
    private int mEditTextCount = 1;

    protected InputDialog(@NonNull Context context) {
        super(context);
        mLinearLayout = new LinearLayout(getContext());
        init(1, new String[]{getContext().getString(R.string.input_message)}, new int[]{DEFAULT_LINES});
    }

    protected InputDialog(@NonNull Context context, int lines) {
        super(context);
        mLinearLayout = new LinearLayout(getContext());
        init(1, new String[]{getContext().getString(R.string.input_message)}, new int[]{lines});
    }

    private void init(int editTextCount, CharSequence[] hints, int[] lines){
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < editTextCount; i++) {
            EditText editText = new EditText(getContext());
            editText.setHintTextColor(getContext().getResources().getColor(R.color.text_color_gray));
            editText.setHint(hints[i]);
            editText.setLines(lines[i]);
            mLinearLayout.addView(editText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        setView(mLinearLayout);
    }

    public void setEditTextCount(int editTextCount, CharSequence[] hints, int[] lines){
        mLinearLayout.removeAllViews();
        init(editTextCount, hints, lines);
    }

    public void setEditLines(int lines){
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            EditText editText = (EditText) mLinearLayout.getChildAt(i);
            editText.setLines(lines);
        }
    }

    public void setEditLines(int position, int lines){
        EditText editText = (EditText) mLinearLayout.getChildAt(position);
        editText.setLines(lines);
    }

    public void setHint(CharSequence hint){
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            EditText editText = (EditText) mLinearLayout.getChildAt(i);
            editText.setHint(hint);
        }
    }

    public void setHint(int positive, CharSequence hint){
        EditText editText = (EditText) mLinearLayout.getChildAt(positive);
        editText.setHint(hint);
    }

    public void setText(CharSequence text){
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            EditText editText = (EditText) mLinearLayout.getChildAt(i);
            editText.setText(text);
        }
    }

    public void setText(int positive, CharSequence text){
        EditText editText = (EditText) mLinearLayout.getChildAt(positive);
        editText.setText(text);
    }

    public CharSequence getEditText(int position){
        return ((EditText)mLinearLayout.getChildAt(position)).getText();
    }

    public void clearText(){
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            EditText editText = (EditText) mLinearLayout.getChildAt(i);
            editText.setText(null);
        }
    }

    public void clearText(int position){
        EditText editText = (EditText) mLinearLayout.getChildAt(position);
        editText.setText(null);
    }

}
