package com.github.evan.common_utils.utils;

import android.support.v4.util.LruCache;

/**
 * Created by Evan on 2018/10/5.
 */

public class LRUCache<K, V> extends LruCache<K, V> {

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LRUCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(K key, V value) {
        return super.sizeOf(key, value);
    }
}
