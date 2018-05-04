package com.framgia.tuannmb.omusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.utils.StringUtil;
import com.framgia.tuannmb.omusic.utils.music.RepeatType;
import com.framgia.tuannmb.omusic.utils.music.SongPlayerManager;

import java.util.List;

public class MusicPlayerService extends Service implements
        SongPlayerManager.OnListenerService {
    private static final String ACTION_CHANGE_MEDIA_STATE = "action_change_media_state";
    private static final String ACTION_NEXT = "action_next";
    private static final String ACTION_PREVIOUS = "action_previous";
    private int mRepeatType;
    private OnListenerActivity mListenerActivity;
    private SongPlayerManager mSongPlayerManager;
    private final IBinder mIBinder = new MusicBinder();

    public void setListenerActivity(OnListenerActivity listenerActivity) {
        mListenerActivity = listenerActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongPlayerManager = new SongPlayerManager();
        mSongPlayerManager.setListenerService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handleIntent(intent);
        return mIBinder;
    }

    @Override
    public void updateSong(Song song) {
        if (mListenerActivity == null) {
            return;
        }
        mListenerActivity.updateSong(song);
    }

    public class MusicBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (action == null) {
            return;
        }
        switch (action) {
            case ACTION_CHANGE_MEDIA_STATE:
                playSong();
                break;
            case ACTION_NEXT:
                nextSong();
                break;
            case ACTION_PREVIOUS:
                previousSong();
                break;
            default:
                break;
        }
    }

    public void setIndexSongCurrent(int position) {
        mSongPlayerManager.setIndexSongCurrent(position);
    }

    public void setSongsList(List<Song> songs) {
        mSongPlayerManager.setCurrentSongs(songs);
    }

    public void playSong() {
        mSongPlayerManager.playSong();
    }

    public void playSong(int position) {
        if (mSongPlayerManager == null) {
            return;
        }
        mSongPlayerManager.playSong(position);
    }

    public void nextSong() {
        mSongPlayerManager.nextSong();
    }

    public void previousSong() {
        mSongPlayerManager.previousSong();
    }

    public Song getCurrentSong() {
        return mSongPlayerManager.getSongCurrent();
    }

    public int getCurrentTime() {
        return mSongPlayerManager.getCurrentTime();
    }

    public String getTextTimeSeekBar() {
        return StringUtil.getTextTimeSeekBar(getCurrentTime(),
                mSongPlayerManager.getSongCurrent().getDuration());
    }

    public String getTextExistTime() {
        return StringUtil.parseMilliSecondsToTimer(mSongPlayerManager.getSongCurrent()
                .getDuration() - getCurrentTime());
    }

    // Music dang trang thai playing hoac paused
    public boolean isPlaying() {
        return mSongPlayerManager.isPlaying();
    }

    //Music dang playing ma khong paused
    public boolean isOnlyPlaying() {
        return mSongPlayerManager.isOnlyPlaying();
    }

    public void seek(int progress) {
        mSongPlayerManager.seek(progress);
    }

    public void swipeRepeatType() {
        if (mSongPlayerManager.getRepeatType() == RepeatType.REPEAT_OFF) {
            mSongPlayerManager.setRepeatType(RepeatType.REPEAT_ONE);
            mRepeatType = RepeatType.REPEAT_ONE;
        } else if (mSongPlayerManager.getRepeatType() == RepeatType.REPEAT_ONE) {
            mSongPlayerManager.setRepeatType(RepeatType.REPEAT_ALL);
            mRepeatType = RepeatType.REPEAT_ALL;
        } else {
            mSongPlayerManager.setRepeatType(RepeatType.REPEAT_OFF);
            mRepeatType = RepeatType.REPEAT_OFF;
        }
    }

    public void shuffleSongs() {
        mSongPlayerManager.setShuffle(!mSongPlayerManager.isShuffle());
    }

    public boolean isShuffle() {
        return mSongPlayerManager.isShuffle();
    }

    public int getRepeatType() {
        return mRepeatType;
    }

    //Dung de ra lenh cho MainActivity cap nhat SongUI
    public interface OnListenerActivity {
        /**
         * Hàm này để khi
         */
        void updateSong(Song song);
    }
}
