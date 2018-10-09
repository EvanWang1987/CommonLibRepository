package com.github.evan.common_utils_demo.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.activity.DialogMode;
import com.github.evan.common_utils.ui.dialog.InputDialog;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.BrowserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/12.
 */
public class AboutWebViewFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_web_view, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDialogConfirmButtonClick(DialogInterface dialog, DialogMode mode) {
        if(mode == DialogMode.INPUT_DIALOG){
            InputDialog inputDialog = (InputDialog) dialog;
            String url = inputDialog.getEditText(0).toString();
            Bundle bundle = new Bundle();
            bundle.putString(BrowserActivity.EXTRAS_LOAD_OF_URL, url);
            loadActivity(BrowserActivity.class, bundle, false, -1);
        }
    }

    @OnClick({R.id.card_load_url, R.id.card_js_call_android, R.id.card_android_call_js})
    void onClick(View view){
        Bundle bundle = null;
        switch (view.getId()){
            case R.id.card_load_url:
                getActivityProvider().showDialog(this, DialogMode.INPUT_DIALOG, getString(R.string.notice), null, -1, getString(R.string.confirm), getString(R.string.cancel), new String[]{getString(R.string.input_url)}, 1, new int[]{1}, -1, -1, -1);
                break;

            case R.id.card_js_call_android:
                bundle = new Bundle();
                bundle.putString(BrowserActivity.EXTRAS_LOAD_OF_URL, "file:///android_asset/js_call_android.html");
                loadActivity(BrowserActivity.class, bundle, false, -1);
                break;

            case R.id.card_android_call_js:
                bundle = new Bundle();
                bundle.putString(BrowserActivity.EXTRAS_LOAD_OF_URL, "file:///android_asset/android_call_js.html");
                loadActivity(BrowserActivity.class, bundle, false, -1);
                break;
        }
    }
}
