package com.github.evan.common_utils_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.github.evan.common_utils.ui.view.ExtraButtonEditText;
import com.github.evan.common_utils.ui.view.LimitEditText;
import com.github.evan.common_utils.ui.view.LoginEditText;
import com.github.evan.common_utils.ui.view.PasswordEditText;
import com.github.evan.common_utils.utils.Logger;

public class MainActivity extends AppCompatActivity implements ExtraButtonEditText.OnExtraButtonClickListener, PasswordEditText.OnPasswordVisibilityCheckedChangeListener {
    private AppCompatEditText mAndroidEditText;
    private LimitEditText mLimitEditText;
    private ExtraButtonEditText mBtnEditText;
    private PasswordEditText mPasswordEditText;
    private LoginEditText mAccountEditText;
    private LoginEditText mPwdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAndroidEditText = (AppCompatEditText) findViewById(R.id.androidEditText);
        mLimitEditText = (LimitEditText) findViewById(R.id.limitEditText);
        mBtnEditText = (ExtraButtonEditText) findViewById(R.id.extraButtonEditText);
        mPasswordEditText = (PasswordEditText) findViewById(R.id.passwordEditText);
        mAccountEditText = (LoginEditText) findViewById(R.id.accountPwdEditText);
        mPwdEditText = (LoginEditText) findViewById(R.id.pwdEditText);
        mBtnEditText.setExtraButtonClickListener(this);
        mPasswordEditText.setPasswordVisibilityCheckedChangeListener(this);
    }

    @Override
    public void onExtraButtonClick(View v, int whichButton) {
        if(whichButton == ExtraButtonEditText.EXTRA_BUTTON_LEFT){
            Toast.makeText(this, "左边按钮被点击", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "右边按钮被点击", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPasswordVisibilityCheckedChange(PasswordEditText v, boolean isChecked) {
        Toast.makeText(this, "密码" + (isChecked ? "可见" : "不可见"), Toast.LENGTH_SHORT).show();
    }
}
