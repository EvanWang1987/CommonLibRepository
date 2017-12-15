package com.github.evan.common_utils.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

/**
 * Created by Evan on 2017/12/14.
 */

public class ScreenSnapShotUtil {

    /**
     *
     * 获取本地视频某帧
     *
     * @param localVideoPath
     * @param position
     * @return
     */
    public static Bitmap catchVideoSnapShot(String localVideoPath, long position) {
        MediaMetadataRetriever rev = new MediaMetadataRetriever();
        rev.setDataSource(localVideoPath);
        Bitmap bitmap = rev.getFrameAtTime(position, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        return bitmap;
    }


}
