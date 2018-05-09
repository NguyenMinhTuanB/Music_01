package com.framgia.tuannmb.omusic.screen.detailmusic;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseActivity;
import com.framgia.tuannmb.omusic.service.MusicPlayerService;
import com.framgia.tuannmb.omusic.utils.Constant;
import com.framgia.tuannmb.omusic.utils.StringUtil;
import com.framgia.tuannmb.omusic.utils.music.RepeatType;

public class DetailMusicActivity extends BaseActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, MusicPlayerService.OnListenerActivity {
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private ImageView mImagePrevious;
    private ImageView mImageShuffle;
    private ImageView mImageRepeat;
    private ImageView mImageDownload;
    private ImageView mImageSong;
    private ImageView mImageBack;

    private TextView mTextTitle;
    private TextView mTextArtist;
    private TextView mTextCurrentTime;
    private TextView mDuration;
    private SeekBar mSeekBar;

    private int mProgress;

    private MusicPlayerService mPlayerService;
    private ServiceConnection mConnection;
    private boolean mIsConnect;

    private SeekBarAsync mSeekBarAsync;

    @Override
    protected void registerListeners() {
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImageRepeat.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImageRepeat.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mImageBack.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void initializeComponents() {
        initView();
        boundService();
        runAnimation();
        updateSeekBar();
        updateUI();
    }

    private void runAnimation() {
        Animation animSongImage =
                AnimationUtils.loadAnimation(this, R.anim.anim_image_detail);
        mImageSong.startAnimation(animSongImage);
        Animation animText = AnimationUtils.loadAnimation(this, R.anim.anim_run_text);
        mTextTitle.startAnimation(animText);
    }

    private void initView() {
        mImagePlay = findViewById(R.id.image_play);
        mImageNext = findViewById(R.id.image_next);
        mImagePrevious = findViewById(R.id.image_previous);
        mImageShuffle = findViewById(R.id.image_shuffle);
        mImageRepeat = findViewById(R.id.image_repeat);
        mImageSong = findViewById(R.id.image_song);
        mImageDownload = findViewById(R.id.image_download);
        mImageBack = findViewById(R.id.image_back);

        mTextTitle = findViewById(R.id.text_artist_name);
        mTextArtist = findViewById(R.id.text_total);

        mDuration = findViewById(R.id.text_duration);
        mTextCurrentTime = findViewById(R.id.text_current_time);

        mSeekBar = findViewById(R.id.seek_bar);
    }

    private void boundService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (iBinder instanceof MusicPlayerService.MusicBinder) {
                    mIsConnect = true;
                    mPlayerService = ((MusicPlayerService.MusicBinder) iBinder).getService();
                    mPlayerService.setListenerActivity(DetailMusicActivity.this);
                    updateUI();
                } else {
                    mIsConnect = false;
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mIsConnect = false;
            }
        };

        Intent intent = new Intent(this, MusicPlayerService.class);
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_detail_music;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play:
                changeStateFromDetailMusic();
                break;
            case R.id.image_next:
                nextFromDetailMusic();
                break;
            case R.id.image_previous:
                previousFromDetailMusic();
                break;
            case R.id.image_repeat:
                repeatFromDetailMusic();
                break;
            case R.id.image_shuffle:
                shuffleFromDetailMusic();
                break;
            case R.id.image_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void setViewForButton() {
        mImagePlay.setImageLevel(getLevelImagePlay());
        mImageShuffle.setImageLevel(getLevelImageShuffle());
        mImageRepeat.setImageLevel(getLevelImageRepeat());
    }

    private int getLevelImagePlay() {
        if (mPlayerService == null) {
            return StateLevel.PLAY;
        }
        if (mPlayerService.isOnlyPlaying()) {
            return StateLevel.PAUSE;
        }

        return StateLevel.PLAY;
    }

    private int getLevelImageRepeat() {
        if (mPlayerService == null) {
            return RepeatType.REPEAT_OFF;
        }
        return mPlayerService.getRepeatType();
    }

    private int getLevelImageShuffle() {
        if (mPlayerService == null) {
            return shuffleLevel.SHUFFLE_OFF;
        }
        if (mPlayerService.isShuffle()) {
            return shuffleLevel.SHUFFLE_ON;
        }
        return shuffleLevel.SHUFFLE_OFF;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mProgress = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPlayerService.seek(mProgress);
        seekBar.setProgress(mProgress);
    }

    private void loadSongAvatar(ImageView image, Song song) {
        Glide.with(this).load(song.getArtworkUrl())
                .placeholder(R.drawable.detail_music).dontAnimate()
                .into(image);
    }

    private void setVisibleDownloadButton(boolean isVisible) {
        if (isVisible) {
            mImageDownload.setVisibility(View.INVISIBLE);
            return;
        }
        mImageDownload.setVisibility(View.GONE);
    }

    private void shuffleFromDetailMusic() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.shuffleSongs();

        if (mPlayerService.isShuffle()) {
            mImageShuffle.setImageLevel(shuffleLevel.SHUFFLE_ON);
            return;
        }
        mImageShuffle.setImageLevel(shuffleLevel.SHUFFLE_OFF);

    }

    private void repeatFromDetailMusic() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.swipeRepeatType();
        mImageRepeat.setImageLevel(mPlayerService.getRepeatType());
    }

    private void previousFromDetailMusic() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.previousSong();
        mImagePlay.setImageLevel(StateLevel.PAUSE);
    }

    private void nextFromDetailMusic() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.nextSong();
        mImagePlay.setImageLevel(StateLevel.PAUSE);
    }

    private void changeStateFromDetailMusic() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.playSong();
        if (mPlayerService.isOnlyPlaying()) {
            mImagePlay.setImageLevel(StateLevel.PAUSE);
            return;
        }
        mImagePlay.setImageLevel(StateLevel.PLAY);
    }

    public boolean isShuffle() {
        return mPlayerService.isShuffle();
    }

    public void setShuffle(boolean shuffle) {
        mPlayerService.setShuffle(shuffle);
    }

    @Override
    public void updateUI() {
        if (!mIsConnect) return;
        if (mSeekBarAsync == null) {
            updateSeekBar();
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setMax((int) getCurrentSong().getDuration());
                mTextTitle.setText(getCurrentSong().getTitle());
                mTextArtist.setText(getCurrentSong().getUsername());
                loadSongAvatar(mImageSong, getCurrentSong());
                setViewForButton();
            }
        });
    }

    private void updateSeekBar() {
        mSeekBarAsync = new SeekBarAsync();
        mSeekBarAsync.execute();
    }

    private class SeekBarAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(Constant.DELAY_TIME);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mTextCurrentTime.setText(StringUtil
                    .parseMilliSecondsToTimer(mPlayerService.getCurrentTime()));
            mSeekBar.setProgress(mPlayerService.getCurrentTime());
            mDuration.setText(mPlayerService.getTextExistTime());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        mIsConnect = false;
        if (mSeekBarAsync != null) {
            mSeekBarAsync.cancel(true);
        }
        super.onDestroy();
    }

    public Song getCurrentSong() {
        return mPlayerService.getCurrentSong();
    }
}
