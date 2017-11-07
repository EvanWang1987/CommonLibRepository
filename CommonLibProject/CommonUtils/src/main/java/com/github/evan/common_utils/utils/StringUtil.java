package com.github.evan.common_utils.utils;

/**
 * Created by Evan on 2017/11/3.
 */

public class StringUtil {

    public static boolean isEmpty(CharSequence value){
        return null == value || value.length() == 0;
    }

    public static boolean isNullString(CharSequence value){
        return value.toString().equalsIgnoreCase("null");
    }

    public static boolean isEmptyString(CharSequence value, boolean isFilteringNullString){
        return isFilteringNullString ? isEmpty(value) || isNullString(value) : isEmpty(value);
    }


}
