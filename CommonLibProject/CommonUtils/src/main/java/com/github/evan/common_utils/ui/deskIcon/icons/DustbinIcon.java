package com.github.evan.common_utils.ui.deskIcon.icons;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.deskIcon.BaseDeskIcon;
import com.github.evan.common_utils.ui.deskIcon.DeskIconConfig;

/**
 * Created by Evan on 2017/12/24.
 */
public class DustbinIcon extends BaseDeskIcon {
    public static final int ASS_ABOVE = 0;
    public static final int RESET = 1;
    public static final int SHOW = 2;

    private ImageView mRootView;

    public DustbinIcon(Context context) {
        super(context);
    }

    @Override
    public void setStatus(int status) {
        if(status == SHOW){
            mRootView.setImageResource(R.mipmap.dustbin_normal);
            show();
        }else if(status == ASS_ABOVE){
            mRootView.setImageResource(R.mipmap.dustbin_focused);
        }else if(status == RESET){
            hidden();
        }
    }

    @Override
    public DeskIconConfig onCreateDeskIconConfig() {
        DeskIconConfig config = new DeskIconConfig();
        mRootView = new ImageView(getContext());
        mRootView.setImageResource(R.mipmap.dustbin_normal);
        config.setDeskIconView(mRootView);
        config.setGravity(Gravity.LEFT|Gravity.TOP);
        config.setInitX(0);
        config.setInitY(0);
        return config;
    }
}
