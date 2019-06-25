package com.github.evan.common_utils.bean;

public class MusicInfo {
    private String filePath;
    private Mp3Information mp3Information;

    public MusicInfo() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Mp3Information getMp3Information() {
        return mp3Information;
    }

    public void setMp3Information(Mp3Information mp3Information) {
        this.mp3Information = mp3Information;
    }
}
