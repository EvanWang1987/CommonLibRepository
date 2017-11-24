package com.github.evan.common_utils.ui.view.viewPager;

import com.github.evan.common_utils.gesture.TouchEventInterceptor;

/**
 * Created by Evan on 2017/11/24.
 */

public interface TouchInterceptable {



    void setInterceptMode(TouchEventInterceptor.InterceptMode interceptMode);

    TouchEventInterceptor.InterceptMode getInterceptMode();


}
