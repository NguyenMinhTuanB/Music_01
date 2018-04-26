package com.framgia.tuannmb.omusic.utils.music;

public interface MusicController {
    void initializeSongData();
    boolean play();
    boolean back();
    boolean next();
    void play(int position);
    void stop();
}
