package com.github.evan.common_utils_demo.ui.activity.aboutActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/10.
 */
public class TaskStackActivity extends BaseLogCatActivity {




    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_extra_information, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_task_stack_start_activity})
    void onClick(View view){
    }

}
