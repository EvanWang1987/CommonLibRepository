package com.github.evan.common_utils_demo.ui.holder.absListViewHolder;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
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
public class GridItemHolder extends BaseAbsListViewHolder<Integer> implements PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.img_preview_grid_item)
    RoundedImageView mImgPreview;
    @BindView(R.id.txt_title_grid_item)
    TextView mTxtTitle;
    @BindView(R.id.txt_views_grid_item)
    TextView mTxtViewsCount;
    @BindView(R.id.txt_danmarkus_grid_item)
    TextView mTxtDanMarkedCount;
    @BindView(R.id.txt_video_duration_grid_item)
    TextView mTxtVideoDuration;
    @BindView(R.id.img_action_more_grid_item)
    ImageView mImgActionMore;
    private PopupMenu mPopMenu;

    public GridItemHolder(Context context) {
        super(context);
        mPopMenu = new PopupMenu(context, mImgActionMore, Gravity.BOTTOM | Gravity.START);
        Menu menu = mPopMenu.getMenu();
        mPopMenu.getMenuInflater().inflate(R.menu.menu_grid_holder_more, menu);
        mPopMenu.setOnMenuItemClickListener(this);
        UiUtil.setPopupToolBarMenuWithIcon(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View root = inflater.inflate(R.layout.item_grid, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onRefreshHolder(Integer data) {

    }

    @OnClick({R.id.layout_root_grid_holder, R.id.img_action_more_grid_item})
    void onClick(View view){
        switch (view.getId()){
            case R.id.layout_root_grid_holder:
                ToastUtil.showToastWithShortDuration("Item clicked！ position: " + getPosition());
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
