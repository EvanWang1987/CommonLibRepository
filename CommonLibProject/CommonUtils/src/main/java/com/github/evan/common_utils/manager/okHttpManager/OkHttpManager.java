package com.github.evan.common_utils.manager.okHttpManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Evan on 2017/12/10.
 */
public class OkHttpManager {
    public static final String TEXT_MIME_TYPE = "text/plain; charset=utf-8";            //纯文本
    public static final String JSON_MIME_TYPE = "application/json; charset=utf-8";      //json
    public static final String XML_MIME_TYPE = "text/xml; charset=utf-8";               //xml
    public static final String HTML_MIME_TYPE = "text/html; charset=utf-8";             //html
    public static final String GIF_MIME_TYPE = "image/gif";                             //gif
    public static final String JPEG_MIME_TYPE = "image/jpeg";                           //jpeg
    public static final String JPG_MIME_TYPE = "application/x-jpg";                     //jpg
    public static final String PNG_MIME_TYPE = "image/png";                             //png
    public static final String PDF_MIME_TYPE = "application/pdf";                       //pdf
    public static final String MP3_MIME_TYPE = "audio/mp3";                             //mp3
    public static final String MP4_MIME_TYPE = "video/mpeg4";                           //mp4
    public static final String STREAM_MIME_TYPE = "application/octet-stream";           //流


    private static OkHttpManager mInstance = null;

    public static OkHttpManager getInstance() {
        if (null == mInstance) {
            synchronized (OkHttpManager.class) {
                if (null == mInstance) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }

    private OkHttpClient mHttpClient;

    private OkHttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.cookieJar(new BaseCookieJar());
        mHttpClient = builder.build();
    }

    public OkHttpClient getHttpClient(){
        return mHttpClient;
    }

    public Request getGetRequest(String url, Map<String, String> heads) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url).get();
        Set<Map.Entry<String, String>> entries = heads.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            builder.header(next.getKey(), next.getValue());
        }
        return builder.build();
    }

    public Request postRequest(String url, RequestBody requestBody, Map<String, String> heads) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url).post(requestBody);
        Set<Map.Entry<String, String>> entries = heads.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            builder.header(next.getKey(), next.getValue());
        }
        return builder.build();
    }

    public Request putRequest(String url, RequestBody requestBody, Map<String, String> heads) {
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url).put(requestBody);
        Set<Map.Entry<String, String>> entries = heads.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            builder.header(next.getKey(), next.getValue());
        }
        return builder.build();
    }

    public Request deleteRequest(String url, RequestBody requestBody, Map<String, String> heads) {
        Request.Builder builder = new Request.Builder();
        boolean isContainsBody = null != requestBody;
        builder = isContainsBody ? builder.url(url).delete(requestBody) : builder.url(url).delete();
        Set<Map.Entry<String, String>> entries = heads.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            builder.header(next.getKey(), next.getValue());
        }
        return builder.build();
    }

    public RequestBody createTextBody(String text) {
        return RequestBody.create(MediaType.parse(TEXT_MIME_TYPE), text);
    }

    public RequestBody createJsonBody(String json) {
        return RequestBody.create(MediaType.parse(JSON_MIME_TYPE), json);
    }

    public RequestBody createXmlBody(String xml) {
        return RequestBody.create(MediaType.parse(XML_MIME_TYPE), xml);
    }

    public RequestBody createHtmlBody(String html) {
        return RequestBody.create(MediaType.parse(HTML_MIME_TYPE), html);
    }

    public RequestBody createGifBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(GIF_MIME_TYPE), bytes);
    }

    public RequestBody createJpegBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(JPEG_MIME_TYPE), bytes);
    }

    public RequestBody createJpgBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(JPG_MIME_TYPE), bytes);
    }

    public RequestBody createPngBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(PNG_MIME_TYPE), bytes);
    }

    public RequestBody createPdfBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(PDF_MIME_TYPE), bytes);
    }

    public RequestBody createMp3Body(byte[] bytes) {
        return RequestBody.create(MediaType.parse(MP3_MIME_TYPE), bytes);
    }

    public RequestBody createMp4Body(byte[] bytes) {
        return RequestBody.create(MediaType.parse(MP4_MIME_TYPE), bytes);
    }

    public RequestBody createStreamBody(byte[] bytes) {
        return RequestBody.create(MediaType.parse(STREAM_MIME_TYPE), bytes);
    }

    public RequestBody createFormBody(Map<String, String> map, boolean isEncode) {
        if (null == map)
            return null;

        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if (isEncode) {
                builder.addEncoded(next.getKey(), next.getValue());
            } else {
                builder.add(next.getKey(), next.getValue());
            }
        }
        RequestBody formBody = builder.build();
        return formBody;
    }
}
