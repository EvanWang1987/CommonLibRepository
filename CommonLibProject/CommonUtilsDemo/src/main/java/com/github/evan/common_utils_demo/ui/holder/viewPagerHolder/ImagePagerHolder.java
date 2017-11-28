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

import com.github.evan.common_utils.ui.holder.BasePagerHolder;
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
public class ImagePagerHolder extends BasePagerHolder<Integer> {
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
        mImgPhoto.setImageResource(ImagePagerAdapter.IMAGES[position]);
        mTxtPosition.setText(getData() + " / " + ImagePagerAdapter.IMAGES.length);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
