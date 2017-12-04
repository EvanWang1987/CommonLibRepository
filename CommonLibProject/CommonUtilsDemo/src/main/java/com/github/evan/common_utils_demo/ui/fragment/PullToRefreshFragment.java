package com.github.evan.common_utils_demo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithClassicProIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithGifIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithLargeImageIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithClassicIndicatorActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithGridViewActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithListViewActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithRecyclerViewActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithScrollViewActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithViewActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithViewGroupActivity;
import com.github.evan.common_utils_demo.ui.activity.ptrActivity.PtrWithViewPagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/30.
 */
public class PullToRefreshFragment extends BaseFragment {
    @BindView(R.id.card_ptr_with_view)
    CardView mCardPtrWithView;
    @BindView(R.id.card_ptr_with_view_group)
    CardView mCardPtrWithViewGroup;

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

    @OnClick({R.id.card_ptr_with_view, R.id.card_ptr_with_view_group, R.id.card_ptr_with_list_view, R.id.card_ptr_with_grid_view, R.id.card_ptr_with_recycler_view, R.id.card_ptr_with_view_pager, R.id.card_ptr_with_scroll_view, R.id.card_ptr_with_classic_indicator, R.id.card_ptr_with_classic_pro_indicator, R.id.card_ptr_with_gif_indicator, R.id.card_ptr_with_large_image_indicator})
    void onClick(View view) {
        Class<? extends Activity> dstActivity = null;
        switch (view.getId()) {
            case R.id.card_ptr_with_view:
                dstActivity = PtrWithViewActivity.class;
                break;

            case R.id.card_ptr_with_view_group:
                dstActivity = PtrWithViewGroupActivity.class;
                break;

            case R.id.card_ptr_with_list_view:
                dstActivity = PtrWithListViewActivity.class;
                break;

            case R.id.card_ptr_with_grid_view:
                dstActivity = PtrWithGridViewActivity.class;
                break;

            case R.id.card_ptr_with_recycler_view:
                dstActivity = PtrWithRecyclerViewActivity.class;
                break;

            case R.id.card_ptr_with_view_pager:
                dstActivity = PtrWithViewPagerActivity.class;
                break;

            case R.id.card_ptr_with_scroll_view:
                dstActivity = PtrWithScrollViewActivity.class;
                break;

            case R.id.card_ptr_with_classic_indicator:
                dstActivity = PtrWithClassicIndicatorActivity.class;
                break;

            case R.id.card_ptr_with_classic_pro_indicator:
                dstActivity = PtrWithClassicProIndicatorActivity.class;
                break;

            case R.id.card_ptr_with_gif_indicator:
                dstActivity = PtrWithGifIndicatorActivity.class;
                break;

            case R.id.card_ptr_with_large_image_indicator:
                dstActivity = PtrWithLargeImageIndicatorActivity.class;
                break;
        }
        loadActivity(dstActivity, null, false, -1);
    }

}
