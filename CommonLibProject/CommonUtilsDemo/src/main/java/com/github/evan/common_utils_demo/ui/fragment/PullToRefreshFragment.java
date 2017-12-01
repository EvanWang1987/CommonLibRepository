package com.github.evan.common_utils_demo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.GifIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.LargeImageIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.ClassicIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.ClassicProIndicatorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/30.
 */
public class PullToRefreshFragment extends BaseFragment {
    @BindView(R.id.btn_with_text_view_pull_to_refresh_fragment)
    Button mBtnPtrWithView;
    @BindView(R.id.btn_with_view_group_pull_to_refresh_fragment)
    Button mBtnPtrWithViewGroup;
    @BindView(R.id.btn_with_list_view_pull_to_refresh_fragment)
    Button mBtnPtrWithListView;
    @BindView(R.id.btn_with_grid_view_pull_to_refresh_fragment)
    Button mBtnPtrWithGridView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pull_to_refresh, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {
    }

    @OnClick({R.id.btn_with_text_view_pull_to_refresh_fragment, R.id.btn_with_view_group_pull_to_refresh_fragment, R.id.btn_with_list_view_pull_to_refresh_fragment, R.id.btn_with_grid_view_pull_to_refresh_fragment})
    void onClick(View view){
        Class<? extends Activity> dstActivity = null;
        switch (view.getId()){
            case R.id.btn_with_text_view_pull_to_refresh_fragment:
                dstActivity = ClassicIndicatorActivity.class;
                break;

            case R.id.btn_with_view_group_pull_to_refresh_fragment:
                dstActivity = ClassicProIndicatorActivity.class;
                break;

            case R.id.btn_with_list_view_pull_to_refresh_fragment:
                dstActivity = GifIndicatorActivity.class;
                break;

            case R.id.btn_with_grid_view_pull_to_refresh_fragment:
                dstActivity = LargeImageIndicatorActivity.class;
                break;
        }
        loadActivity(dstActivity, null, false, -1);
    }

}
