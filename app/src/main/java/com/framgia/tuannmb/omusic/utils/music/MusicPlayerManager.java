package com.framgia.tuannmb.omusic.utils.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.List;

public class MusicPlayerManager implements MusicController {
    private MediaPlayer mPlayer;

    private Context mContext;
    private List<Song> mSongs;

    private int mState = MediaState.IDLE;
    private int mIndexSong;
    private boolean mIsShuffle;
    private int mRepeatMode = LoopType.NO_LOOP;

    @Override
    public void initializeSongData() {

    }

    @Override
    public boolean play() {
        return false;
    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public void play(int position) {

    }

    @Override
    public void stop() {

    }
}
