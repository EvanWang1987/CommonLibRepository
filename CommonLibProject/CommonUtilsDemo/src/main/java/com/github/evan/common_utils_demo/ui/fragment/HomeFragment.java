package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.ui.deskIcon.DeskIconManager;
import com.github.evan.common_utils.ui.deskIcon.icons.AssIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.LaunchBaseIcon;
import com.github.evan.common_utils.ui.deskIcon.icons.RocketIcon;
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
        DeskIconManager.getInstance(getActivity()).prepare();
    }

    @OnClick({R.id.card_open_rocket, R.id.card_close_rocket})
    void onClick(View view){
        switch (view.getId()){
            case R.id.card_open_rocket:
                boolean prepared = DeskIconManager.getInstance(getContext()).isPrepared();
                if(!prepared){
                    DeskIconManager.getInstance(getContext()).prepare();
                }
                break;


            case R.id.card_close_rocket:
                DeskIconManager.getInstance(getContext()).release();
                break;
        }
    }
}
