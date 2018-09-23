package com.github.evan.common_utils.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.github.evan.common_utils.db.BaseSQLiteHelper;
import com.github.evan.common_utils.manager.threadManager.ThreadFactory;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evan on 2018/9/23.
 */
public abstract class BaseDao<SQLiteHelper extends BaseSQLiteHelper, Model, Key> {
    private static ThreadPoolExecutor mDaoExecutor;

    {
        mDaoExecutor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory("SQLite DAO thread"), new ThreadPoolExecutor.AbortPolicy());
        mDaoExecutor.prestartCoreThread();  //预创建一个线程
    }

    protected Hashtable<Key, Model> mMemoryCache;
    protected SQLiteHelper mHelper;

    public BaseDao(SQLiteHelper helper) {
        mHelper = helper;
        if(isCacheInMemory()){
            mMemoryCache = new Hashtable<>();
            mMemoryCache.putAll(onCacheIntoMemory());
        }
    }

    public abstract boolean insert(Model model);

    public abstract boolean remove(Key removeKey);

    public abstract boolean update(Key updateKey, Model model);

    public abstract Model query(Key queryKey);

    public abstract List<Model> queryAll();

    protected Hashtable<Key, Model> onCacheIntoMemory(){
        return null;
    }

    public abstract boolean isCacheInMemory();

    public void saveToDb(String sql){
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        writableDatabase.execSQL(sql);
    }

    public void saveToDbInBackground(final String sql){
        mDaoExecutor.submit(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
                writableDatabase.execSQL(sql);
            }
        });
    }

}
