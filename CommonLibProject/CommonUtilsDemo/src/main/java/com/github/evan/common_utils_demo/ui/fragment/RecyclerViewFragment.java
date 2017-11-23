package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.itemDecoration.GridDecoration;
import com.github.evan.common_utils.ui.itemDecoration.ListDecoration;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Evan on 2017/11/20.
 */
public class RecyclerViewFragment extends BaseFragment {
    private static final int[] STAGGERED_IMAGE = {R.mipmap.img_staggered_one, R.mipmap.img_staggered_two, R.mipmap.img_staggered_three,
            R.mipmap.img_staggered_four, R.mipmap.img_staggered_five, R.mipmap.img_staggered_six, R.mipmap.img_staggered_seven, R.mipmap.img_staggered_eight, R.mipmap.img_staggered_nine, R.mipmap.img_staggered_ten,
            R.mipmap.img_staggered_eleven, R.mipmap.img_staggered_twelve, R.mipmap.img_staggered_thirteen, R.mipmap.img_staggered_fourteen, R.mipmap.img_staggered_fifteen, R.mipmap.img_staggered_sixteen, R.mipmap.img_staggered_seventeen,
            R.mipmap.img_staggered_eighteen, R.mipmap.img_staggered_nineteen, R.mipmap.img_staggered_twenty};


    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_btn_list_mode)
    RadioButton mBtnVerticalStyle;
    @BindView(R.id.radio_btn_horizontal_list_mode)
    RadioButton mBtnHorizontalStyle;
    @BindView(R.id.radio_btn_grid_mode)
    RadioButton mBtnGridStyle;
    @BindView(R.id.radio_btn_waterfall)
    RadioButton mBtnWaterfallStyle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnCheckedChanged({R.id.radio_btn_list_mode, R.id.radio_btn_horizontal_list_mode, R.id.radio_btn_grid_mode, R.id.radio_btn_waterfall})
    void onCheckedChange(CompoundButton button) {
        if (button.isChecked()) {
            switch (button.getId()) {
                case R.id.radio_btn_list_mode:
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_VERTICAL);
                    mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                    break;

                case R.id.radio_btn_horizontal_list_mode:
                    RecyclerView.LayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    mRecyclerView.setLayoutManager(horizontalLayoutManager);
                    mRecyclerAdapter.setCurrentMode(RecyclerAdapter.LIST_MODE_HORIZONTAL);
                    mRecyclerView.addItemDecoration(new ListDecoration(R.color.Alpha, DensityUtil.dp2px(5)));
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                    break;

                case R.id.radio_btn_grid_mode:
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                    mRecyclerAdapter.setCurrentMode(RecyclerAdapter.GRID_MODE);
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                    break;

                case R.id.radio_btn_waterfall:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                    mRecyclerAdapter.setCurrentMode(RecyclerAdapter.STAGGERED_GRID_MODE);
                    mRecyclerView.setAdapter(mRecyclerAdapter);

                    break;
            }
        }
    }

    @Override
    public void onHandleMessage(Message message) {
        if (message.what == LOAD_COMPLETE) {
            mBtnVerticalStyle.setChecked(true);
        }
    }

    @Override
    protected void loadData() {
        new Thread() {
            @Override
            public void run() {
                int N = 20;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(STAGGERED_IMAGE[i]);
                }

                mRecyclerAdapter = new RecyclerAdapter(getContext());
                mRecyclerAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }
}
