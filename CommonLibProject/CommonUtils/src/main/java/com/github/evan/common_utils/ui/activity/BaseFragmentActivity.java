package com.github.evan.common_utils.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.github.evan.common_utils.ui.fragment.FragmentProvider;
import com.github.evan.common_utils.utils.FragmentUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evan on 2018/9/10.
 */
public abstract class BaseFragmentActivity extends BaseActivity implements ActivityProvider{
    private Map<String, FragmentProvider> mFragments = new HashMap<>();
    private List<String> mFragmentTags = new ArrayList<>();
    private String mShowingFragmentTag;
    private FragmentManager mFragmentManager;
    private FragmentProvider mCurrentShowDialogFragment;

    protected abstract List<String> getFragmentName();
    protected abstract String showWhichFragmentWhenInitialized();
    protected abstract @IdRes int getFragmentContainerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        initFragment();
    }

    private void initFragment(){
        int fragmentContainerId = getFragmentContainerId();
        List<String> fragmentNames = getFragmentName();
        String whichWillBeShowing = showWhichFragmentWhenInitialized();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (int i = 0, length = fragmentNames.size(); i < length; i++) {
            String fragmentName = fragmentNames.get(i);
            Fragment fragment = Fragment.instantiate(this, fragmentName);
            if(fragment instanceof FragmentProvider){
                FragmentProvider fragmentProvider = (FragmentProvider) fragment;
                fragmentProvider.setActivityProvider(this);
                mFragments.put(fragmentName, fragmentProvider);
                fragmentTransaction.add(fragmentContainerId, fragment, fragmentName);
                if(!fragmentName.equals(whichWillBeShowing)){
                    fragmentTransaction.hide(fragment);
                }else{
                    fragmentTransaction.show(fragment);
                    mShowingFragmentTag = fragmentName;
                }
            }else{
                throw new RuntimeException("Fragment must be FragmentProvider");
            }
        }
        fragmentTransaction.commit();
        mFragmentTags.clear();
        mFragmentTags.addAll(fragmentNames);
    }

    @Override
    public void switchFragment(String tagWhichBeShowing){
        FragmentUtil.switchFragment(mFragmentManager, mFragmentTags, tagWhichBeShowing);
        mShowingFragmentTag = tagWhichBeShowing;
    }

    public Collection<FragmentProvider> getmFragments() {
        return mFragments.values();
    }

    public List<String> getmFragmentTags() {
        return mFragmentTags;
    }

    public String getmShoowingFragmentTag() {
        return mShowingFragmentTag;
    }

    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    @Override
    public void showDialog(FragmentProvider currentFragment, DialogMode dialogMode, CharSequence title, CharSequence message, int icon, CharSequence okMessage, CharSequence cancelMessage, CharSequence hint[], int editTextCount, int lines[], int maxProgress, int progress, int progressStyle) {
        dismissDialogsFromActivityProvider();
        mCurrentShowDialogFragment = currentFragment;
        switch (dialogMode){
            case INPUT_DIALOG:
                showInputDialog(title, hint, editTextCount, lines, okMessage, cancelMessage);
                break;

            case MESSAGE_DIALOG:
                showMessageDialog(title, message, okMessage, cancelMessage);
                break;

            case PROGRESS_DIALOG:
                showProgressDialog(title, message, progressStyle, maxProgress, progress);
                break;

            default:
                mCurrentShowDialogFragment = null;
                break;
        }
    }

    @Override
    public void dismissDialogsFromActivityProvider() {
        dismissAllDialog();
    }

    @Override
    public void updateProgressDialogFromActivityProvider(int progress) {
        updateProgressDialog(progress);
    }

    @Override
    public void onDialogConfirmButtonClick(DialogInterface dialog, DialogMode mode) {
        if(mCurrentShowDialogFragment != null){
            mCurrentShowDialogFragment.onDialogConfirmButtonClick(dialog, mode);
            mCurrentShowDialogFragment = null;
            return;
        }
        super.onDialogConfirmButtonClick(dialog, mode);
    }

    @Override
    public void onDialogCancelButtonClick(DialogInterface dialog, DialogMode mode) {
        if(mCurrentShowDialogFragment != null){
            mCurrentShowDialogFragment.onDialogCancelButtonClick(dialog, mode);
            mCurrentShowDialogFragment = null;
            return;
        }
        super.onDialogCancelButtonClick(dialog, mode);
    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return null;
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0, length = mFragments.size(); i < length; i++) {
            Fragment fragment = mFragments.get(i).getFragment();
            fragments.add(fragment);
        }
        return fragments;
    }

    @Override
    public List<FragmentProvider> getFragmentProviders() {
        return new ArrayList<>(mFragments.values());
    }

    @Override
    public Fragment getFragment(String tag) {
        FragmentProvider fragmentProvider = mFragments.get(tag);
        return null != fragmentProvider ? fragmentProvider.getFragment() : null;
    }

    @Override
    public FragmentProvider getFragmentProvider(String tag) {
        return mFragments.get(tag);
    }

    @Override
    public BaseFragmentActivity getHostActivity() {
        return this;
    }
}
