package com.github.evan.common_utils.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.ui.view.gestureImageView.PhotoView;

/**
 * Created by Evan on 2017/12/25.
 */

public class SinglePhotoActivity extends BaseActivity {
    public static final String PHOTO_RES_ID = "photo_res_id";
    public static final String PHOTO_LINK = "photo_link";

    private PhotoView mPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoView = (PhotoView) findViewById(R.id.photo_view_single_photo_activity);
        Bundle extras = getIntent().getExtras();
        if(null != extras){
            int resId = extras.getInt(PHOTO_RES_ID, -1);
            String link = extras.getString(PHOTO_LINK, null);
            if(resId == -1 && link == null){
                finish();
                return;
            }

            if(resId != -1){
                mPhotoView.setImageResource(resId);
            }
        }

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_single_photo;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }
}
