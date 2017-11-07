package com.github.evan.common_utils.utils;

import com.google.gson.Gson;

/**
 * Created by Evan on 2017/10/7.
 */
public class JsonUtil {
    private static final Gson mGson = new Gson();

    public <Bean> Bean fromJson(String jsonString, Class<Bean> classType){
        return mGson.fromJson(jsonString, classType);
    }

}
