package com.github.evan.common_utils.ui.view.nestingTouchView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Evan on 2017/12/7.
 *
 * 此类只为生成统一的自定义属性给所有NestingView使用
 *
 */
public final class NestingView extends View{

    public NestingView(Context context) {
        super(context);
    }

    public NestingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
