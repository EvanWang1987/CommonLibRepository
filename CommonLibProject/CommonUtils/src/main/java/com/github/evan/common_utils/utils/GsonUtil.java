package com.github.evan.common_utils.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Evan on 2017/12/10.
 */
public class GsonUtil {

    private static GsonUtil mInstance = null;

    public static GsonUtil getInstance() {
        if (null == mInstance) {
            synchronized (GsonUtil.class) {
                if (null == mInstance) {
                    mInstance = new GsonUtil();
                }
            }
        }
        return mInstance;
    }

    private Gson mGson;

    private GsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();   //序列化null值
        builder.setDateFormat(DateUtil.yyyy_MM_dd_HH_mm_ss);    //统一序列化日期格式
        builder.setPrettyPrinting();    //输出格式化
        mGson = builder.create();
    }

    public <DST> DST fromJson(String jsonString, Class<DST> clazz) {
        return mGson.fromJson(jsonString, clazz);
    }

    public <DST> List<DST> fromJson(String jsonString) {
        Type type = new TypeToken<List<DST>>() {
        }.getType();
        return mGson.fromJson(jsonString, type);
    }

    public <DST> String toJson(DST dst) {
        return mGson.toJson(dst);
    }

    public String toJsonWithObject(Object object) {
        return mGson.toJson(object);
    }

    public <DST> String toJson(List<DST> list) {
        Type type = new TypeToken<List<DST>>() {
        }.getType();
        return mGson.toJson(list, type);
    }
}
