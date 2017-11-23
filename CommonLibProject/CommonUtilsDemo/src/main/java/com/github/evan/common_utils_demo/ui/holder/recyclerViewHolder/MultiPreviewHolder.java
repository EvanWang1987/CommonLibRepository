package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/22.
 */
public class MultiPreviewHolder extends BaseRecyclerViewHolder<Integer> {
    public static final int ITEM_VIEW_TYPE_MULTI_PREVIEW = 2;

    @BindView(R.id.img_first_icon_three_preview_holder)
    ImageView mImgSecondPreview;
    @BindView(R.id.img_second_icon_three_preview_holder)
    ImageView mImgThirdPreview;
    @BindView(R.id.img_third_icon_three_preview_holder)
    ImageView mImgFirstPreview;
    @BindView(R.id.txt_title_three_preview_holder)
    TextView mTxtTitle;
    @BindView(R.id.txt_desc_three_preview_holder)
    TextView mTxtDesc;
    @BindView(R.id.txt_position_three_preview_holder)
    TextView mTxtPosition;
    @BindView(R.id.txt_thumb_up_three_preview_holder)
    TextView mTxtThumbUp;

    public MultiPreviewHolder(Context context, View itemView) {
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

    @OnClick({R.id.layout_root_multi_preview_holder, R.id.txt_thumb_up_three_preview_holder})
    void onClick(View view){
        switch (view.getId()){
            case R.id.layout_root_multi_preview_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Adapter Position: " + getAdapterPosition() + ", Layout Position: " + getLayoutPosition());
                break;

            case R.id.txt_thumb_up_three_preview_holder:
                ToastUtil.showToastWithShortDuration("赞");
                break;
        }

    }
}
