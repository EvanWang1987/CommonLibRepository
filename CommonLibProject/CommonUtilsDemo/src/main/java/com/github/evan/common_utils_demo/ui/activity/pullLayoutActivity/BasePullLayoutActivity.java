package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.content.DialogInterface;
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
import com.github.evan.common_utils.ui.view.pullable_view.PullChecker;
import com.github.evan.common_utils.ui.view.pullable_view.PullDirection;
import com.github.evan.common_utils.ui.view.pullable_view.PullLayout;
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

    protected PullLayout mPullLayout;
    private FrameLayout mMainLayout;
    private FloatingActionButton mBtnInvoke, mBtnInvokeSecond, mBtnDone;
    private AlertDialog mChoiceDirectionDialog;
    private AlertDialog mChoiceIndicatorDialog;
    private AlertDialog mCanScrollOverstepIndicatorDialog;
    private String[] mDirectionArgs;
    private String[] mIndicatorArgs;
    private String[] mCanScrollOverstepIndicatorArgs;

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
        mDirectionArgs = getResources().getStringArray(R.array.pull_layout_direction);
        mIndicatorArgs = getResources().getStringArray(R.array.pull_layout_indicator_style);
        mCanScrollOverstepIndicatorArgs = getResources().getStringArray(R.array.can_scroll_overstep_indicator);
        mChoiceDirectionDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_pull_direction).setSingleChoiceItems(mDirectionArgs, -1, this).create();
        mChoiceIndicatorDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_pull_indicator).setSingleChoiceItems(mIndicatorArgs, -1, this).create();
        mCanScrollOverstepIndicatorDialog = new AlertDialog.Builder(this).setTitle(R.string.choice_can_scroll_overstep_indicator).setSingleChoiceItems(mCanScrollOverstepIndicatorArgs, -1, this).create();
        mChoiceDirectionDialog.setCancelable(false);
        mChoiceIndicatorDialog.setCancelable(false);
        mCanScrollOverstepIndicatorDialog.setCancelable(false);
        mChoiceDirectionDialog.setCanceledOnTouchOutside(false);
        mChoiceIndicatorDialog.setCanceledOnTouchOutside(false);
        mCanScrollOverstepIndicatorDialog.setCanceledOnTouchOutside(false);
        mPullLayout = new PullLayout(this);
        mPullLayout.setPullChecker(this);
        mChoiceDirectionDialog.show();
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
    public void onClick(DialogInterface dialog, int which){
        if(dialog == mChoiceDirectionDialog){
            mPullDirection = onSelectedPullDirection(mDirectionArgs[which]);
            mPullLayout.setPullDirection(mPullDirection);
            dialog.dismiss();
            mCanScrollOverstepIndicatorDialog.show();
        }else if(dialog == mCanScrollOverstepIndicatorDialog){
            mCanScrollOverstepIndicator = canScrollOverstepIndicator(mCanScrollOverstepIndicatorArgs[which]);
            mPullLayout.setCanScrollOverstepIndicator(mCanScrollOverstepIndicator);
            dialog.dismiss();
            mChoiceIndicatorDialog.show();
        }else if(dialog == mChoiceIndicatorDialog){
            mIndicators = onSelectedIndicator(mIndicatorArgs[which]);
            IIndicator firstIndicator = null;
            IIndicator secondIndicator = null;
            switch (mPullDirection){
                case TOP_TO_BOTTOM:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    break;

                case BOTTOM_TO_TOP:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    break;

                case BOTH_TOP_BOTTOM:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    secondIndicator = mIndicators[1];
                    secondIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    mPullLayout.replaceSecondIndicator(secondIndicator, false);
                    break;

                case LEFT_TO_RIGHT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    break;

                case RIGHT_TO_LEFT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    break;

                case BOTH_LEFT_RIGHT:
                    firstIndicator = mIndicators[0];
                    firstIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    secondIndicator = mIndicators[1];
                    secondIndicator.getIndicatorView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
                    mPullLayout.replaceFirstIndicator(firstIndicator, false);
                    mPullLayout.replaceSecondIndicator(secondIndicator, false);
                    break;
            }
            View view = onCreateView(LayoutInflater.from(this));
            view.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
            mPullLayout.replaceContentView(view, true);
            mMainLayout.addView(mPullLayout, 0, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

    public PullDirection onSelectedPullDirection(String args){
        PullDirection direction;
        switch (args){
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

    public boolean canScrollOverstepIndicator(String args){
        boolean returnValue = true;
        switch (args){
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

    public IIndicator[] onSelectedIndicator(String args){
        IIndicator[] indicators = null;
        switch (mPullDirection){
            default:
            case TOP_TO_BOTTOM:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this)};
                }
                break;

            case BOTTOM_TO_TOP:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this)};
                }
                break;

            case LEFT_TO_RIGHT:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this)};
                }
                break;

            case RIGHT_TO_LEFT:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this)};
                }
                break;

            case BOTH_TOP_BOTTOM:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshIndicator(this), new ClassicRefreshIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshIndicator(this), new ClassicProRefreshIndicator(this)};
                }
                break;

            case BOTH_LEFT_RIGHT:
                if(args.equals("经典样式")){
                    indicators = new IIndicator[]{new ClassicRefreshHorIndicator(this), new ClassicRefreshHorIndicator(this)};
                }else if(args.equals("经典加强样式")){
                    indicators = new IIndicator[]{new ClassicProRefreshHorIndicator(this), new ClassicRefreshHorIndicator(this)};
                }
                break;

        }
        return indicators;
    }

}
