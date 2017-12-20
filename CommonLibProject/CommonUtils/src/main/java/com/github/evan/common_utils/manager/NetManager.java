package com.github.evan.common_utils.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.evan.common_utils.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Evan on 2017/10/6.
 */
public class NetManager {
    private static final String PING_URL = "www.qq.com";

    private static NetManager mInstance = null;
    public static NetManager getInstance(Context context){
        if(null == mInstance){
            synchronized (NetManager.class){
                mInstance = new NetManager(context);
            }
        }
        return mInstance;
    }

    private NetManager(Context context) {
        this.mContext = context.getApplicationContext();
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private Context mContext;
    private ConnectivityManager mConnectivityManager;

    /**
     * 判断网络是否已连接
     * @return
     */
    public boolean isNetConnected(){
        NetworkInfo mobileNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);       //手机网络
        NetworkInfo wifiNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);           //wifi网络
        NetworkInfo etherNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);      //以太网, 某些安卓设备带以太网卡
        return mobileNetwork.isConnected() || wifiNetwork.isConnected() || etherNetwork.isConnected();
    }

    /**
     * 判断网络是否正在连接
     * @return
     */
    public boolean isNetConnecting(){
        NetworkInfo mobileNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);       //手机网络
        NetworkInfo wifiNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);           //wifi网络
        NetworkInfo etherNetwork = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);      //以太网, 某些安卓设备带以太网卡
        return mobileNetwork.getState() == NetworkInfo.State.CONNECTING || wifiNetwork.getState() == NetworkInfo.State.CONNECTING || etherNetwork.getState() == NetworkInfo.State.CONNECTING;
    }

    /**
     * 判断网络是否可用(已连接不代表可用)
     * @return
     */
    public boolean isNetAvailable(){
        return isNetConnected() && ping(PING_URL);
    }

    /**
     * 是否是wifi网络
     * @return
     */
    public boolean isWifiNetwork(){
        return mConnectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 是否是手机网络
     * @return
     */
    public boolean isMobileNetwork(){
        return mConnectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 是否是以太网络
     * @return
     */
    public boolean isEthernetwork(){
        return mConnectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_ETHERNET;
    }

    public final boolean ping(String url) {
        String result = null;
        try {
            String ip = url;
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 200 " + ip);// ping3次, 每次200毫秒超时限制
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
                stringBuffer.append("\r\n");
            }
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                return true;
            } else {
            }
        } catch (IOException e) {
            Logger.printStackTrace(e);
        } catch (InterruptedException e) {
            Logger.printStackTrace(e);
        }
        return false;
    }
}
