package com.github.evan.common_utils.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Evan on 2017/12/10.
 */
public class EncodeUtil {


    public static String encodeByBase64(String value, String charset){
        try {
            byte[] bytes = value.getBytes(charset);
            String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
            return encoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static String decodeByBase64(String value, String charset){
        try {
            byte[] decode = Base64.decode(value.getBytes(charset), Base64.DEFAULT);
            return new String(decode, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }

}
