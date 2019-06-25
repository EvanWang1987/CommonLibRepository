package com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.bean.MusicInfo;
import com.github.evan.common_utils.ui.adapter.BaseRecyclerViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.MusicHolder;

public class MusicAdapter extends BaseRecyclerViewAdapter<MusicInfo> {

    public MusicAdapter(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.holder_music, null);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder<MusicInfo> onCreateHolder(View itemView, ViewGroup parent, int viewType) {
        MusicHolder musicHolder = new MusicHolder(itemView.getContext(), itemView);
        return musicHolder;
    }
}
