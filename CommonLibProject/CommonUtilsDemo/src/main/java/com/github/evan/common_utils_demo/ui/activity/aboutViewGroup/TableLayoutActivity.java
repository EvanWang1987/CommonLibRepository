package com.github.evan.common_utils_demo.ui.activity.aboutViewGroup;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2018/10/3.
 */

public class TableLayoutActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.activity_table_layout;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
