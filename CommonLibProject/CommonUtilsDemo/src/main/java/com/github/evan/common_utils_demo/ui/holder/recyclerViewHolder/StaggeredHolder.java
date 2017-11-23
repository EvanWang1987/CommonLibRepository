package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.view.View;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.imageView.RoundedImageView;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/23.
 */

public class StaggeredHolder extends BaseRecyclerViewHolder<Integer> {
    @BindView(R.id.img_preview_layout_root_staggered_grid_holder)
    RoundedImageView mImgPreview;

    public StaggeredHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onRefreshHolder(Integer integer) {
        mImgPreview.setImageResource(integer);
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
