package com.github.evan.common_utils.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.evan.common_utils.ui.holder.BasePagerHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Evan on 2017/11/22.
 */
public abstract class BasePagerAdapter<Data> extends PagerAdapter {
    public abstract BasePagerHolder<Data> onCreateHolder(Context context, ViewGroup parent);

    private Context mContext;
    private List<Data> mData = new ArrayList<>();
    private ConcurrentHashMap<Integer, View> mConvertViews = new ConcurrentHashMap<>();

    public BasePagerAdapter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void add(Data data){
        if(!mData.contains(data)){
            mData.add(data);
        }
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
    public CharSequence getPageTitle(int position) {
        return "Pager " + position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View convertView = mConvertViews.get(position);
        Data data = mData.get(position);
        BasePagerHolder<Data> holder = null;
        if(null != convertView){
            Object tag = convertView.getTag();
            if(null != tag && tag instanceof BasePagerHolder){
                holder = (BasePagerHolder<Data>) tag;
            }
            else{
                holder = onCreateHolder(mContext, container);
                convertView = holder.getRootView();
                convertView.setTag(holder);
            }
        }else{
            holder = onCreateHolder(mContext, container);
            convertView = holder.getRootView();
            convertView.setTag(holder);
        }

        if(!holder.isContextExists()){
            holder.setContext(mContext);
        }
        holder.setPosition(position);
        holder.setData(data);
        holder.instantiateItem(container, position);
        mConvertViews.put(position, convertView);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View convertView = mConvertViews.get(position);
        container.removeView(convertView);
        BasePagerHolder<Data> holder = (BasePagerHolder<Data>) convertView.getTag();
        holder.destroyItem(container, position, object);
        mConvertViews.remove(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
