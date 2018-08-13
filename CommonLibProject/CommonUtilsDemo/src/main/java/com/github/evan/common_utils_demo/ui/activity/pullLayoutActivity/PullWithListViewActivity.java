package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.view.pullable_view.PullListener;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.DefaultAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Evan on 2018/2/14.
 */

public class PullWithListViewActivity extends BasePullLayoutActivity implements PullListener {

    private DefaultAdapter mDefaultAdapter;
    private ListView mListView;
    private List<Integer> mData = new ArrayList<>(10);
    private List<Integer> mLoadMoreData = new ArrayList<>(10);

    @Override
    public void onHandleMessage(Message message) {
        int what = message.what;
        if (what == LOAD_COMPLETE) {
            mDefaultAdapter.replace(mData);
            mDefaultAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mPullLayout.invokeComplete();
        } else if (what == LOAD_MORE_COMPLETE) {
            mDefaultAdapter.addAll(mLoadMoreData);
            mDefaultAdapter.notifyDataSetChanged();
            mPullLayout.invokeComplete();
        }
    }

    @Override
    public boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide) {
        if (isTop2BottomSlide) {
            return !mListView.canScrollVertically(-1);
        } else if (isBottom2TopSlide) {
            return !mListView.canScrollVertically(1);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        mListView = new ListView(this);
        mListView.setDivider(new ColorDrawable(getResources().getColor(com.github.evan.common_utils.R.color.Alpha)));
        mListView.setDividerHeight(DensityUtil.dp2px(5));
        mListView.setSelector(com.github.evan.common_utils.R.color.Alpha);
        mDefaultAdapter = new DefaultAdapter(this);
        mListView.setAdapter(mDefaultAdapter);
        mPullLayout.setPullListener(this);
        mListView.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.White)));
        return mListView;
    }

    @Override
    public void onAutoInvokeButtonClick(View view) {
        mPullLayout.autoInvoke(false, 800);
        onInvoke(true, false, false, false);
    }

    @Override
    public void onAutoInvokeSecondIndicatorButtonClick(View view) {
        mPullLayout.autoInvoke(true, 800);
        onInvoke(false, true, false, false);
    }

    @Override
    public void onInvokingCompleteButtonClick(View view) {
        mPullLayout.invokeComplete();
    }

    @Override
    public void onStartPull() {

    }

    @Override
    public void onInvoke(boolean isFromTopSide, boolean isFromBottomSide, boolean isFromLeftSide, boolean isFromRightSide) {
        if (isFromTopSide) {
            refresh();
        } else if (isFromBottomSide) {
            loadMore();
        }
    }

    private void refresh() {
        ThreadPoolExecutor networkThreadPool = ThreadManager.getInstance().getNetworkThreadPool();
        networkThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                mData.clear();
                for (int i = 0, length = 10; i < length; i++) {
                    mData.add(i);
                }
                sendEmptyMessage(LOAD_COMPLETE);
            }
        });
    }

    private void loadMore() {
        ThreadPoolExecutor networkThreadPool = ThreadManager.getInstance().getNetworkThreadPool();
        networkThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                mLoadMoreData.clear();
                for (int i = 0, length = 10; i < length; i++) {
                    mLoadMoreData.add(mData.size() + i);
                }
                sendEmptyMessage(LOAD_MORE_COMPLETE);
            }
        });
    }
}
