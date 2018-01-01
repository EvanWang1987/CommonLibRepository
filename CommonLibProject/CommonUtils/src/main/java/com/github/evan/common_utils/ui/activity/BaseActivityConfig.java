package com.github.evan.common_utils.ui.activity;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.utils.DateUtil;
import com.github.evan.common_utils.utils.ResourceUtil;

/**
 * Created by Evan on 2017/11/3.
 */

public class BaseActivityConfig {
    public boolean isPressTwiceToExit = false;
    public long pressTwiceIntervalTime = 2000L;
    public String pressTwiceIntervalNotifyText = ResourceUtil.getString(R.string.press_twice_notify_text, DateUtil.time2String(pressTwiceIntervalTime, DateUtil.mm_ss, ""));

}
