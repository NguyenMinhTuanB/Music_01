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
    private MediaPlayer mPlayer;
    private List<Song> mCurrentSongs;
    private int mIndexSongCurrent;
    private int mState = MediaPlayerState.IDLE;
    private OnListenerService mOnListenerService;

    public SongPlayerManager() {
        mPlayer = new MediaPlayer();
    }

    public void setOnListenerService(OnListenerService onListenerService) {
        mOnListenerService = onListenerService;
    }

    //Dang trang thai playing
    public boolean isOnlyPlaying() {
        return mState == MediaPlayerState.PLAYING;
    }

    public boolean isPause(){
        return mState == MediaPlayerState.PAUSED;
    }

    public boolean isPlaying() {
        return mState == MediaPlayerState.PLAYING || mState == MediaPlayerState.PAUSED;
    }

    public void setIndexSongCurrent(int indexSongCurrent) {
        mIndexSongCurrent = indexSongCurrent;
    }

    public Song getSongCurrent() {
        if (mCurrentSongs == null) {
            return null;
        }
        return mCurrentSongs.get(mIndexSongCurrent);
    }

    public void playSong() {
        if (mState == MediaPlayerState.IDLE || mState == MediaPlayerState.STOPPED) {
            try {
                Song song = getSongCurrent();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if (song.getUri() == null) {
                    nextSong();
                    return;
                }
                mPlayer.setDataSource(song.getUri());
                mPlayer.setOnPreparedListener(this);
                mPlayer.setOnCompletionListener(this);
                mPlayer.prepareAsync();
                mState = MediaPlayerState.PLAYING;
                mOnListenerService.updateUI();
            } catch (IOException e) {
                //TOAST
                nextSong();
                e.printStackTrace();
            }
            return;
        }
        //dang o playing thi se chuyen sang paused
        if (mState == MediaPlayerState.PLAYING) {
            mPlayer.pause();
            mState = MediaPlayerState.PAUSED;
            //mOnListenerService.stopForegroundServiceWhenPause();
            mOnListenerService.updateUI();
            return;
        }

        mPlayer.start();
        mState = MediaPlayerState.PLAYING;
        //mOnListenerService.startForegroundServiceAgain();
        mOnListenerService.updateUI();
    }

    public void stopSong() {
        if (mState == MediaPlayerState.PLAYING || mState == MediaPlayerState.PAUSED) {
            mPlayer.stop();
            mPlayer.reset();
            mState = MediaPlayerState.STOPPED;
        }
    }

    public void nextSong() {
        if (mIsShuffle) {
            mIndexSongCurrent = new Random().nextInt(mCurrentSongs.size());
        } else {
            if (mIndexSongCurrent == mCurrentSongs.size() - 1) {
                mIndexSongCurrent = 0;
            } else {
                mIndexSongCurrent++;
            }
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
                mediaPlayer.start();
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
        if(mIndexSongCurrent == mCurrentSongs.size() -1){

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

    public interface OnListenerService {
        void updateUI();
    }
}
