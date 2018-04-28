package com.framgia.tuannmb.omusic.utils.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.framgia.tuannmb.omusic.data.model.Song;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MusicPlayerManager implements MusicController, MediaPlayer.OnCompletionListener {
    private MediaPlayer mPlayer;

    private Context mContext;
    private List<Song> mSongs;

    private int mState = MediaState.IDLE;
    private int mIndexSong;
    private boolean mIsShuffle;
    private int mLoopMode = LoopType.NO_LOOP;

    public MusicPlayerManager(Context context) {
        mContext = context;
    }

    @Override
    public void initializeSongData() {

    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public boolean isShuffle() {
        return mIsShuffle;
    }

    public void setShuffle(boolean shuffle) {
        mIsShuffle = shuffle;
    }

    public int getLoopMode() {
        return mLoopMode;
    }

    public void setLoopMode(int loopMode) {
        mLoopMode = loopMode;
    }

    @Override
    public boolean play() {
        try {
            if (mState == MediaState.IDLE || mState == MediaState.STOPPED) {
                Song song = mSongs.get(mIndexSong);
                mPlayer.setDataSource(song.getUri());
                mPlayer.setOnCompletionListener(this);
                mPlayer.prepare();
                mPlayer.start();
                mState = MediaState.PLAYING;
                return true;
            } else if (mState == MediaState.PLAYING) {
                mPlayer.pause();
                mState = MediaState.PAUSED;
                return false;
            } else {
                mPlayer.start();
                mState = MediaState.PLAYING;
                return true;
            }

        } catch (IOException e) {
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean back() {
        if (mIndexSong == 0) {
            mIndexSong = mSongs.size();
        }
        mIndexSong--;
        stop();
        return play();
    }

    @Override
    public boolean next() {
        if (mIsShuffle) {
            mIndexSong = new Random().nextInt(mSongs.size());
        } else {
            mIndexSong = (mIndexSong + 1) % mSongs.size();
        }
        stop();
        return play();
    }

    @Override
    public void play(int position) {
        mIndexSong = position;
        stop();
        play();
    }

    @Override
    public void stop() {
        if (mState == MediaState.PLAYING || mState == MediaState.PAUSED) {
            mPlayer.stop();
            mPlayer.reset();
            mState = MediaState.STOPPED;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (mLoopMode) {
            case LoopType.NO_LOOP:
                if (mIndexSong < mSongs.size() - 1) {
                    mIndexSong++;
                    stop();
                    play();
                }
                break;
            case LoopType.LOOP_ONE:
                stop();
                play();
                break;
            case LoopType.LOOP_LIST:
                mIndexSong++;
                if (mIndexSong == mSongs.size()) {
                    mIndexSong = 0;
                }
                stop();
                play();
                break;
            default:
                break;
        }
    }
}
