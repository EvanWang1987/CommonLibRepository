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
public class MultiPreviewHolder extends BaseAbsListViewHolder<Integer> {
    public static final int ITEM_TYPE_THREE_PREVIEW = 2;

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

    public MultiPreviewHolder(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.item_list_multi_preview, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onRefreshHolder(Integer integer) {
        mTxtPosition.setText(integer + "");
    }

    @OnClick({R.id.layout_root_multi_preview_holder, R.id.txt_thumb_up_three_preview_holder})
    void onClick(View view){
        switch (view.getId()){
            case R.id.layout_root_multi_preview_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Position: " + getPosition());
                break;

            case R.id.txt_thumb_up_three_preview_holder:
                ToastUtil.showToastWithShortDuration("赞");
                break;
        }

    }
}
