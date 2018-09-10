package com.github.evan.common_utils.ui.fragment;


import android.support.v4.app.Fragment;

import com.github.evan.common_utils.ui.activity.ActivityProvider;

/**
 * Created by Evan on 2018/9/10.
 */
public interface FragmentProvider {

    BaseFragment getFragment();

    void setActivityProvider(ActivityProvider activityProvider);

    ActivityProvider getActivityProvider();

}
