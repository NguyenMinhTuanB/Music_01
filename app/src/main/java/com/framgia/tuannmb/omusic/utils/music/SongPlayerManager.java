package com.framgia.tuannmb.omusic.utils.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.utils.StringUtil;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class SongPlayerManager implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {

    private int mRepeatType = RepeatType.REPEAT_OFF;
    private boolean mIsShuffle;
    private OnListenerService mListenerService;
    private MediaPlayer mPlayer;
    private List<Song> mCurrentSongs;
    private int mIndexSongCurrent;
    private int mState = MediaPlayerState.IDLE;

    public SongPlayerManager() {
        mPlayer = new MediaPlayer();
    }

    public void setListenerService(OnListenerService listenerService) {
        mListenerService = listenerService;
    }

    //Dang trang thai playing
    public boolean isOnlyPlaying() {
        return mState == MediaPlayerState.PLAYING;
    }

    public boolean isPlaying() {
        return mState == MediaPlayerState.PLAYING || mState == MediaPlayerState.PAUSED;
    }

    public void setIndexSongCurrent(int indexSongCurrent) {
        mIndexSongCurrent = indexSongCurrent;
    }

    public Song getSongCurrent() {
        return mCurrentSongs.get(mIndexSongCurrent);
    }

    public void playSong() {
        if (mState == MediaPlayerState.IDLE || mState == MediaPlayerState.STOPPED) {
            Song song = mCurrentSongs.get(mIndexSongCurrent);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.setDataSource(song.getUri());
            } catch (IOException e) {
                nextSong();
            }
            //Mỗi khi chạy bài mới sẽ set thông tin mới
            if (mListenerService != null) {
                mListenerService.updateSong(getSongCurrent());
            }
            //chay seekbar
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.prepareAsync();
            mState = MediaPlayerState.PLAYING;
            return;
        }
        //dang o playing thi se chuyen sang paused
        if (mState == MediaPlayerState.PLAYING) {
            mPlayer.pause();
            mState = MediaPlayerState.PAUSED;
            return;
        }

        mPlayer.start();
        mState = MediaPlayerState.PLAYING;
    }

    public void stopSong() {
        if (mState == MediaPlayerState.PLAYING || mState == MediaPlayerState.PAUSED) {
            mPlayer.stop();
            mPlayer.reset();
            mState = MediaPlayerState.STOPPED;
        }
    }

    public void nextSong() {
        if (mIndexSongCurrent == mCurrentSongs.size()) {
            return;
        }
        if (mIsShuffle) {
            mIndexSongCurrent = new Random().nextInt(mCurrentSongs.size());
        } else {
            mIndexSongCurrent++;
        }
        stopSong();
        playSong();
    }

    public void previousSong() {
        if (mIndexSongCurrent == 0) {
            return;
        }
        mIndexSongCurrent--;
        stopSong();
        playSong();
    }

    public void setCurrentSongs(List<Song> currentSongs) {
        if (currentSongs == null) {
            return;
        }
        mCurrentSongs = currentSongs;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        switch (mRepeatType) {
            case RepeatType.REPEAT_OFF:
                repeatOff();
                break;
            case RepeatType.REPEAT_ONE:
                mPlayer.setLooping(true);
                break;
            case RepeatType.REPEAT_ALL:
                repeatAll();
                break;
            default:
                break;
        }
    }

    private void repeatAll() {
        mIndexSongCurrent++;
        if (mIndexSongCurrent == mCurrentSongs.size()) {
            mIndexSongCurrent = 0;
        }
        stopSong();
        playSong();
    }

    private void repeatOff() {
        if (mIndexSongCurrent < mCurrentSongs.size() - 1) {
            mIndexSongCurrent++;
            stopSong();
            playSong();
        }
    }

    public int getCurrentTime() {
        return mPlayer.getCurrentPosition();
    }

    public String getTextTimeSeekBar() {
        return StringUtil.getTextTimeSeekBar(getCurrentTime(),
                getSongCurrent().getDuration());
    }

    public void playSong(int position) {
        mIndexSongCurrent = position;
        stopSong();
        playSong();
    }

    public void seek(int progress) {
        mPlayer.seekTo(progress);
    }

    public int getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(int repeatType) {
        mRepeatType = repeatType;
    }

    public boolean isShuffle() {
        return mIsShuffle;
    }

    public void setShuffle(boolean shuffle) {
        mIsShuffle = shuffle;
    }

    //Dung de ra lenh cho MainActivity cap nhat SongUI
    public interface OnListenerService {
        void updateSong(Song song);
    }
}
