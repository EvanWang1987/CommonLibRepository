package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/11/22.
 */

public class ViewPagerFragment extends BaseFragment {
    public static final int[] IMAGES = {R.mipmap.img_view_pager_one, R.mipmap.img_view_pager_two, R.mipmap.img_view_pager_three, R.mipmap.img_view_pager_four, R.mipmap.img_view_pager_five};

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private ImagePagerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_pager, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mViewPager.setAdapter(mAdapter);
        }
    }

    @Override
    protected void loadData() {
        new Thread(){
            @Override
            public void run() {
                int N = IMAGES.length;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(i + 1);
                }
                mAdapter = new ImagePagerAdapter(getContext());
                mAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }
}
