package com.github.evan.common_utils_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/24.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        DeskIconManager.getInstance(this).release();
    }

    @OnClick({R.id.card_open_rocket})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_open_rocket:
                DeskIconManager.getInstance(this).prepareRocket();
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }
}
