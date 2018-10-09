package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.github.evan.common_utils.cache.BitmapCacher;
import com.github.evan.common_utils.cache.BitmapCacherCallback;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.ui.view.imageView.RoundedImageView;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/23.
 */

public class StaggeredBitmapCacherHolder extends BaseRecyclerViewHolder<String> {
    @BindView(R.id.img_preview_layout_root_staggered_grid_holder)
    RoundedImageView mImgPreview;
    @BindView(R.id.loading_pager_staggered_grid_holder)
    LoadingPager mLoadingPager;

    public StaggeredBitmapCacherHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onRefreshHolder(String data) {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mImgPreview.setVisibility(View.GONE);

        BitmapCacher.getInstance().getBitmap(data, new BitmapCacherCallback() {
            @Override
            public void onBitmapLoaded(String url, Bitmap bitmap) {
                boolean isBitmapComplete = null != bitmap && !bitmap.isRecycled();
                mImgPreview.setImageBitmap(isBitmapComplete ? bitmap : null);
                mLoadingPager.setLoadingStatus(isBitmapComplete ? LoadingPager.LoadingStatus.IDLE : LoadingPager.LoadingStatus.LOAD_EMPTY);
                mLoadingPager.setVisibility(isBitmapComplete ? View.GONE : View.VISIBLE);
                mImgPreview.setVisibility(isBitmapComplete ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onBitmapLoadFailed(String url, Bitmap defaultBitmap) {

            }
        });


    }

    @Override
    public void onHolderRecycled() {

    }

    @OnClick({R.id.layout_root_staggered_grid_holder})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_root_staggered_grid_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Adapter Position: " + getAdapterPosition() + ", Layout Position: " + getLayoutPosition());
                break;
        }
    }

}
