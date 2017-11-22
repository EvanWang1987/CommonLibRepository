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

/**
 * Created by Evan on 2017/11/21.
 */
public class RecyclerAdapter extends SwitchRecyclerAdapter<Integer> {

    public RecyclerAdapter(Context context) {
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
        else {
            holder = new GridHolder(parent.getContext(), itemView);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = BaseRecyclerViewHolder.DEFAULT_ITEM_TYPE;
        boolean threeNumber = position % 3 == 0;
        boolean twoNumber = position % 2 == 0;
        if(twoNumber){
            type = BigPreviewHolder.ITEM_VIEW_TYPE_BIG_PREVIEW;
        }
        else if(threeNumber){
            type = MultiPreviewHolder.ITEM_VIEW_TYPE_MULTI_PREVIEW;
        }

        return type;
    }
}
