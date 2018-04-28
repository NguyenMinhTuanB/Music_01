package com.framgia.tuannmb.omusic.screen.main;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseActivity;
import com.framgia.tuannmb.omusic.screen.songs.SongsFragment;

import java.io.IOException;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MediaPlayer.OnPreparedListener {
    private DrawerLayout mDrawer;
    private SongsFragment mSongsFragment;

    private Song mSong;

    @Override
    protected void initializeComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.sound_cloud_music);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addSoundCloudFragment();
    }

    private void addSoundCloudFragment() {
        if (mSongsFragment == null) {
            mSongsFragment = new SongsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mSongsFragment)
                    .commit();
        }
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_sound_cloud:
                break;
            case R.id.nav_my_music:
                break;
            default:
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setSong(Song song) {
        mSong = song;
    }

    public Song getSong() {
        return mSong;
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
