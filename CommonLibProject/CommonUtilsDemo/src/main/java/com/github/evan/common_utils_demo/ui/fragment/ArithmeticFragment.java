package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.LogCatView;
import com.github.evan.common_utils.utils.SortUtils;
import com.github.evan.common_utils_demo.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/2/1.
 */
public class ArithmeticFragment extends BaseFragment {
    @BindView(R.id.log_cat_view)
    LogCatView mLogCatView;
    Random mRandom = new Random();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arithmetic, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_binary_search, R.id.card_select_sort, R.id.card_bubble_sort, R.id.card_merge_sort})
    void onClick(View view){
        switch (view.getId()){

            case R.id.card_binary_search:
                int[] dstBinarySearchArray = {mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101)};
                SortUtils.selectSort(dstBinarySearchArray, SortUtils.OrderBy.Descending, false, null);
                int index = SortUtils.binarySearch(dstBinarySearchArray, dstBinarySearchArray[mRandom.nextInt(dstBinarySearchArray.length)], SortUtils.OrderBy.Descending, true, mLogCatView);
                mLogCatView.addLog("折半查找index: " + index);
                break;

            case R.id.card_select_sort:
                int[] dstSelectArray = {mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101)};
                SortUtils.selectSort(dstSelectArray, SortUtils.OrderBy.Ascending, true, mLogCatView);
                break;

            case R.id.card_bubble_sort:
                int[] dstBubbleArray = {mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101)};
                SortUtils.bubbleSort(dstBubbleArray, SortUtils.OrderBy.Ascending, true, mLogCatView);
                break;

            case R.id.card_merge_sort:
                int[] a = {mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101)};
                int[] b = {mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101), mRandom.nextInt(101)};
                SortUtils.selectSort(a, SortUtils.OrderBy.Ascending, false, null);
                SortUtils.selectSort(b, SortUtils.OrderBy.Ascending, false, null);
                SortUtils.mergeSort(a, b, SortUtils.OrderBy.Ascending, true, mLogCatView);
                break;
        }
    }

}
