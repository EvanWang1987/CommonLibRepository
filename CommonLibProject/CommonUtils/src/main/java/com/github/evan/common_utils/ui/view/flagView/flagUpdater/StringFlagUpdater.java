package com.github.evan.common_utils.ui.view.flagView.flagUpdater;

import com.github.evan.common_utils.utils.SpUtil;
import com.github.evan.common_utils.utils.StringUtil;

/**
 * Created by Evan on 2017/11/10.
 */
public class StringFlagUpdater extends BaseFlagUpdater<String> {

    public StringFlagUpdater(String flagName) {
        super(flagName);
    }

    @Override
    public boolean saveFlag(String flag) {
        SpUtil spUtil = getSpUtil();
        return spUtil.commitString(getFlagName(), flag, false, FLAG_UPDATER_SP_NAME);
    }

    @Override
    public String getFlag() {
        SpUtil spUtil = getSpUtil();
        return spUtil.getString(getFlagName(), null, false, FLAG_UPDATER_SP_NAME);
    }

    @Override
    public boolean isFlagChange(String currentFlag) {
        String savedFlag = getFlag();
        return StringUtil.equals(currentFlag, savedFlag, false);
    }

    @Override
    public boolean isFlagChange(String currentFlag, boolean isOverWriteFlag) {
        boolean isChanged = isFlagChange(currentFlag);
        if(isChanged){
            boolean isSaved = saveFlag(currentFlag);
            if(isSaved){
                return true;
            }else{
                throw new IllegalArgumentException("数据保存失败");
            }
        }
        return false;
    }


}
