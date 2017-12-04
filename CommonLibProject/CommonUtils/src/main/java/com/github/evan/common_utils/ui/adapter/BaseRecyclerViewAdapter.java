package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.holder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/11/20.
 */
public abstract class BaseRecyclerViewAdapter<Data> extends RecyclerView.Adapter<BaseRecyclerViewHolder<Data>> {
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType);
    public abstract BaseRecyclerViewHolder<Data> onCreateHolder(View itemView, ViewGroup parent, int viewType);

    private Context mContext;
    private List<Data> mData = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context.getApplicationContext();
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
    public BaseRecyclerViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = onCreateView(LayoutInflater.from(mContext), parent, viewType);
        BaseRecyclerViewHolder<Data> holder = onCreateHolder(root, parent, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<Data> holder, int position) {
        Data data = mData.get(position);
        holder.setData(data);
        holder.onRefreshHolder(data);
    }

    @Override
    public int getItemViewType(int position) {
        return BaseRecyclerViewHolder.DEFAULT_ITEM_TYPE;
    }

    @Override
    public void onViewRecycled(BaseRecyclerViewHolder<Data> holder) {
        holder.clearContext();
        holder.onHolderRecycled();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
