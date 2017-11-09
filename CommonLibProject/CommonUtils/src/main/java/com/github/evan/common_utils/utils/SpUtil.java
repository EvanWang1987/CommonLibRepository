package com.github.evan.common_utils.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.github.evan.common_utils.BaseApplication;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Evan on 2017/11/3.
 */
public class SpUtil {
    public static final String DEFAULT_SP_FILE_NAME = "default_shared_preferences";
    private ConcurrentHashMap<String, SharedPreferences> mSps = new ConcurrentHashMap<>();

    private static SpUtil mIns = null;
    public static SpUtil getIns(){
        if(null == mIns){
            synchronized (SpUtil.class){
                mIns = new SpUtil();
            }
        }
        return mIns;
    }

    private SpUtil(){
        File spFileDir = new File(BaseApplication.getApplication().getFilesDir().getParentFile(), "shared_prefs");
        String[] fileNames = spFileDir.list();
        for (int i = 0; i < fileNames.length; i++) {
            String name = fileNames[i];
            SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
            mSps.put(name, sp);
        }
        if(!mSps.containsKey(DEFAULT_SP_FILE_NAME)){
            SharedPreferences defaultSp = BaseApplication.getApplication().getSharedPreferences(DEFAULT_SP_FILE_NAME, Context.MODE_PRIVATE);
            mSps.put(DEFAULT_SP_FILE_NAME, defaultSp);
        }
    }

    /**
     * 初始化一个SharedPreference, 并内存缓存
     * @param isUseDefaultSp    是否使用默认
     * @param spName            SharedPreference名
     * @return
     */
    private SharedPreferences initSp(boolean isUseDefaultSp, String spName){
        if(StringUtil.isEmpty(spName)){
           throw new IllegalArgumentException("Illegal SharedPreference name!");
        }
        
        String name = isUseDefaultSp ? DEFAULT_SP_FILE_NAME : spName;
        SharedPreferences sp = mSps.get(name);
        sp = null == sp ? BaseApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE) : sp;
        mSps.put(name, sp);
        return sp;
    }

    public boolean commitString(String key, String value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString(key, value).commit();
    }

    public boolean commitStringSet(String key, Set<String> value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putStringSet(key, value).commit();
    }

    public boolean commitInt(String key, int value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt(key, value).commit();
    }

    public boolean commitLong(String key, long value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putLong(key, value).commit();
    }

    public boolean commitBoolean(String key, boolean value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putBoolean(key, value).commit();
    }

    public boolean commitFloat(String key, float value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putFloat(key, value).commit();
    }

    public void applyString(String key, String value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();
    }

    public void applyStringSet(String key, Set<String> value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, value).apply();
    }

    public void applyInt(String key, int value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value).apply();
    }

    public void applyLong(String key, long value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value).apply();
    }

    public void applyBoolean(String key, boolean value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();
    }

    public void applyFloat(String key, float value, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value).apply();
    }


    public String getString(String key, String defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getStringSet(key, defValue);
    }

    public int getInt(String key, int defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getLong(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue, boolean useDefaultSp, String spName){
        SharedPreferences sp = initSp(useDefaultSp, spName);
        return sp.getFloat(key, defValue);
    }




}
