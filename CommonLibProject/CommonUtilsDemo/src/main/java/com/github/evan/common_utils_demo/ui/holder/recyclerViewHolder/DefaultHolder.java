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
 * Created by Evan on 2017/11/20.
 */
public class DefaultHolder extends BaseRecyclerViewHolder<String> {
    @BindView(R.id.img_icon_default_holder)
    ImageView mImgIcon;
    @BindView(R.id.txt_title_default_holder)
    TextView mTxtTitle;
    @BindView(R.id.txt_desc_default_holder)
    TextView mTxtDesc;
    @BindView(R.id.txt_position_default_holder)
    TextView mTxtPosition;
    @BindView(R.id.txt_thumb_up_default_holder)
    TextView mTxtThumbUpCount;

    public DefaultHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onRefreshHolder(String data) {
        mTxtPosition.setText((getAdapterPosition() + 1) + "");
    }

    @Override
    public void onHolderRecycled() {
    }

    @OnClick({R.id.layout_root_default_holder, R.id.txt_thumb_up_default_holder})
    void onClick(View view){
        switch (view.getId()){
            case R.id.layout_root_default_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Adapter Position: " + getAdapterPosition() + ", Layout Position: " + getLayoutPosition());
                break;

            case R.id.txt_thumb_up_default_holder:
                ToastUtil.showToastWithShortDuration("赞");
                break;
        }
    }
}
