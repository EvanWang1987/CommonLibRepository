package com.github.evan.common_utils_demo.ui.adapter.viewPagerAdapter;

import android.content.Context;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BasePagerAdapter;
import com.github.evan.common_utils.ui.holder.BasePagerHolder;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.holder.viewPagerHolder.ImagePagerHolder;

/**
 * Created by Evan on 2017/11/22.
 */

public class ImagePagerAdapter extends BasePagerAdapter<String> {
//    public static final int[] IMAGES = {R.mipmap.img_view_pager_one, R.mipmap.img_view_pager_two, R.mipmap.img_view_pager_three, R.mipmap.img_view_pager_four, R.mipmap.img_view_pager_five};
    public static final String[] IMAGES = {"http://img4.imgtn.bdimg.com/it/u=3562756219,2128271467&fm=214&gp=0.jpg", "http://img5.imgtn.bdimg.com/it/u=3850743351,477502698&fm=27&gp=0.jpg", "http://f2.dn.anqu.com/down/MjVkNw==/allimg/1208/48-120P3151T8.jpg", "http://pic1.win4000.com/wallpaper/b/538e982c5b085.jpg", "http://img22.mtime.cn/up/2011/09/29/135827.74307891_o.jpg"};

    public ImagePagerAdapter(Context context) {
        super(context);
    }

    @Override
    public BasePagerHolder<String> onCreateHolder(Context context, ViewGroup parent, int position) {
        return new ImagePagerHolder(context, parent);
    }
}
