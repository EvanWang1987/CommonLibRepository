package com.github.evan.common_utils.ui.view.flagView.flagUpdater;

/**
 * Created by Evan on 2017/11/10.
 */
public class TimeFlagUpdater extends BaseFlagUpdater<Long> {

    public TimeFlagUpdater(String flagName) {
        super(flagName);
    }

    @Override
    public boolean saveFlag(Long flag) {
        return getSpUtil().commitLong(getFlagName(), flag, false, FLAG_UPDATER_SP_NAME);
    }

    @Override
    public Long getFlag() {
        return getSpUtil().getLong(getFlagName(), -1, false, FLAG_UPDATER_SP_NAME);
    }

    @Override
    public boolean isFlagChange(Long currentFlag) {
        long savedTime = getSpUtil().getLong(getFlagName(), -1, false, FLAG_UPDATER_SP_NAME);
        return currentFlag > savedTime;
    }

    @Override
    public boolean isFlagChange(Long currentFlag, boolean isOverWriteFlagWhenFlagChanged) {
        boolean isFlagChanged = isFlagChange(currentFlag);
        if(isFlagChanged){
            boolean isSaved = saveFlag(currentFlag);
            if(isSaved){
                return true;
            }else{
                throw new IllegalArgumentException("Over write flag fail!");
            }
        }
        return false;
    }
}
