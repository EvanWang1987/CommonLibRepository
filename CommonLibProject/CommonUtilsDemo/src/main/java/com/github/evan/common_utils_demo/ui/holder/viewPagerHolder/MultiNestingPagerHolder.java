package com.github.evan.common_utils_demo.ui.holder.viewPagerHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.gesture.TouchEventInterceptor;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingScrollView;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/24.
 */
public class MultiNestingPagerHolder extends BasePagerHolder<Integer> {

    @BindView(R.id.nesting_view_pager_multi_nesting_holder)
    NestingViewPager mViewPager;
    @BindView(R.id.nesting_scroll_view_multi_nesting_holder)
    NestingScrollView mScrollView;
    @BindView(R.id.txt_test_multi_nesting_holder)
    TextView mTxtTest;
    private ImagePagerAdapter mImagePagerAdapter;

    public MultiNestingPagerHolder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.pager_multi_nesting, null);
        ButterKnife.bind(this, root);
        mImagePagerAdapter = new ImagePagerAdapter(getContext());
        return root;
    }

    @Override
    public void instantiateItem(ViewGroup container, int position) {
        int N = ViewPagerFragment.IMAGES.length;
        List<Integer> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(i + 1);
        }
        mImagePagerAdapter.replace(data);
        mViewPager.setAdapter(mImagePagerAdapter);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
