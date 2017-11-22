package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.GridViewAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.SingleStyleItemAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.ThreeStyleItemAdapter;
import com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter.TwoStyleItemAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Evan on 2017/11/20.
 */
public class ListGridViewFragment extends BaseFragment {
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_btn_single_style_adapter)
    RadioButton mBtnSingleAdapter;
    @BindView(R.id.radio_btn_two_style_adapter)
    RadioButton mBtnTwoStyleAdapter;
    @BindView(R.id.radio_btn_three_style_adapter)
    RadioButton mBtnThreeStyleAdapter;
    @BindView(R.id.radio_btn_grid_view)
    RadioButton mBtnGridView;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private SingleStyleItemAdapter mSingleStyleItemAdapter;
    private TwoStyleItemAdapter mTwoStyleItemAdapter;
    private ThreeStyleItemAdapter mThreeStyleItemAdapter;
    private GridViewAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_grid_view, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroy() {
        mSingleStyleItemAdapter.recycle();
        mTwoStyleItemAdapter.recycle();
        mThreeStyleItemAdapter.recycle();
        mGridAdapter.recycle();
        super.onDestroy();
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message.what == LOAD_COMPLETE){
            mBtnSingleAdapter.setChecked(true);
        }
    }

    @OnCheckedChanged({R.id.radio_btn_single_style_adapter, R.id.radio_btn_two_style_adapter, R.id.radio_btn_three_style_adapter, R.id.radio_btn_grid_view})
    void onCheckedChanged(CompoundButton button){
        if(button.isChecked()){{
            switch (button.getId()){
                case R.id.radio_btn_single_style_adapter:
                    mListView.setAdapter(mSingleStyleItemAdapter);
                    mListView.setVisibility(View.VISIBLE);
                    mGridView.setVisibility(View.INVISIBLE);
                    break;

                case R.id.radio_btn_two_style_adapter:
                    mListView.setAdapter(mTwoStyleItemAdapter);
                    mListView.setVisibility(View.VISIBLE);
                    mGridView.setVisibility(View.INVISIBLE);
                    break;

                case R.id.radio_btn_three_style_adapter:
                    mListView.setAdapter(mThreeStyleItemAdapter);
                    mListView.setVisibility(View.VISIBLE);
                    mGridView.setVisibility(View.INVISIBLE);
                    break;

                case R.id.radio_btn_grid_view:
                    mGridView.setAdapter(mGridAdapter);
                    mGridView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.INVISIBLE);
                    break;
            }
        }}
    }

    @Override
    protected void loadData() {
        new Thread(){
            @Override
            public void run() {
                int N = 30;
                List<Integer> data = new ArrayList<>(N);
                for (int i = 0; i < N; i++) {
                    data.add(i + 1);
                }
                mSingleStyleItemAdapter = new SingleStyleItemAdapter(getContext());
                mSingleStyleItemAdapter.replace(data);
                mTwoStyleItemAdapter = new TwoStyleItemAdapter(getContext());
                mTwoStyleItemAdapter.replace(data);
                mThreeStyleItemAdapter = new ThreeStyleItemAdapter(getContext());
                mThreeStyleItemAdapter.replace(data);
                mGridAdapter = new GridViewAdapter(getContext());
                mGridAdapter.replace(data);
                sendEmptyMessage(LOAD_COMPLETE);
            }
        }.start();
    }
}
