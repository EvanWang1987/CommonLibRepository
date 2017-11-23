package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.imageView.RoundedImageView;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/22.
 */
public class BigPreviewHolder extends BaseRecyclerViewHolder<Integer> {
    public static final int ITEM_VIEW_TYPE_BIG_PREVIEW = 1;

    @BindView(R.id.img_icon_big_preview_holder)
    RoundedImageView mImgIcon;
    @BindView(R.id.txt_title_big_preview_holder)
    TextView mTxtTitle;
    @BindView(R.id.txt_desc_big_preview_holder)
    TextView mTxtDesc;
    @BindView(R.id.txt_position_big_preview_holder)
    TextView mTxtPosition;
    @BindView(R.id.txt_thumb_up_big_preview_holder)
    TextView mTxtThumbUp;

    public BigPreviewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onRefreshHolder(Integer integer) {
        mTxtPosition.setText(getAdapterPosition() + "");
    }

    @Override
    public void onHolderRecycled() {

    }

    @OnClick({R.id.layout_root_big_preview_holder, R.id.txt_thumb_up_big_preview_holder})
    void onClick(View view){
        switch (view.getId()){
            case R.id.layout_root_big_preview_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Adapter Position: " + getAdapterPosition() + ", Layout Position: " + getLayoutPosition());
                break;

            case R.id.txt_thumb_up_big_preview_holder:
                ToastUtil.showToastWithShortDuration("赞");
                break;
        }
    }
}
