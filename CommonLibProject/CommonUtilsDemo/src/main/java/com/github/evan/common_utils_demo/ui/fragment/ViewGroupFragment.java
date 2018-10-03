package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.AbsoluteLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.ConstraintLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.FrameLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.GridLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.LinearLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.RelativeLayoutActivity;
import com.github.evan.common_utils_demo.ui.activity.aboutViewGroup.TableLayoutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/10/3.
 */

public class ViewGroupFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_view_group, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_linear_layout, R.id.card_frame_layout, R.id.card_relative_layout, R.id.card_table_layout, R.id.card_grid_layout, R.id.card_constraint_layout, R.id.card_absolute_layout})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_linear_layout:
                loadActivity(LinearLayoutActivity.class);
                break;

            case R.id.card_frame_layout:
                loadActivity(FrameLayoutActivity.class);
                break;

            case R.id.card_relative_layout:
                loadActivity(RelativeLayoutActivity.class);
                break;

            case R.id.card_table_layout:
                loadActivity(TableLayoutActivity.class);
                break;

            case R.id.card_grid_layout:
                loadActivity(GridLayoutActivity.class);
                break;

            case R.id.card_constraint_layout:
                loadActivity(ConstraintLayoutActivity.class);
                break;

            case R.id.card_absolute_layout:
                loadActivity(AbsoluteLayoutActivity.class);
                break;
        }
    }

}
