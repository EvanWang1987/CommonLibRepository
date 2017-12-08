package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils.ui.view.imageView.RoundedImageView;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils.utils.UiUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/11/21.
 */
public class GridHolder extends BaseRecyclerViewHolder<String> implements PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.img_preview_grid_item)
    RoundedImageView mImgPreview;
    @BindView(R.id.txt_title_grid_item)
    TextView mTxtTitle;
    @BindView(R.id.txt_views_grid_item)
    TextView mTxtWatchCount;
    @BindView(R.id.txt_danmarkus_grid_item)
    TextView mTxtDanMarkCount;
    @BindView(R.id.txt_video_duration_grid_item)
    TextView mTxtVideoDuration;
    @BindView(R.id.img_action_more_grid_item)
    ImageView mImgActionMore;
    private PopupMenu mPopMenu;

    public GridHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        mPopMenu = new PopupMenu(context, mImgActionMore, Gravity.BOTTOM | Gravity.START);
        Menu menu = mPopMenu.getMenu();
        mPopMenu.getMenuInflater().inflate(R.menu.menu_grid_holder_more, menu);
        mPopMenu.setOnMenuItemClickListener(this);
        UiUtil.setPopupToolBarMenuWithIcon(menu);
    }


    @Override
    public void onRefreshHolder(String data) {

    }

    @Override
    public void onHolderRecycled() {

    }

    @OnClick({R.id.layout_root_grid_holder, R.id.img_action_more_grid_item})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_root_grid_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ Adapter Position: " + getAdapterPosition() + ", Layout Position: " + getLayoutPosition());
                break;

            case R.id.img_action_more_grid_item:
                mPopMenu.show();
                break;
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_grid_holder_thumb_up:
                ToastUtil.showToastWithShortDuration("赞");
                return true;

            case R.id.menu_grid_holder_search:
                ToastUtil.showToastWithShortDuration("搜索");
                return true;

            case R.id.menu_grid_holder_share:
                ToastUtil.showToastWithShortDuration("分享");
                return true;
        }
        return false;
    }
}
