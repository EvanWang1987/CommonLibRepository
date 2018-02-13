package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.view.pullable_view.PullDirection;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicProRefreshHorIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicProRefreshIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicRefreshHorIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.ClassicRefreshIndicator;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.IIndicator;
import com.github.evan.common_utils_demo.R;

import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/2/13.
 */

public class PullWithViewGroupActivity extends BasePullLayoutActivity {

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_pull_layout_with_view_group, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public PullDirection onSelectedPullDirection(String args) {
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

    @Override
    public IIndicator[] onSelectedIndicator(String args) {
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

    @Override
    public boolean canScrollOverstepIndicator(String args) {
        return true;
    }

    @Override
    public void onAutoInvokeButtonClick(View view) {
        mPullLayout.autoInvoke(false, 800);
    }

    @Override
    public void onAutoInvokeSecondIndicatorButtonClick(View view) {
        mPullLayout.autoInvoke(true, 800);
    }

    @Override
    public void onInvokingCompleteButtonClick(View view) {
        mPullLayout.invokeComplete();
    }

}
