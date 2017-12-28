package com.github.evan.common_utils_demo.ui.fragment.fragmentBackStack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.aboutFragment.FragmentBackStackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2017/12/27.
 */
public class FragmentC extends BaseFragment {
    @BindView(R.id.txt_extra_fragment_c)
    TextView mTxtExtra;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_back_stack_c, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if(null != arguments){
            String extraString = arguments.getString(FragmentBackStackActivity.EXTRA_FROM_LAST_FRAGMENT, "上级Fragment未携带数据");
            if(!StringUtil.isEmpty(extraString)){
                mTxtExtra.setText(extraString);
            }
        }
    }

    @Override
    protected void loadData() {

    }

}
