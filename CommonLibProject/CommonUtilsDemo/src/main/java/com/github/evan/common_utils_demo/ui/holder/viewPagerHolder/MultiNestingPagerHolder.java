package com.github.evan.common_utils_demo.ui.holder.viewPagerHolder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingRecyclerView;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingScrollView;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingViewPager;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.DefaultRecyclerAdapter;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.RecyclerAdapter;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/24.
 */
public class MultiNestingPagerHolder extends BasePagerHolder<TitleInteger> {

    @BindView(R.id.nesting_view_pager_multi_nesting_holder)
    NestingViewPager mViewPager;
    @BindView(R.id.nesting_recycler_view_pager_multi_nesting)
    NestingRecyclerView mRecyclerView;
    private ImagePagerAdapter mImagePagerAdapter;
    private DefaultRecyclerAdapter mRecyclerAdapter;

    public MultiNestingPagerHolder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.pager_multi_nesting, null);
        ButterKnife.bind(this, root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mImagePagerAdapter = new ImagePagerAdapter(getContext());
        mRecyclerAdapter = new DefaultRecyclerAdapter(getContext());
        return root;
    }

    @Override
    public void instantiateItem(ViewGroup container, int position) {
        int N = ImagePagerAdapter.IMAGES.length;
        List<Integer> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            data.add(ImagePagerAdapter.IMAGES[i]);
        }
        mImagePagerAdapter.replace(data);
        mViewPager.setAdapter(mImagePagerAdapter);

        int Z = 15;
        List<Integer> listData = new ArrayList<>(Z);
        for (int i = 0; i < Z; i++) {
            listData.add(i + i);
        }
        mRecyclerAdapter.replace(listData);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
