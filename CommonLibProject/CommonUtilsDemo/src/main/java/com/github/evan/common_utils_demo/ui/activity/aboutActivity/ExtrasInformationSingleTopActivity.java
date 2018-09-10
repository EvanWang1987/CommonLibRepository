package com.github.evan.common_utils_demo.ui.activity.aboutActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/10.
 */
public class ExtrasInformationSingleTopActivity extends BaseActivity {
    public static final String EXTRAS_KEY = "EXTRAS_KEY";
    @BindView(R.id.task_mode_spinner)
    Spinner mSpinner;
    @BindView(R.id.txt_task_stack_subtitle)
    TextView mTxtExtras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ResourceUtil.getStringArray(R.array.task_stack_menu));
        mSpinner.setAdapter(adapter);
        String stringExtra = getIntent().getExtras().getString(EXTRAS_KEY);
        mTxtExtras.setText(stringExtra);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_extra_information;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_task_stack_start_activity})
    void onClick(View view){
        Intent intent = new Intent();
        ComponentName componentName;
        Bundle bundle = new Bundle();
        String mode = mSpinner.getSelectedItem().toString();
        if(mode.equals(getString(R.string.standard_mode))){
            componentName = new ComponentName(this, ExtrasInformationActivity.class);
            bundle.putString(ExtrasInformationActivity.EXTRAS_KEY, mode);
        }else if(mode.equals(getString(R.string.single_top_mode))){
            componentName = new ComponentName(this, ExtrasInformationSingleTopActivity.class);
            bundle.putString(ExtrasInformationActivity.EXTRAS_KEY, mode);
        }else if(mode.equals(getString(R.string.single_instance_mode))){
            componentName = new ComponentName(this, ExtrasInformationSingleInstanceActivity.class);
            bundle.putString(ExtrasInformationActivity.EXTRAS_KEY, mode);
        }else{
            //single task
            componentName = new ComponentName(this, ExtrasInformationSingleTaskActivity.class);
            bundle.putString(ExtrasInformationActivity.EXTRAS_KEY, mode);
        }

        intent.setComponent(componentName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
