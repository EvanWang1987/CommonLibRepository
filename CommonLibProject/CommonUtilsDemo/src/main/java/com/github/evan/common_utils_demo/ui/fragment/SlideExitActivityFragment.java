package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.slideExitActivity.SlideExitBottomToTopActivity;
import com.github.evan.common_utils_demo.ui.activity.slideExitActivity.SlideExitLeftToRightActivity;
import com.github.evan.common_utils_demo.ui.activity.slideExitActivity.SlideExitRightToLeftActivity;
import com.github.evan.common_utils_demo.ui.activity.slideExitActivity.SlideExitTopToBottomActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/18.
 */
public class SlideExitActivityFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slide_exit_activity, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.card_slide_exit_left_to_right, R.id.card_slide_exit_right_to_left, R.id.card_slide_exit_top_to_bottom, R.id.card_slide_exit_bottom_to_top})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.card_slide_exit_left_to_right:
                loadActivity(SlideExitLeftToRightActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_right_to_left:
                loadActivity(SlideExitRightToLeftActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_top_to_bottom:
                loadActivity(SlideExitTopToBottomActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_bottom_to_top:
                loadActivity(SlideExitBottomToTopActivity.class, bundle, false, -1);
                break;
        }
    }

    @Override
    protected void loadData() {

    }
}
