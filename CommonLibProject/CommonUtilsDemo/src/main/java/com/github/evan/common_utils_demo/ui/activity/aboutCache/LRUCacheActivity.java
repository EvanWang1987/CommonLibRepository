package com.github.evan.common_utils_demo.ui.activity.aboutCache;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;

import com.github.evan.common_utils.ui.activity.BaseActivityConfig;
import com.github.evan.common_utils_demo.R;
import com.github.evan.common_utils_demo.ui.activity.collectionActivity.BaseLogCatActivity;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/10/5.
 */

public class LRUCacheActivity extends BaseLogCatActivity {

    private LruCache<String, String> mLRUCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateSubView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.activity_lru_cache, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public BaseActivityConfig onCreateActivityConfig() {
        return new BaseActivityConfig();
    }

    @OnClick({R.id.card_init_lru_cache, R.id.card_lru_cache_add, R.id.card_lru_cache_used_memory, R.id.card_lru_cache_total_memory})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.card_init_lru_cache:
                initLRU();
                break;

            case R.id.card_lru_cache_add:
                if(null == mLRUCache){
                    addLog("请先初始化");
                    return;
                }

                int putCount = mLRUCache.putCount();
                String key = "key" + (putCount + 1);
                String value = UUID.randomUUID().toString();
                mLRUCache.put(key, value);
                addLog("put key: " + key + ", value: " + value);
                break;

            case R.id.card_lru_cache_used_memory:
                if(null == mLRUCache){
                    addLog("请先初始化");
                    return;
                }

                int usedByte = 0;
                Map<String, String> snapshot = mLRUCache.snapshot();
                Set<Map.Entry<String, String>> entries = snapshot.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> next = iterator.next();
                    String value1 = next.getValue();
                    usedByte += value1.length();
                }

                addLog("Used memory: " + usedByte + " byte");
                break;

            case R.id.card_lru_cache_total_memory:
                if(null == mLRUCache){
                    addLog("请先初始化");
                    return;
                }

                int maxSize = mLRUCache.maxSize();
                addLog("total memory: " + maxSize + " byte");

                break;
        }
    }

    private void initLRU(){
        if(null != mLRUCache){
            addLog("已经初始化LRUCache");
            return;
        }



        long memory = Runtime.getRuntime().maxMemory();
        long maxMemoryOfMB = memory / 1024 / 1024;
        long maxLruCacheMemoryOfByte = 200;
        addLog("进程最大内存: " + maxMemoryOfMB + "MB");
        mLRUCache = new LruCache<String, String>((int)maxLruCacheMemoryOfByte){
            @Override
            protected int sizeOf(String key, String value) {
                return value.getBytes().length;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
                addLog("key: " + key + ", value: " + oldValue + "has removed!");
            }
        };
        addLog("创建LRUCache,分配内存: " + maxLruCacheMemoryOfByte + " byte");
    }
}
