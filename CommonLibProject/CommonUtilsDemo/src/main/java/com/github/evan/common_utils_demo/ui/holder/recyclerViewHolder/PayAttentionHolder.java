package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.bean.TitleInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/19.
 */

public class PayAttentionHolder extends BaseRecyclerViewHolder<TitleInteger> {
    @BindView(R.id.ic_portrait_pay_attention_holder)
    ImageView mImgPortrait;
    @BindView(R.id.txt_channel_pay_attention_holder)
    TextView mTxtChannel;
    @BindView(R.id.btn_pay_attention_holder)
    TextView mTxtPayAttention;

    public PayAttentionHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onRefreshHolder(TitleInteger integer) {
        mImgPortrait.setImageResource(integer.getValue());
        mTxtChannel.setText(integer.getTitle());
    }

    @Override
    public void onHolderRecycled() {

    }

    @OnClick({R.id.btn_pay_attention_holder})
    void onClick(View view){
        ToastUtil.showToastWithShortDuration("item clicked! position: " + getLayoutPosition());
    }
}
