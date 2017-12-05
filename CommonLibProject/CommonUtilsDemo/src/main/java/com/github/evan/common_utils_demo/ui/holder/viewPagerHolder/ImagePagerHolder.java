package com.github.evan.common_utils_demo.ui.holder.viewPagerHolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils.ui.view.LoadingPager;
import com.github.evan.common_utils.utils.BitmapUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter.ImagePagerAdapter;
import com.github.evan.common_utils_demo.ui.fragment.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/22.
 */
public class ImagePagerHolder extends BasePagerHolder<String> implements RequestListener<String, Bitmap> {
    @BindView(R.id.loading_pager_image_pager_holder)
    LoadingPager mLoadingPager;
    @BindView(R.id.img_image_pager_holder)
    ImageView mImgPhoto;
    @BindView(R.id.txt_position_image_pager_holder)
    TextView mTxtPosition;

    public ImagePagerHolder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public View onCreateView(Context context, LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_image_pager, parent, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void instantiateItem(ViewGroup container, int position) {
        String data = getData();
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOADING);
        mLoadingPager.setVisibility(View.VISIBLE);
        mImgPhoto.setVisibility(View.GONE);

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource == null || resource.isRecycled()) {
                    mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOAD_EMPTY);
                    mImgPhoto.setVisibility(View.GONE);
                    mLoadingPager.setVisibility(View.VISIBLE);
                } else {
                    if (ImagePagerHolder.this.isDisplaying()) {
                        mImgPhoto.setImageBitmap(resource);
                        mImgPhoto.setVisibility(View.VISIBLE);
                        mLoadingPager.setVisibility(View.GONE);
                    } else {
                        resource.recycle();
                    }
                }
            }
        };

        Glide.with(getContext()).load(getData()).asBitmap().listener(this).into(target);
        mTxtPosition.setText(getPosition() + 1 + " / " + ImagePagerAdapter.IMAGES.length);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @OnClick({R.id.img_image_pager_holder})
    void onClick(View view) {
        ToastUtil.showToastWithShortDuration("Pager clicked！ position is : " + getPosition());
    }

    @Override
    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
        mLoadingPager.setLoadingStatus(LoadingPager.LoadingStatus.LOAD_EMPTY);
        mImgPhoto.setVisibility(View.INVISIBLE);
        mLoadingPager.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
    }
}
