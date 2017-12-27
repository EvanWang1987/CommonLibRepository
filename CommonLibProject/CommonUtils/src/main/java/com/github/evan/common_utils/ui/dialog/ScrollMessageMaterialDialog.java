package com.github.evan.common_utils.ui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.evan.common_utils.R;

/**
 * Created by Evan on 2017/12/26.
 */

public class ScrollMessageMaterialDialog extends AlertDialog {
    private ImageView mIcTitle;
    private TextView mTxtTitle, mTxtMessage;
    private ScrollView mScrollView;
    private View mRoot;

    public ScrollMessageMaterialDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ScrollMessageMaterialDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    public ScrollMessageMaterialDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        mRoot = LayoutInflater.from(getContext()).inflate(R.layout.dialog_scroll_message, null);
        mIcTitle = mRoot.findViewById(R.id.ic_title_scroll_message_material_dialog);
        mTxtTitle = mRoot.findViewById(R.id.txt_title_scroll_message_material_dialog);
        mTxtMessage = mRoot.findViewById(R.id.txt_message_scroll_message_material_dialog);
        mScrollView = mRoot.findViewById(R.id.scroll_view_scroll_message_material_dialog);
    }

    @Override
    public void show() {
        super.show();
        setContentView(mRoot);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTxtTitle.setText(title);
    }

    @Override
    public void setIcon(int resId) {
        mIcTitle.setImageResource(resId);
    }

    @Override
    public void setIcon(Drawable icon) {
        mIcTitle.setImageDrawable(icon);
    }

    @Override
    public void setMessage(CharSequence message) {
        mTxtMessage.setText(message);
    }
}
