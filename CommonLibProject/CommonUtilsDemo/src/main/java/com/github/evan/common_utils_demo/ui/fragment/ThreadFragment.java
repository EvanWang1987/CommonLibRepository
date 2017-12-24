package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/24.
 */
public class ThreadFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thread, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_single_producer_consumer, R.id.card_multi_producer_consumer, R.id.card_dead_lock})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_single_producer_consumer:

                break;

            case R.id.card_multi_producer_consumer:

                break;

            case R.id.card_dead_lock:

                break;
        }
    }

    private void deadLockDemo(){

    }

}
