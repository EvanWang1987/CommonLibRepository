package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.slideExitActivity.SlideExitDemoActivity;

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
                bundle.putInt(SlideExitDemoActivity.SLIDE_EXIT_DIRECTION, SlideExitDirection.LEFT_TO_RIGHT.value);
                loadActivity(SlideExitDemoActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_right_to_left:
                bundle.putInt(SlideExitDemoActivity.SLIDE_EXIT_DIRECTION, SlideExitDirection.RIGHT_TO_LEFT.value);
                loadActivity(SlideExitDemoActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_top_to_bottom:
                bundle.putInt(SlideExitDemoActivity.SLIDE_EXIT_DIRECTION, SlideExitDirection.TOP_TO_BOTTOM.value);
                loadActivity(SlideExitDemoActivity.class, bundle, false, -1);
                break;

            case R.id.card_slide_exit_bottom_to_top:
                bundle.putInt(SlideExitDemoActivity.SLIDE_EXIT_DIRECTION, SlideExitDirection.BOTTOM_TO_TOP.value);
                loadActivity(SlideExitDemoActivity.class, bundle, false, -1);
                break;

//            case R.id.card_slide_exit_multi_nesting:
//                loadActivity(MultiNestingDemoActivity.class, bundle, false, -1);
//                break;
        }
    }

    @Override
    protected void loadData() {

    }
}
