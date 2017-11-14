package com.github.evan.common_utils.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.github.evan.common_utils.BaseApplication;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Evan on 2017/11/3.
 */
public class SpUtil {
    public static final String TEST_KEY = "sp_util_test_key";       //测试key, 每个SharedPreference文件生成后都会放一个测试key
    private ConcurrentHashMap<String, SharedPreferences> mSps = new ConcurrentHashMap<>();

    private static SpUtil mIns = null;

    public static SpUtil getIns() {
        if (null == mIns) {
            synchronized (SpUtil.class) {
                mIns = new SpUtil();
            }
        }
        return mIns;
    }

    private SpUtil() {
        initExistsSharedPreferenceOnDisk();
    }
    
    
    public boolean initSharedPreference(boolean isDefaultSp, String spName){
        return getSharedPreference(isDefaultSp, spName) != null;
    }

    public boolean removeSharedPreferencesFileAndMemoryCash(boolean isDefaultSp, String spName) {
        String targetSpName = isDefaultSp ? FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME : spName;
        boolean isFileExists = FileUtil.isSharedPreferenceFileExistsOnDisk(isDefaultSp, spName);
        if (isFileExists) {
            if (mSps.containsKey(targetSpName)) {
                mSps.remove(targetSpName);
            }
            String path = FileUtil.getSharedPreferenceFile(isDefaultSp, spName).getAbsolutePath();
            FileUtil.FileStatus fileStatus = FileUtil.deleteFile(path);
            return fileStatus == FileUtil.FileStatus.DELETE_SUCCESS;
        }

        return false;
    }

    public boolean containsSharedPreference(String fileName) {
        return mSps.containsKey(fileName);
    }

    public boolean containsKey(String fileName, String key) {
        return mSps.containsKey(fileName) && mSps.get(fileName).contains(key);
    }

    public boolean commitString(String key, String value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString(key, value).commit();
    }

    public boolean commitStringSet(String key, Set<String> value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putStringSet(key, value).commit();
    }

    public boolean commitInt(String key, int value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt(key, value).commit();
    }

    public boolean commitLong(String key, long value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putLong(key, value).commit();
    }

    public boolean commitBoolean(String key, boolean value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putBoolean(key, value).commit();
    }

    public boolean commitFloat(String key, float value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putFloat(key, value).commit();
    }

    public void applyString(String key, String value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();
    }

    public void applyStringSet(String key, Set<String> value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, value).apply();
    }

    public void applyInt(String key, int value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value).apply();
    }

    public void applyLong(String key, long value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value).apply();
    }

    public void applyBoolean(String key, boolean value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).apply();
    }

    public void applyFloat(String key, float value, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value).apply();
    }


    public String getString(String key, String defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getStringSet(key, defValue);
    }

    public int getInt(String key, int defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getLong(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue, boolean useDefaultSp, String spName) {
        SharedPreferences sp = getSharedPreference(useDefaultSp, spName);
        return sp.getFloat(key, defValue);
    }

    /**
     * 初始化所有已经生成的SharedPreference, 并内存缓存
     */
    private void initExistsSharedPreferenceOnDisk() {
        List<String> sharedPreferenceFiles = FileUtil.listSharedPreferenceFiles();
        if(null != sharedPreferenceFiles){
            int N = sharedPreferenceFiles.size();
            for (int i = 0; i < N; i++) {
                String name = sharedPreferenceFiles.get(i);
                int index = name.lastIndexOf(FileUtil.SUFFIX_NAME_XML);
                name = name.substring(0, index);
                SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
                boolean isContainsTestKey = sp.contains(TEST_KEY);
                if (!isContainsTestKey) {
                    SharedPreferences.Editor editor = sp.edit();
                    try {
                        String keyContent = TEST_KEY + " at " + DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                        editor.putString(TEST_KEY, keyContent).commit();
                    } catch (Exception e) {
                        Logger.printStackTrace(e);
                        //发生异常，不是SharedPreferences文件, 删除
                        FileUtil.removeSharedPreferenceFile(false, name);
                        continue;
                    }
                }
                mSps.put(name, sp);
            }
        }

        if (!mSps.containsKey(FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME)) {
            SharedPreferences defaultSp = BaseApplication.getApplication().getSharedPreferences(FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
            String keyContent = TEST_KEY + " at " + DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
            defaultSp.edit().putString(TEST_KEY, keyContent).commit();
            mSps.put(FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME, defaultSp);
        }
    }

    /**
     * 初始化一个SharedPreference, 并内存缓存
     * @param spName
     * @return
     */
    private SharedPreferences getSharedPreference(boolean isDefaultSp, String spName) {
        String targetSpName = isDefaultSp ? FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME : spName;
        boolean isFileOnDisk = FileUtil.isSharedPreferenceFileExistsOnDisk(isDefaultSp, spName);

        if (isFileOnDisk && mSps.containsKey(targetSpName)) {
            //硬盘，内存都缓存
            return mSps.get(targetSpName);
        }

        //先查看文件是否存在
        if (isFileOnDisk) {
            if (!mSps.containsKey(targetSpName)) {
                //文件存在，内存没缓存
                SharedPreferences sp = isLegitimateSharedPreference(isDefaultSp, spName);
                if (null != sp) {
                    //合法文件，重新缓存
                    mSps.put(targetSpName, sp);
                    return sp;
                }
            }
        }


        SharedPreferences sharedPreference = isLegitimateSharedPreference(false, targetSpName);
        mSps.put(targetSpName, sharedPreference);
        return sharedPreference;
    }

    private SharedPreferences isLegitimateSharedPreference(boolean isDefaultSpName, String spName) {
        String targetSpName = isDefaultSpName ? FileUtil.DEFAULT_SHARED_PREFERENCE_FILE_NAME : spName;
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(targetSpName, Context.MODE_PRIVATE);

        try {
            boolean containsTestKey = sharedPreferences.contains(TEST_KEY);
            if (!containsTestKey) {
                String keyContent = TEST_KEY + " at " + DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
                sharedPreferences.edit().putString(TEST_KEY, keyContent).commit();
            }
        } catch (Exception e) {
            Logger.printStackTrace(e);
            FileUtil.removeSharedPreferenceFile(isDefaultSpName, spName);
            SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(targetSpName, Context.MODE_PRIVATE);
            String keyContent = TEST_KEY + " at " + DateUtil.currentTime2String(DateUtil.yyyy_MM_dd_HH_mm_ss, Locale.getDefault());
            sp.edit().putString(TEST_KEY, keyContent).commit();
            return sp;
        }
        return sharedPreferences;
    }


}
