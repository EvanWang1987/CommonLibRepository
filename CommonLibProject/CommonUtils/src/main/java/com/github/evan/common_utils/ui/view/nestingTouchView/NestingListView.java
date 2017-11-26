package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Evan on 2017/11/26.
 */

public class NestingListView extends ListView {
    public NestingListView(Context context) {
        super(context);
    }

    public NestingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestingListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
