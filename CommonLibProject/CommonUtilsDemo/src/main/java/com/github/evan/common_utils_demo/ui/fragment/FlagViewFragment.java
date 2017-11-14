package com.github.evan.common_utils_demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.ui.view.flagView.TimeFlagTextView;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/11.
 */
public class FlagViewFragment extends BaseFragment {
    @BindView(R.id.txtWithLeftTopTimeFlag)
    TimeFlagTextView mTxtWithLeftTopTimeFlag;
    @BindView(R.id.txtWithLeftBottomTimeFlag)
    TimeFlagTextView mTxtWithLeftBottomTimeFlag;
    @BindView(R.id.txtWithRightTopTimeFlag)
    TimeFlagTextView mTxtWithRightTopTimeFlag;
    @BindView(R.id.txtWithRightBottomTimeFlag)
    TimeFlagTextView mTxtWithRightBottomTimeFlag;

    @BindView(R.id.txtUpdateTime)
    TextView mTxtUpdateTime;


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flag_view, null);
        ButterKnife.bind(this, root);
        updateLastFlagUpdateTime();
        return root;
    }

    @OnClick({R.id.btnClickShowTimeFlag, R.id.btnClickDismissTimeFlag, R.id.btnClickUpdateFlag2CurrentTime, R.id.btnClickLastUpdateTime})
    void onUpdateTimeFlagButtonClick(View v) {
        switch (v.getId()){
            case R.id.btnClickShowTimeFlag:
                showTimeFlagDelay();
                break;

            case R.id.btnClickDismissTimeFlag:
                dismissTimeFlag();
                break;

            case R.id.btnClickUpdateFlag2CurrentTime:
                updateFlag2CurrentTimeDelay();
                break;

            case R.id.btnClickLastUpdateTime:
                updateLastFlagUpdateTime();
                break;
        }
    }

    private void updateFlag2CurrentTimeDelay(){
        postDelay(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                String timeString = DateUtil.time2String(currentTimeMillis, DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                mTxtWithLeftTopTimeFlag.updateFlagValue(currentTimeMillis);
                mTxtWithLeftBottomTimeFlag.updateFlagValue(currentTimeMillis);
                mTxtWithRightTopTimeFlag.updateFlagValue(currentTimeMillis);
                mTxtWithRightBottomTimeFlag.updateFlagValue(currentTimeMillis);

                mTxtUpdateTime.setText(ResourceUtil.getString(R.string.currentUpdateTime, timeString));
                Toast.makeText(getContext(), "所有时间标记已经更新为: " + timeString, Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }

    private void showTimeFlagDelay(){
        mTxtWithLeftTopTimeFlag.showFlag();
        mTxtWithLeftBottomTimeFlag.showFlag();
        mTxtWithRightTopTimeFlag.showFlag();
        mTxtWithRightBottomTimeFlag.showFlag();
    }

    private void dismissTimeFlag(){
        mTxtWithLeftTopTimeFlag.dismissFlag();
        mTxtWithLeftBottomTimeFlag.dismissFlag();
        mTxtWithRightTopTimeFlag.dismissFlag();
        mTxtWithRightBottomTimeFlag.dismissFlag();
    }

    private void updateLastFlagUpdateTime() {
        Long lastFlagValue = mTxtWithLeftTopTimeFlag.getLastFlagValue();
        String timeString = DateUtil.time2String(lastFlagValue, DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
        mTxtUpdateTime.setText(ResourceUtil.getString(R.string.lastUpdateTime, timeString));
    }
}
