package com.github.evan.common_utils_demo.ui.activity.aboutAnimation;

import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/21.
 */

public class ObjectAnimatorActivity extends BaseLogCatActivity {



    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_object_animator, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
