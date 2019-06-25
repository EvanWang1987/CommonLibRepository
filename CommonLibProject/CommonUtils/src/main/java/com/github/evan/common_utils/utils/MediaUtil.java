package com.github.evan.common_utils.utils;

import android.media.MediaMetadataRetriever;
import com.github.evan.common_utils.bean.Mp3Information;

public class MediaUtil {

    public static Mp3Information getMp3Information(String filePath){
        if(StringUtil.isEmpty(filePath)){
            return null;
        }

        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(filePath);
            Logger.i("Getting the information from file which from: " + filePath);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            Logger.i("title: " + title);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            Logger.i("album: " + album);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            Logger.i("artist: " + artist);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            Logger.i("duration: " + duration);
            Mp3Information info = new Mp3Information();
            info.setTitle(title);
            info.setAlbum(album);
            info.setArtist(artist);
            info.setDuration(duration);

            return info;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return null;
    }


}
