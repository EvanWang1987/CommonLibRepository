package com.github.evan.common_utils_demo.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutActivity.ExtrasInformationActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutActivity.LifeCycleActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutActivity.TaskStackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/9/10.
 */
public class AboutActivityFragment extends BaseFragment {
    private AlertDialog mdSingleChoiceDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_activity, null);
        ButterKnife.bind(this, root);
        String[] stringArray = ResourceUtil.getStringArray(R.array.task_stack_menu);
        mdSingleChoiceDialog = DialogFactory.createMDSingleChoiceDialog(getContext(), stringArray, getString(R.string.choice_task_stack_mode), getString(R.string.confirm), null, new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_activity_life_cycle, R.id.card_activity_task_stack})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_activity_life_cycle:
                loadActivity(LifeCycleActivity.class);
                break;

            case R.id.card_activity_task_stack:
                Bundle bundle = new Bundle();
                bundle.putString(ExtrasInformationActivity.EXTRAS_KEY, "Standard Mode");
                loadActivity(ExtrasInformationActivity.class, bundle, false, -1);
//                mdSingleChoiceDialog.show();
                break;
        }
    }
}
