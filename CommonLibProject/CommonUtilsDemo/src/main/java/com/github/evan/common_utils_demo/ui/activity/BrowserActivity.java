package com.github.evan.common_utils_demo.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/12.
 */

public class BrowserActivity extends BaseActivity implements TextWatcher{
    public static final String EXTRAS_LOAD_OF_URL = "EXTRAS_LOAD_OF_URL";

    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar_web_view)
    ProgressBar mProgressBar;
    @BindView(R.id.edit_text_web_view)
    EditText mEditText;
    @BindView(R.id.btn_function_web_view)
    ImageButton mBtnFunction;
    @BindView(R.id.btn_android_call_js)
    Button mBtnAndroidCallJs;
    private String mInitUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mInitUrl = getIntent().getExtras().getString(EXTRAS_LOAD_OF_URL);
        if(StringUtil.isEmpty(mInitUrl)){
            finish();
            return;
        }
        init();
        mEditText.addTextChangedListener(this);
        mWebView.loadUrl(mInitUrl);
        mEditText.setText(mInitUrl);
    }

    @OnClick({R.id.btn_android_call_js})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_android_call_js:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }else{
                    mWebView.loadUrl("javascript:callJS()");
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Logger.d("onPageStarted");
                mProgressBar.setVisibility(View.VISIBLE);
                mBtnFunction.setImageResource(R.drawable.selector_browser_stop_white);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Logger.d("onPageFinished");
                mProgressBar.setVisibility(View.GONE);
                mBtnFunction.setImageResource(R.drawable.selector_browser_refresh_white);
                if(mInitUrl.equals("file:///android_asset/android_call_js.html")){
                    mBtnAndroidCallJs.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Logger.d("progress: " + newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(getFilesDir().getPath());

        mWebView.addJavascriptInterface(new JsAndroidObject(), "Android");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mBtnFunction.setImageResource(R.drawable.selector_browser_load_white);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_browser;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    private class JsAndroidObject{

        @JavascriptInterface
        public void showToast(String message){
            ToastUtil.showToastWithShortDuration(message);
        }

    }
}
