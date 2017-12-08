package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
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

public class StaggeredHolder extends BaseRecyclerViewHolder<String> implements RequestListener<String, Bitmap> {
    @BindView(R.id.img_preview_layout_root_staggered_grid_holder)
    RoundedImageView mImgPreview;
    @BindView(R.id.loading_pager_staggered_grid_holder)
    LoadingPager mLoadingPager;

    public StaggeredHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onRefreshHolder(String data) {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mImgPreview.setVisibility(View.GONE);

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                boolean isBitmapComplete = null != resource && !resource.isRecycled();
                mImgPreview.setImageBitmap(isBitmapComplete ? resource : null);
                mLoadingPager.setLoadingStatus(isBitmapComplete ? LoadingPager.LoadingStatus.IDLE : LoadingPager.LoadingStatus.LOAD_EMPTY);
                mLoadingPager.setVisibility(isBitmapComplete ? View.GONE : View.VISIBLE);
                mImgPreview.setVisibility(isBitmapComplete ? View.VISIBLE : View.GONE);
            }
        };
        Glide.with(getContext()).load(data).asBitmap().listener(this).into(target);
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

    @Override
    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOAD_EMPTY);
        mLoadingPager.setVisibility(View.VISIBLE);
        mImgPreview.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
    }
}
