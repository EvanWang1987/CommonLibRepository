package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.evan.common_utils.ui.activity.BaseActivity;
import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils.ui.view.pullable_view.FloatingPullLayout;
import com.github.evan.common_utils.ui.view.pullable_view.PullChecker;
import com.github.evan.common_utils.ui.view.pullable_view.PullDirection;
import com.github.evan.common_utils.ui.view.pullable_view.PullLayout;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicFloatingRefreshHorIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicFloatingRefreshIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicProRefreshHorIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicProRefreshIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicRefreshHorIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicRefreshIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.IIndicator;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/2/13.
 */
public abstract class BasePullLayoutActivity extends BaseActivity implements DialogInterface.OnClickListener, View.OnClickListener, PullChecker {
    public abstract View onCreateView(LayoutInflater inflater);

    public abstract void onAutoInvokeButtonClick(View view);

    public abstract void onAutoInvokeSecondIndicatorButtonClick(View view);

    public abstract void onInvokingCompleteButtonClick(View view);

    protected PullDirection mPullDirection;
    protected IIndicator[] mIndicators;
    protected boolean mCanScrollOverstepIndicator;
    protected boolean mIsFloatingPullLayout;
    protected PullLayout mPullLayout;
    protected FloatingPullLayout mFloatingPullLayout;
    private FrameLayout mMainLayout;
    private FloatingActionButton mBtnInvoke, mBtnInvokeSecond, mBtnDone;
    private AlertDialog mChoiceFloatingDialog;
    private AlertDialog mChoiceDirectionDialog;
    private AlertDialog mChoiceIndicatorDialog;
    private AlertDialog mCanScrollOverstepIndicatorDialog;
    private String[] mDirectionArgs;
    private String[] mIndicatorArgs;
    private String[] mCanScrollOverstepIndicatorArgs;
    private String[] mFloatingArgs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLayout = (FrameLayout) findViewById(R.id.main_layout_base_pull_layout_activity);
        mBtnInvoke = (FloatingActionButton) findViewById(R.id.btn_invoke);
        mBtnInvokeSecond = (FloatingActionButton) findViewById(R.id.btn_invoke_second);
        mBtnDone = (FloatingActionButton) findViewById(R.id.btn_invoking_complete);
        mBtnInvoke.setOnClickListener(this);
        mBtnInvokeSecond.setOnClickListener(this);
        mBtnDone.setOnClickListener(this);
        mFloatingArgs = getResources().getStringArray(R.array.is_pull_layout_floating);
        mDirectionArgs = getResources().getStringArray(R.array.pull_layout_direction);
        mIndicatorArgs = getResources().getStringArray(R.array.pull_layout_indicator_style);
        mCanScrollOverstepIndicatorArgs = getResources().getStringArray(R.array.can_scroll_overstep_indicator);
        mChoiceFloatingDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_is_pull_layout_floating).setSingleChoiceItems(mFloatingArgs, -1, this).create();
        mChoiceDirectionDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_pull_direction).setSingleChoiceItems(mDirectionArgs, -1, this).create();
        mChoiceIndicatorDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_pull_indicator).setSingleChoiceItems(mIndicatorArgs, -1, this).create();
        mCanScrollOverstepIndicatorDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_can_scroll_overstep_indicator).setSingleChoiceItems(mCanScrollOverstepIndicatorArgs, -1, this).create();
        mChoiceFloatingDialog.setCancelable(false);
        mChoiceDirectionDialog.setCancelable(false);
        mChoiceIndicatorDialog.setCancelable(false);
        mCanScrollOverstepIndicatorDialog.setCancelable(false);
        mChoiceFloatingDialog.setCanceledOnTouchOutside(false);
        mChoiceDirectionDialog.setCanceledOnTouchOutside(false);
        mChoiceIndicatorDialog.setCanceledOnTouchOutside(false);
        mCanScrollOverstepIndicatorDialog.setCanceledOnTouchOutside(false);
        mChoiceFloatingDialog.show();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_pull_layout;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialog == mChoiceFloatingDialog) {
            String mFloatingArg = mFloatingArgs[which];
            if(mFloatingArg.equals("悬浮")){
                mIsFloatingPullLayout = true;
                mFloatingPullLayout = new FloatingPullLayout(this);
                mFloatingPullLayout.setPullChecker(this);
            }else{
                //不悬浮
                mIsFloatingPullLayout = false;
                mPullLayout = new PullLayout(this);
                mPullLayout.setPullChecker(this);
            }
            dialog.dismiss();
            mChoiceDirectionDialog.show();
        } else if (dialog == mChoiceDirectionDialog) {
            mPullDirection = onSelectedPullDirection(mDirectionArgs[which]);
            if(mIsFloatingPullLayout){
                mFloatingPullLayout.setPullDirection(mPullDirection);
            }else{
                mPullLayout.setPullDirection(mPullDirection);
            }
            dialog.dismiss();
            mCanScrollOverstepIndicatorDialog.show();
        } else if (dialog == mCanScrollOverstepIndicatorDialog) {
            mCanScrollOverstepIndicator = canScrollOverstepIndicator(mCanScrollOverstepIndicatorArgs[which]);
            if(mIsFloatingPullLayout){
                mFloatingPullLayout.setCanScrollOverstepIndicator(mCanScrollOverstepIndicator);
            }else{
                mPullLayout.setCanScrollOverstepIndicator(mCanScrollOverstepIndicator);
            }
            dialog.dismiss();
            mChoiceIndicatorDialog.show();
        } else if (dialog == mChoiceIndicatorDialog) {
            mIndicators = onSelectedIndicator(mIndicatorArgs[which]);
            IIndicator firstIndicator = null;
            IIndicator secondIndicator = null;
            switch (mPullDirection) {
                case TOP_TO_BOTTOM:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }
                    break;

                case BOTTOM_TO_TOP:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }
                    break;

                case BOTH_TOP_BOTTOM:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    secondIndicator = mIndicators[1];
                    secondIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                        mFloatingPullLayout.replaceSecondIndicator(secondIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                        mPullLayout.replaceSecondIndicator(secondIndicator, false);
                    }

                    break;

                case LEFT_TO_RIGHT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }
                    break;

                case RIGHT_TO_LEFT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    }
                    break;

                case BOTH_LEFT_RIGHT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    secondIndicator = mIndicators[1];
                    secondIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    if(mIsFloatingPullLayout){
                        mFloatingPullLayout.replaceFirstIndicator(firstIndicator, false);
                        mFloatingPullLayout.replaceSecondIndicator(secondIndicator, false);
                    }else{
                        mPullLayout.replaceFirstIndicator(firstIndicator, false);
                        mPullLayout.replaceSecondIndicator(secondIndicator, false);
                    }
                    break;
            }
            View view = onCreateView(LayoutInflater.from(this));
            view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
            if(mIsFloatingPullLayout){
                mFloatingPullLayout.replaceContentView(view, true);
            }else{
                mPullLayout.replaceContentView(view, true);
            }
            mMainLayout.addView(mIsFloatingPullLayout ? mFloatingPullLayout : mPullLayout, 0, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_invoke:
                onAutoInvokeButtonClick(v);
                break;

            case R.id.btn_invoke_second:
                onAutoInvokeSecondIndicatorButtonClick(v);
                break;

            case R.id.btn_invoking_complete:
                onInvokingCompleteButtonClick(v);
                break;
        }
    }

    public PullDirection onSelectedPullDirection(String args) {
        PullDirection direction;
        switch (args) {
            default:
            case "从上向下":
                direction = PullDirection.TOP_TO_BOTTOM;
                break;

            case "从下向上":
                direction = PullDirection.BOTTOM_TO_TOP;
                break;

            case "上下双方向":
                direction = PullDirection.BOTH_TOP_BOTTOM;
                break;

            case "从左向右":
                direction = PullDirection.LEFT_TO_RIGHT;
                break;

            case "从右向左":
                direction = PullDirection.RIGHT_TO_LEFT;
                break;

            case "左右双方向":
                direction = PullDirection.BOTH_LEFT_RIGHT;
                break;
        }
        return direction;
    }

    public boolean canScrollOverstepIndicator(String args) {
        boolean returnValue = true;
        switch (args) {
            default:
            case "可以":
                returnValue = true;
                break;

            case "禁止":
                returnValue = false;
                break;
        }

        return returnValue;
    }

    public IIndicator[] onSelectedIndicator(String args) {
        IIndicator[] indicators = null;
        switch (mPullDirection) {
            default:
            case TOP_TO_BOTTOM:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshIndicator(this)};
                }
                break;

            case BOTTOM_TO_TOP:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshIndicator(this)};
                }
                break;

            case LEFT_TO_RIGHT:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshHorIndicator(this)};
                }
                break;

            case RIGHT_TO_LEFT:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshHorIndicator(this)};
                }
                break;

            case BOTH_TOP_BOTTOM:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this), new ClassicRefreshIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this), new ClassicProRefreshIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshIndicator(this), new ClassicFloatingRefreshIndicator(this)};
                }
                break;

            case BOTH_LEFT_RIGHT:
                if (args.equals("经典样式")) {
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this), new ClassicRefreshHorIndicator(this)};
                } else if (args.equals("经典加强样式")) {
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this), new ClassicRefreshHorIndicator(this)};
                }else if(args.equals("IOS样式")){
                    indicators = new IIndicator[]{new ClassicFloatingRefreshHorIndicator(this), new ClassicFloatingRefreshHorIndicator(this)};
                }
                break;

        }
        return indicators;
    }

}
