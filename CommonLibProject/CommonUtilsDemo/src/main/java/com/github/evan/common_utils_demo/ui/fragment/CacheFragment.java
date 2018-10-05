package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutCache.LRUCacheActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/10/5.
 */

public class CacheFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cache, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.card_lru_cache, R.id.card_image_cache})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_lru_cache:
                loadActivity(LRUCacheActivity.class);
                break;

            case R.id.card_image_cache:

                break;
        }
    }
}
