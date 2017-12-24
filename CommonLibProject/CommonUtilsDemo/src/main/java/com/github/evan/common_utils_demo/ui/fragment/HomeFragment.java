package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/9.
 */
public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);
        loadData();
        return root;
    }

    @Override
    protected void loadData() {
        boolean prepared = DeskIconManager.getInstance(getContext()).isRocketPrepared();
        if(!prepared){
            DeskIconManager.getInstance(getContext()).prepareRocket();
        }
    }

    @OnClick({R.id.card_open_rocket, R.id.card_close_rocket})
    void onClick(View view){
        boolean prepared = DeskIconManager.getInstance(getContext()).isRocketPrepared();
        switch (view.getId()){
            case R.id.card_open_rocket:
                if(!prepared){
                    DeskIconManager.getInstance(getContext()).prepareRocket();
                }
                break;


            case R.id.card_close_rocket:
                if(prepared){
                    DeskIconManager.getInstance(getContext()).releaseRocket();
                }
                break;
        }
    }
}
