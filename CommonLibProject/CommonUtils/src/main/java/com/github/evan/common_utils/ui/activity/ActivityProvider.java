package com.github.evan.common_utils.ui.activity;


import android.support.v4.app.Fragment;

import com.github.evan.common_utils.ui.fragment.FragmentProvider;
import java.util.List;

/**
 * Created by Evan on 2018/9/10.
 */
public interface ActivityProvider {

    List<Fragment> getFragments();

    List<FragmentProvider> getFragmentProviders();

    Fragment getFragment(String tag);

    FragmentProvider getFragmentProvider(String tag);

    BaseFragmentActivity getHostActivity();


}
