package com.github.evan.common_utils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import com.Evan.CommonUtil.BaseApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evan on 2017/10/5.
 */

public class PackageUtil {

    /**
     * 检查权限
     * @param permissions
     * @return  没有的权限,返回List不为null
     */
    public static @NonNull List<String> checkPermission(String... permissions) {
        List<String> returnValue = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            boolean isExistsPermission = ContextCompat.checkSelfPermission(BaseApplication.getApplication(), permission) == PackageManager.PERMISSION_GRANTED;
            if(!isExistsPermission){
                returnValue.add(permission);
            }
        }
        return returnValue;
    }

    /**
     * 获取包名
     * @return
     */
    public static String getPackageName(){
        Context application = BaseApplication.getApplication();
        return application.getPackageName();
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getVersionCode(){
        try {
            Context application = BaseApplication.getApplication();
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本名
     * @return
     */
    public static String getVersionName(){
        try {
            Context application = BaseApplication.getApplication();
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
