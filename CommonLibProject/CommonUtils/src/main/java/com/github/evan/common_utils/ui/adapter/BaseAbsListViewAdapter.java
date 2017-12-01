package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.github.evan.common_utils.ui.holder.BaseAbsListViewHolder;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/11/20.
 */
public abstract class BaseAbsListViewAdapter<Data> extends BaseAdapter {
    public abstract BaseAbsListViewHolder<Data> onCreateHolder(Context context, int itemViewType);
    private Context mContext;
    private List<Data> mData = new ArrayList<>();

    public BaseAbsListViewAdapter(Context context) {
        if(null == context){
            throw new IllegalArgumentException("Context can not be null！");
        }
        mContext = context;
    }

    public void recycle(){
        mContext = null;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void add(Data data){
        if(!mData.contains(data)){
            mData.add(data);
        }
    }

    public void addAll(List<Data> data){
        mData.addAll(data);
    }

    public void remove(Data data){
        if(mData.contains(data)){
            mData.remove(data);
        }
    }

    public void update(Data data){
        int indexOf = mData.indexOf(data);
        if(indexOf != -1){
            mData.add(indexOf, data);
        }
    }

    public Data get(int position){
        return mData.get(position);
    }

    public void replace(List<Data> data){
        mData.clear();
        mData.addAll(data);
    }

    public void clear(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return BaseAbsListViewHolder.DEFAULT_ITEM_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Data data = mData.get(position);
        int itemViewType = getItemViewType(position);
        BaseAbsListViewHolder<Data> holder = null;

        if(null == convertView){
            holder = onCreateHolder(mContext, itemViewType);
        }
        else{
            Object tag = convertView.getTag();
            if(null != tag && tag instanceof BaseAbsListViewHolder){
                BaseAbsListViewHolder<Data> temp = (BaseAbsListViewHolder<Data>) tag;
                int tempItemViewType = temp.getItemViewType();
                if(tempItemViewType == itemViewType){
                    holder = temp;
                }
                else{
                    holder = onCreateHolder(mContext, itemViewType);
                }
            }
            else{
                holder = onCreateHolder(mContext, itemViewType);
            }
        }

        holder.setContext(mContext);
        holder.setData(data);
        holder.setPosition(position);
        holder.onRefreshHolder(data);
        return holder.getRootView();
    }
}
