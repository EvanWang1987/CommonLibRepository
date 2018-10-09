package com.github.evan.common_utils.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/11/11.
 */

public class FragmentUtil {

    /**
     * 所有Fragment都添加到Activity中，并默认显示目标Fragment
     * @param activity
     * @param fragmentManager
     * @param fragmentContainerId
     * @param fragmentNames
     * @param fragmentTags
     * @param showingFragmentTag
     */
    public static void addAllFragmentAndShowSomeOne(Activity activity, FragmentManager fragmentManager, @IdRes int fragmentContainerId, String[] fragmentNames, boolean isUseFragmentNameReplaceTag, String[] fragmentTags, String showingFragmentTag){
        if(null == fragmentNames || fragmentNames.length == 0){
            return;
        }

        if(!isUseFragmentNameReplaceTag && ((null == fragmentTags || fragmentTags.length == 0) || fragmentNames.length != fragmentTags.length)){
            return;
        }

        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        int N = fragmentNames.length;
        for (int index = 0; index < N; index++) {
            String fragmentName = fragmentNames[index];
            String fragmentTag = isUseFragmentNameReplaceTag ? fragmentName : fragmentTags[index];
            Fragment fragment = Fragment.instantiate(activity, fragmentName);
            beginTransaction.add(fragmentContainerId, fragment, fragmentTag);
            boolean isShowingFragment = StringUtil.equals(showingFragmentTag, fragmentTag, false);
            if(isShowingFragment){
                beginTransaction.show(fragment);
            }
            else {
                beginTransaction.hide(fragment);
            }
        }
        beginTransaction.commit();
    }

    public static void switchFragment(FragmentManager fragmentManager, List<String> tags, String whichWillBeShowing){
        if(null == fragmentManager || null == tags || tags.size() <= 0 || StringUtil.isEmpty(whichWillBeShowing)){
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0, length = tags.size(); i < length; i++) {
            String tag = tags.get(i);
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if(null != fragment){
                if(tag.equals(whichWillBeShowing)){
                    fragmentTransaction.show(fragment);
                }else{
                    fragmentTransaction.hide(fragment);
                }
            }
        }

        fragmentTransaction.commit();
    }
}
