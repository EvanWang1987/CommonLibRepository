package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.view.pullable_view.PullChecker;
import com.github.evan.common_utils.ui.view.pullable_view.PullLayout;
import com.github.evan.common_utils.ui.view.pullable_view.PullListener;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/2/12.
 */
public class PullWithTextViewActivity extends BaseActivity implements PullListener, PullChecker {
    @BindView(R.id.pull_layout_with_text_view)
    PullLayout mPullLayout;
    @BindView(R.id.txt_pull_layout_with_text_view)
    TextView mTxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPullLayout.setPullListener(this);
        mPullLayout.setPullChecker(this);
        postDelay(new Runnable() {
            @Override
            public void run() {
                mPullLayout.autoInvoke(true, 500);
                postDelay(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToastWithShortDuration("刷新完毕");
                        mPullLayout.invokeComplete();
                    }
                }, 3000);
            }
        }, 500);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pull_with_text_view;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Override
    public void onStartPull() {
        ToastUtil.showToastWithShortDuration("开始拉动刷新");
    }

    @Override
    public void onInvoke(boolean isFromTopSide, boolean isFromBottomSide, boolean isFromLeftSide, boolean isFromRightSide) {
        postDelay(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastWithShortDuration("刷新完毕");
                mPullLayout.invokeComplete();
            }
        }, 3000);
    }

    @Override
    public boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide) {
        return true;
    }
}
