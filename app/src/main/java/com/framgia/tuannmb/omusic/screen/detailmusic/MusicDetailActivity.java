package com.framgia.tuannmb.omusic.screen.detailmusic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.framgia.tuannmb.omusic.App;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseActivity;
import com.framgia.tuannmb.omusic.utils.music.MusicPlayerManager;

import java.io.IOException;

public class MusicDetailActivity extends BaseActivity implements
        MusicDetailContract.View, MediaPlayer.OnPreparedListener {


    private static final String TAG = "MusicDetailActivity";
    private MusicDetailContract.Presenter mPresenter;
    private MediaPlayer player;


    private MusicPlayerManager mPlayerManager;

    @Override
    protected void initializeComponents() {
        mPresenter = new MusicDetailPresenter();
        mPresenter.setView(this);


        mPlayerManager = new MusicPlayerManager(this);
        mPlayerManager.setSongs(App.sSongs);
        int position = getIntent().getIntExtra("position", 0);
        mPlayerManager.play(position);
    }

    private void playSongTest(Song song) {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            Log.d(TAG, "playSongTest: TEST..");
            player.setDataSource(song.getUri());
            player.setOnPreparedListener(this);
            player.prepareAsync();
            Log.d(TAG, "playSongTest: PREPARE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_detail_music;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared: START");
        mediaPlayer.start();
    }
}
