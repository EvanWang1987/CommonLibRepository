package com.github.evan.common_utils_demo.ui.adapter.absListViewAdapter;

import android.content.Context;

import com.github.evan.common_utils.ui.adapter.BaseAbsListViewAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.BigPreviewHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.DefaultHolder;
import com.github.evan.common_utils_demo.ui.holder.absListViewHolder.MultiPreviewHolder;

/**
 * Created by Evan on 2017/11/20.
 */
public class ThreeStyleItemAdapter extends BaseAbsListViewAdapter<Integer> {

    public ThreeStyleItemAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseAbsListViewHolder<Integer> onCreateHolder(Context context, int itemViewType) {
        BaseAbsListViewHolder holder = null;
        switch (itemViewType){
            default:
            case BaseAbsListViewHolder.DEFAULT_ITEM_TYPE:
                holder = new DefaultHolder(context);
                break;

            case BigPreviewHolder.ITEM_TYPE_BIG_IMAGE:
                holder = new BigPreviewHolder(context);
                break;

            case MultiPreviewHolder.ITEM_TYPE_THREE_PREVIEW:
                holder = new MultiPreviewHolder(context);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        int type = BaseAbsListViewHolder.DEFAULT_ITEM_TYPE;
        boolean threeNumber = position % 3 == 0;
        boolean twoNumber = position % 2 == 0;
        if(twoNumber){
            type = BigPreviewHolder.ITEM_TYPE_BIG_IMAGE;
        }
        else if(threeNumber){
            type = MultiPreviewHolder.ITEM_TYPE_THREE_PREVIEW;
        }

        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
