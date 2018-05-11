package com.framgia.tuannmb.omusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.notification.NotificationAction;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;
import com.framgia.tuannmb.omusic.utils.StringUtil;
import com.framgia.tuannmb.omusic.utils.music.RepeatType;
import com.framgia.tuannmb.omusic.utils.music.SongPlayerManager;

import java.util.List;

public class MusicPlayerService extends Service implements SongPlayerManager.OnListenerService {
    public static final String TAG = MusicPlayerService.class.getSimpleName();

    private static final int FOREGROUND_SERVICE = 101;
    private static final int ICON_SIZE = 128;

    private static final String PREVIOUS = "Prev";
    private static final String PLAY = "Play";
    private static final String NEXT = "Next";
    private static final String NOTIFICATION_CHANEL = "OMusic";

    private int mRepeatType;
    private OnListenerActivity mListenerActivity;
    private SongPlayerManager mSongPlayerManager;
    private final IBinder mIBinder = new MusicBinder();

    private Bitmap mBitmap;
    private NotificationCompat.Builder mBuilder;

    public void setListenerActivity(OnListenerActivity listenerActivity) {
        mListenerActivity = listenerActivity;
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate: MusicService Start");
        super.onCreate();
        mSongPlayerManager = new SongPlayerManager();
        mSongPlayerManager.setOnListenerService(this);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: MusicService DEtroy");
        super.onDestroy();
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
    public void updateUI() {
        Log.d(TAG, "updateUI: 1");
        mListenerActivity.updateUI();
        loadImage();
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
            case NotificationAction.START_FOREGROUND_ACTION:
                startForeground();
                break;
            case NotificationAction.PLAY_ACTION:
                playSong();
                break;
            case NotificationAction.NEXT_ACTION:
                //startForeground();
                nextSong();
                break;
            case NotificationAction.PREV_ACTION:
                //startForeground();
                previousSong();
                break;
            case NotificationAction.STOP_FOREGROUND_ACTION:
                stopForeground(true);
                stopSelf();
            default:
                break;
        }
    }

    private boolean mIsForeground;

    public void stopServiceWhenPause(){
        if(isOnlyPlaying()){
            stopForeground(true);
            mIsForeground = false;
        }
        else {
            startForeground();
            mIsForeground = true;
        }
    }

    private void startAgainForeground(){
        if(!mIsForeground){
            startForeground();
            mIsForeground = true;
        }
    }

    public void setIndexSongCurrent(int position) {
        mSongPlayerManager.setIndexSongCurrent(position);
    }

    public void setCurrentSongs(List<Song> songs) {
        mSongPlayerManager.setCurrentSongs(songs);
    }

    public void playSong() {
        stopServiceWhenPause();
        mSongPlayerManager.playSong();
        updateNotification(getCurrentSong().getTitle(),
                getCurrentSong().getUsername(), true);
    }

    public void playSong(int position) {
        //startAgainForeground();
        if (mSongPlayerManager == null) {
            return;
        }
        mSongPlayerManager.playSong(position);
    }

    public void nextSong() {
        //startAgainForeground();
        mSongPlayerManager.nextSong();
        updateNotification(getCurrentSong().getTitle(),
                getCurrentSong().getUsername(), false);
    }

    public void previousSong() {
        //startAgainForeground();
        mSongPlayerManager.previousSong();
        updateNotification(getCurrentSong().getTitle(),
                getCurrentSong().getUsername(), false);
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

    public boolean isPause() {
        return mSongPlayerManager.isPause();
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
        void updateUI();
    }

    public Notification getNotification(String title, String artist, boolean isPlayOrPause) {
        Intent ncIntent = new Intent(this, MainActivity.class);
        ncIntent.setAction(NotificationAction.MAIN_ACTION);
        ncIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0,
                ncIntent, 0);

        Intent prevIntent = new Intent(this, MusicPlayerService.class);
        prevIntent.setAction(NotificationAction.PREV_ACTION);
        PendingIntent pPrevIntent = PendingIntent.getService(this, 0,
                prevIntent, 0);

        Intent playIntent = new Intent(this, MusicPlayerService.class);
        playIntent.setAction(NotificationAction.PLAY_ACTION);
        PendingIntent pPlayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, MusicPlayerService.class);
        nextIntent.setAction(NotificationAction.NEXT_ACTION);
        PendingIntent pNextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc);
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "")
                .setContentTitle(title)
                .setContentText(artist)
                .setSmallIcon(R.drawable.ic_music)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setOngoing(false)
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(android.R.drawable.ic_media_previous, PREVIOUS, pPrevIntent)
                .addAction(getIdIcon(isPlayOrPause), PLAY, pPlayIntent)
                .addAction(android.R.drawable.ic_media_next, NEXT, pNextIntent)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2));
        setLargeIconBuilder();
        return mBuilder.build();
    }

    public void loadImage() {
        if (getCurrentSong() == null) return;
        Glide.with(this)
                .load(getCurrentSong().getArtworkUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = resource;

                        mBuilder.setLargeIcon(mBitmap);
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(FOREGROUND_SERVICE, mBuilder.build());
                    }
                });
    }

    private void setLargeIconBuilder() {
        if (mBuilder == null) return;
        if (mBitmap == null) {
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_head_phone_blue));
        } else {
            mBuilder.setLargeIcon(mBitmap);
        }
    }

    private int getIdIcon(boolean isPlayOrPause) {
        if (isPlayOrPause) {
            if (isOnlyPlaying()) {
                return android.R.drawable.ic_media_pause;
            } else {
                return android.R.drawable.ic_media_play;
            }
        }
        return android.R.drawable.ic_media_pause;
    }

    private void startForeground() {
        startForeground(FOREGROUND_SERVICE,
                getNotification(getCurrentSong().getTitle(),
                        getCurrentSong().getUsername(), false));
    }

    private void updateNotification(String title, String artist, boolean isClickPlayOrPause) {
        Notification notification = getNotification(title, artist, isClickPlayOrPause);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(FOREGROUND_SERVICE, notification);
    }

    public void setShuffle(boolean shuffle) {
        mSongPlayerManager.setShuffle(shuffle);
    }

    public void stopSong() {
        mSongPlayerManager.stopSong();
    }
}
