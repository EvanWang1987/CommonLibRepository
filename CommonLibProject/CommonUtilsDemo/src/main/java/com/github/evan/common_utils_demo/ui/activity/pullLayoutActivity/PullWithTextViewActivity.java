package com.github.evan.common_utils_demo.ui.activity.pullLayoutActivity;

import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.evan.common_utils.ui.view.pullable_view.PullChecker;
import com.github.evan.common_utils.ui.view.pullable_view.PullDirection;
import com.github.evan.common_utils.ui.view.pullable_view.indicator.IIndicator;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2018/2/12.
 */
public class PullWithTextViewActivity extends BasePullLayoutActivity {

    @Override
    public boolean checkCanPull(boolean isTop2BottomSlide, boolean isBottom2TopSlide, boolean isLeft2RightSlide, boolean isRight2LeftSlide) {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        TextView textView = new TextView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(getResources().getColor(com.github.evan.common_utils.R.color.text_color_black));
        textView.getPaint().setFakeBoldText(true);
        textView.setGravity(Gravity.CENTER);
        textView.setText(R.string.with_text_view);
        textView.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.white)));
        return textView;
    }

    @Override
    public void onAutoInvokeButtonClick(View view) {
        if(mIsFloatingPullLayout){
            mFloatingPullLayout.autoInvoke(false, 800);
        }else{
            mPullLayout.autoInvoke(false, 800);
        }
    }

    @Override
    public void onAutoInvokeSecondIndicatorButtonClick(View view) {
        if(mIsFloatingPullLayout){
            mFloatingPullLayout.autoInvoke(true, 800);
        }else{
            mPullLayout.autoInvoke(true, 800);
        }
    }

    @Override
    public void onInvokingCompleteButtonClick(View view) {
        mPullLayout.invokeComplete();
    }
}
