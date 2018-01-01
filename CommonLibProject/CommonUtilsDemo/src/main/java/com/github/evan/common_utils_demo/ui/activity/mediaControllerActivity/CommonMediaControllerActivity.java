package com.github.evan.common_utils_demo.ui.activity.mediaControllerActivity;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;

/**
 * Created by Evan on 2017/12/13.
 */

public class CommonMediaControllerActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
