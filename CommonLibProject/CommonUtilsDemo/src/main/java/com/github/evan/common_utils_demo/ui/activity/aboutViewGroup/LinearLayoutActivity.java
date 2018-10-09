package com.github.evan.common_utils_demo.ui.activity.aboutViewGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/10/3.
 */

public class LinearLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_linearlayout;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_weight_formula})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_weight_formula:
                showMessageDialog(getString(R.string.weight_formula), getString(R.string.weight_formula_details), getString(R.string.confirm), getString(R.string.cancel));
                break;
        }
    }
}
