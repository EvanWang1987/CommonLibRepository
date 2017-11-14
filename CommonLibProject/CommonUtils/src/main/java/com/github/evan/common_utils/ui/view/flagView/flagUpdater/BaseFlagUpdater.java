package com.github.evan.common_utils.ui.view.flagView.flagUpdater;

import com.github.evan.common_utils.utils.SpUtil;
import com.github.evan.common_utils.utils.StringUtil;

/**
 * Created by Evan on 2017/11/10.
 */
public abstract class BaseFlagUpdater<FlagDataType> {
    protected static final String FLAG_UPDATER_SP_NAME = "flag_updater";
    private String mFlagName;
    private SpUtil mSpUtil;

    public BaseFlagUpdater(String flagName) {
        mFlagName = flagName;
        if(StringUtil.isEmptyString(flagName, true)){
            throw new IllegalArgumentException("Flag name can not be empty or null.");
        }
        mSpUtil = SpUtil.getIns();
        boolean isInitSuccess = mSpUtil.initSharedPreference(false, FLAG_UPDATER_SP_NAME);
        if(!isInitSuccess){
            throw new RuntimeException("Flag updater init fail!");
        }
    }

    protected String getFlagName(){
        return mFlagName;
    }

    protected SpUtil getSpUtil(){
        return mSpUtil;
    }

    public abstract boolean saveFlag(FlagDataType flag);

    public abstract FlagDataType getFlag();

    public abstract boolean isFlagChange(FlagDataType currentFlag);

    public abstract boolean isFlagChange(FlagDataType currentFlag, boolean isOverWriteFlagWhenFlagChanged);

}
