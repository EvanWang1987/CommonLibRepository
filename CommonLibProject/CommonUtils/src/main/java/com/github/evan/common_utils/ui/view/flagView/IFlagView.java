package com.github.evan.common_utils.ui.view.flagView;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by Evan on 2017/11/10.
 */
public interface IFlagView<FlagType> {

    void updateFlagValue(FlagType flag);

    FlagType getLastFlagValue();

    void showFlag();

    void dismissFlag();

    boolean isFlagShowing();

    void setFlagLocation(FlagLocation location);

    FlagLocation getFlagLocation();

    void setFlagDrawable(Drawable drawable);

    void setFlagDrawable(@DrawableRes int resId);

    Drawable getFlagDrawable();

    void setFlagName();

    String getFlagName();


}
