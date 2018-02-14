package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils_demo.R;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/2/13.
 */
public class PullWithViewGroupActivity extends BasePullLayoutActivity {

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_pull_layout_with_view_group, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAutoInvokeButtonClick(View view) {
        mPullLayout.autoInvoke(false, 800);
    }

    @Override
    public void onAutoInvokeSecondIndicatorButtonClick(View view) {
        mPullLayout.autoInvoke(true, 800);
    }

    @Override
    public void onInvokingCompleteButtonClick(View view) {
        mPullLayout.invokeComplete();
    }

    @Override
    public boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide) {
        return true;
    }
}
