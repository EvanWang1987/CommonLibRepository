package com.github.evan.common_utils_demo.ui.holder.absListViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/20.
 */
public class DefaultHolder extends BaseAbsListViewHolder<Integer> {
    @BindView(R.id.img_icon_default_holder)
    ImageView mImgIcon;
    @BindView(R.id.txt_title_default_holder)
    TextView mTxtTitle;
    @BindView(R.id.txt_desc_default_holder)
    TextView mTxtDesc;
    @BindView(R.id.txt_position_default_holder)
    TextView mTxtPosition;
    @BindView(R.id.txt_thumb_up_default_holder)
    TextView mTxtThumbUp;


    public DefaultHolder(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.item_list, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onRefreshHolder(Integer data) {
        mTxtPosition.setText(getPosition() + 1 + "");
    }

    @OnClick({R.id.layout_root_default_holder, R.id.txt_thumb_up_default_holder})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_root_default_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Position: " + getPosition());
                break;

            case R.id.txt_thumb_up_default_holder:
                ToastUtil.showToastWithShortDuration("赞");
                break;
        }
    }
}
