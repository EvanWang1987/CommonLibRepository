package com.github.evan.common_utils_demo.ui.adapter.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.SwitchRecyclerAdapter;
import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.BigPreviewHolder;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.DefaultHolder;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.GridHolder;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.MultiPreviewHolder;
import com.github.evan.common_utils_demo.ui.holder.recyclerViewHolder.StaggeredHolder;

/**
 * Created by Evan on 2017/11/21.
 */
public class DefaultRecyclerAdapter extends SwitchRecyclerAdapter<Integer> {

    public DefaultRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public View onCreateViewWithSwitchMode(LayoutInflater inflater, ViewGroup parent, int viewType, int switchMode) {
        View root = null;
        if(switchMode == LIST_MODE_VERTICAL){
            switch (viewType){
                default:
                case BaseRecyclerViewHolder.DEFAULT_ITEM_TYPE:
                    root = inflater.inflate(R.layout.item_list, parent, false);
                    break;

                case BigPreviewHolder.ITEM_VIEW_TYPE_BIG_PREVIEW:
                    root = inflater.inflate(R.layout.item_list_big_preview, parent, false);
                    break;

                case MultiPreviewHolder.ITEM_VIEW_TYPE_MULTI_PREVIEW:
                    root = inflater.inflate(R.layout.item_list_multi_preview, parent, false);
                    break;
            }
        }
        else if(switchMode == STAGGERED_GRID_MODE){
            root = inflater.inflate(R.layout.item_staggered_grid, parent, false);
        }
        else {
            root = inflater.inflate(R.layout.item_grid, parent, false);
        }
        return root;
    }

    @Override
    public BaseRecyclerViewHolder<Integer> onCreateHolderWithSwitchMode(View itemView, ViewGroup parent, int viewType, int switchMode) {
        BaseRecyclerViewHolder<Integer> holder = null;
        if(switchMode == LIST_MODE_VERTICAL){
            switch (viewType){
                default:
                case BaseRecyclerViewHolder.DEFAULT_ITEM_TYPE:
                    holder = new DefaultHolder(parent.getContext(), itemView);
                    break;

                case BigPreviewHolder.ITEM_VIEW_TYPE_BIG_PREVIEW:
                    holder = new BigPreviewHolder(parent.getContext(), itemView);
                    break;

                case MultiPreviewHolder.ITEM_VIEW_TYPE_MULTI_PREVIEW:
                    holder = new MultiPreviewHolder(parent.getContext(), itemView);
                    break;
            }
        }
        else if(switchMode == STAGGERED_GRID_MODE){
            holder = new StaggeredHolder(parent.getContext(), itemView);
        }
        else {
            holder = new GridHolder(parent.getContext(), itemView);
        }
        return holder;
    }

}
