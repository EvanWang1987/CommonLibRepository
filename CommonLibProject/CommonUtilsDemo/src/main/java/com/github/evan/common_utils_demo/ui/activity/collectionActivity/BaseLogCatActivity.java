package com.github.evan.common_utils_demo.ui.activity.collectionActivity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import com.github.evan.common_utils.ui.activity.slideExitActivity.BaseSlideExitActivity;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitActivityConfig;
import com.github.evan.common_utils.ui.activity.slideExitActivity.SlideExitDirection;
import com.github.evan.common_utils.ui.view.LogCatView;
import com.github.evan.common_utils.ui.view.nestingTouchView.NestingScrollView;
import com.github.evan.common_utils.utils.ResourceUtil;
import com.github.evan.common_utils_demo.R;

/**
 * Created by Evan on 2017/12/25.
 */
public abstract class BaseLogCatActivity extends BaseSlideExitActivity {
    public abstract View onCreateSubView(LayoutInflater inflater);
    private LogCatView mLogCat;
    private NestingScrollView mScrollView;

    @Override
    public void onSlideExit(SlideExitDirection direction, View dst, Activity activity) {
        activity.finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.activity_base_collection, null);
        mLogCat = root.findViewById(R.id.log_cat_base_collection_activity);
        mScrollView = root.findViewById(R.id.nesting_scroll_view_base_collection_activity);
        mLogCat.setCloseButtonVisibility(View.GONE);
        View view = onCreateSubView(inflater);
        if(null != view){
            mScrollView.addView(view, new NestingScrollView.LayoutParams(NestingScrollView.LayoutParams.MATCH_PARENT, NestingScrollView.LayoutParams.MATCH_PARENT));
        }
        return root;
    }

    @Override
    public SlideExitActivityConfig onCreateConfig() {
        SlideExitActivityConfig config = new SlideExitActivityConfig();
        config.setBackgroundColor(ResourceUtil.getColor(com.github.evan.common_utils.R.color.White));
        config.setSlidingPercentRelativeActivityWhenNotExit(0.3f);
        config.setExitDirection(SlideExitDirection.LEFT_TO_RIGHT);
        config.setExitDuration(200);
        config.setRollBackDuration(300);
        return config;
    }

    protected void addLog(CharSequence log){
        mLogCat.addLog(log);
    }

    protected CharSequence getAllLog(){
        return mLogCat.getAllLog();
    }
}
