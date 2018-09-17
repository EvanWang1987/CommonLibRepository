package com.github.evan.common_utils_demo.ui.activity.aboutDataSave;

import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/9/17.
 */

public class ContentProviderActivity extends BaseLogCatActivity {

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_content_provider, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
