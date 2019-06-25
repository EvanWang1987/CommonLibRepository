package com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.bean.MusicInfo;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicHolder extends BaseRecyclerViewHolder<MusicInfo> {
    @BindView(R.id.music_name)
    public TextView mTxtAuthor;
    @BindView(R.id.music_author)
    public TextView mTxtName;
    @BindView(R.id.status_playing)
    public ImageView mImgIcon;

    public MusicHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onRefreshHolder(MusicInfo musicInfo) {
        mTxtName.setText(musicInfo.getMp3Information().getTitle());
        mTxtAuthor.setText(musicInfo.getMp3Information().getArtist());

    }

    @Override
    public void onHolderRecycled() {

    }
}
