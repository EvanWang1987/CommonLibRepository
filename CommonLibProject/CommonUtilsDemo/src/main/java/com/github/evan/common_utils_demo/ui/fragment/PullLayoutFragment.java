package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity.PullWithListViewActivity;
import com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity.PullWithTextViewActivity;
import com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity.PullWithViewGroupActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/2/12.
 */

public class PullLayoutFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pull_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_pull_layout_with_text_view, R.id.card_pull_layout_with_view_group, R.id.card_pull_layout_with_list_view, R.id.card_pull_layout_with_grid_view, R.id.card_pull_layout_with_recycler_view, R.id.card_pull_layout_with_view_pager, R.id.card_pull_layout_with_scroll_view, R.id.card_pull_layout_with_horizontal_scroll_view})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_pull_layout_with_text_view:
                loadActivity(PullWithTextViewActivity.class);
                break;

            case R.id.card_pull_layout_with_view_group:
                loadActivity(PullWithViewGroupActivity.class);
                break;

            case R.id.card_pull_layout_with_list_view:
                loadActivity(PullWithListViewActivity.class);
                break;

            case R.id.card_pull_layout_with_grid_view:

                break;

            case R.id.card_pull_layout_with_recycler_view:

                break;

            case R.id.card_pull_layout_with_view_pager:

                break;

            case R.id.card_pull_layout_with_scroll_view:

                break;

            case R.id.card_pull_layout_with_horizontal_scroll_view:

                break;
        }
    }
}
