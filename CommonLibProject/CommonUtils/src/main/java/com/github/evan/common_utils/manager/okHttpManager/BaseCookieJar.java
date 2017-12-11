package com.github.evan.common_utils.manager.okHttpManager;

import com.github.evan.common_utils.utils.EncodeUtil;
import com.github.evan.common_utils.utils.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Evan on 2017/12/10.
 */
public class BaseCookieJar implements CookieJar {
    private static final String COOKIE_FILE_NAME = "cookie_file";
    private static final String HOST_KEY = "hosts";
    private SpUtil mSpUtil;
    private Map<String, Set<String>> mCookies = new HashMap<>();


    public BaseCookieJar() {
        mSpUtil = SpUtil.getIns();
        mSpUtil.initSharedPreference(false, COOKIE_FILE_NAME);
        Set<String> hosts = mSpUtil.getStringSet(HOST_KEY, null, false, COOKIE_FILE_NAME);
        Iterator<String> iterator = hosts.iterator();
        while (iterator.hasNext()) {
            String host = iterator.next();
            Set<String> cookies = mSpUtil.getStringSet(host, null, false, COOKIE_FILE_NAME);
            mCookies.put(host, cookies);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.isEmpty())
            return;

        String host = url.host();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieToString = cookie.toString();
            String encodedCookie = EncodeUtil.encodeByBase64(cookieToString, "UTF-8");
            set.add(encodedCookie);
        }

        mCookies.put(host, set);
        mSpUtil.commitStringSet(host, set, false, COOKIE_FILE_NAME);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> returnValue = new ArrayList<>();
        String host = url.host();
        Set<String> encodedCookieString = mCookies.get(host);
        if (null != encodedCookieString) {
            Iterator<String> iterator = encodedCookieString.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                String cookieString = EncodeUtil.decodeByBase64(next, "UTF-8");
                Cookie cookie = Cookie.parse(url, cookieString);
                returnValue.add(cookie);
            }
        }
        return returnValue;
    }
}
