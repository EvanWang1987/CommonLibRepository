package com.github.evan.common_utils.ui.view.flagView.flagPainter;

import android.graphics.Canvas;
import android.view.View;
import com.github.evan.common_utils.ui.view.flagView.FlagLocation;

/**
 * Created by Evan on 2017/11/10.
 */
public interface IFlagPainter<GraphicsObj, GraphicsObj2> {

    void draw(View view, Canvas canvas, FlagLocation location, GraphicsObj obj1, GraphicsObj2 obj2);
}
