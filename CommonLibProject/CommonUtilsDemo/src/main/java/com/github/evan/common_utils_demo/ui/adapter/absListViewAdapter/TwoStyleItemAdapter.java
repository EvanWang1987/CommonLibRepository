package com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.adapter.BaseAbsListViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.BigPreviewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.DefaultHolder;

/**
 * Created by Evan on 2017/11/20.
 */
public class TwoStyleItemAdapter extends BaseAbsListViewAdapter<Integer> {

    public TwoStyleItemAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseAbsListViewHolder<Integer> onCreateHolder(Context context, int itemViewType) {
        BaseAbsListViewHolder<Integer> holder = null;
        switch (itemViewType){
            default:
            case DefaultHolder.DEFAULT_ITEM_TYPE:
                holder = new DefaultHolder(context);
                break;

            case BigPreviewHolder.ITEM_TYPE_BIG_IMAGE:
                holder = new BigPreviewHolder(context);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = DefaultHolder.DEFAULT_ITEM_TYPE;
        boolean isEvenNumber = position % 2 == 0;
        if(isEvenNumber){
            type = BigPreviewHolder.ITEM_TYPE_BIG_IMAGE;
        }
        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
