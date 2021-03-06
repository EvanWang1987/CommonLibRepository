package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.pullable_view.FloatingPullLayout;
import com.github.evan.common_utils.ui.view.pullable_view.PullChecker;
import com.github.evan.common_utils.ui.view.pullable_view.PullLayout;
import com.github.evan.common_utils.ui.view.pullable_view.PullListener;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/24.
 */
public class DebugFragment extends BaseFragment implements PullChecker, PullListener {
    @BindView(R.id.pullable_parent)
    PullLayout mPullLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug, null);
        ButterKnife.bind(this, view);
        mPullLayout.setPullChecker(this);
        mPullLayout.setPullListener(this);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btn_invoke, R.id.btn_invoking_complete})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_invoke:
                mPullLayout.autoInvoke(false, 500);
                break;

            case R.id.btn_invoking_complete:
                mPullLayout.invokeComplete();
                break;
        }

    }

    @Override
    public boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide) {
        return true;
    }

    @Override
    public void onStartPull() {
        ToastUtil.showToastWithShortDuration("开始拉动");
    }

    @Override
    public void onInvoke(boolean isFromTopSide, boolean isFromBottomSide, boolean isFromLeftSide, boolean isFromRightSide) {
        String desc = "";
        if (isFromTopSide) {
            desc = "开始刷新, 从头部拉动";
        } else if (isFromBottomSide) {
            desc = "开始刷新, 从底部拉动";
        } else if (isFromLeftSide) {
            desc = "开始刷新, 从左边拉动";
        } else {
            desc = "开始刷新, 从右边拉动";
        }
        ToastUtil.showToastWithShortDuration(desc);
    }
}
