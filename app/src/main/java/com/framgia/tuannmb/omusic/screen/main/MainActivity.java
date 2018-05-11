package com.framgia.tuannmb.omusic.screen.main;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.downloadsong.DownloadKey;
import com.framgia.tuannmb.omusic.downloadsong.DownloadSongService;
import com.framgia.tuannmb.omusic.notification.NotificationAction;
import com.framgia.tuannmb.omusic.screen.BaseActivity;
import com.framgia.tuannmb.omusic.screen.detailmusic.DetailMusicActivity;
import com.framgia.tuannmb.omusic.screen.detailmusic.StateLevel;
import com.framgia.tuannmb.omusic.screen.mysong.MySongsFragment;
import com.framgia.tuannmb.omusic.screen.mysong.detailalbum.DetailAlbumFragment;
import com.framgia.tuannmb.omusic.screen.mysong.detailartist.DetailArtistFragment;
import com.framgia.tuannmb.omusic.screen.soundcloudsong.SongsFragment;
import com.framgia.tuannmb.omusic.service.MusicPlayerService;
import com.framgia.tuannmb.omusic.utils.StringUtil;
import com.framgia.tuannmb.omusic.utils.music.SongPlayerManager;

import java.util.List;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener,
        MusicPlayerService.OnListenerActivity{
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private SongsFragment mSongsFragment;
    private MySongsFragment mMySongsFragment;
    private DetailAlbumFragment mDetailAlbumFragment;
    private DetailArtistFragment mDetailArtistFragment;

    private ImageView mImagePlaySmall;
    private ImageView mImageNextSmall;
    private ImageView mImagePreviousSmall;
    private ImageView mImageSongSmall;
    private TextView mTextArtistAndTitle;

    private ConstraintLayout mSmallControlView;

    private int mProgress;

    private MusicPlayerService mPlayerService;
    private ServiceConnection mConnection;
    private boolean mIsConnect;
    private boolean mIsOnline;
    private boolean mIsForegroundRunning;

    @Override
    protected void registerListeners() {
        mImagePlaySmall.setOnClickListener(this);
        mImageNextSmall.setOnClickListener(this);
        mImagePreviousSmall.setOnClickListener(this);
        mSmallControlView.setOnClickListener(this);
    }

    @Override
    protected void initializeComponents() {
        initView();
        initViewController();
        addSoundCloudFragment();
        boundService();
        getIntentFromNotification();
        intVisibleControl();
    }

    private void intVisibleControl() {

    }

    private void getIntentFromNotification() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        if (intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(NotificationAction.MAIN_ACTION)) {
            showDetailMusic();
            setVisibleController(true);
        }
    }

    private void showDetailMusic() {
        Intent intent = new Intent(this, DetailMusicActivity.class);
        startActivity(intent);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.sound_cloud_music);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewController() {
        mSmallControlView = findViewById(R.id.play_small_view);
        mImagePlaySmall = findViewById(R.id.image_play_small);
        mImageNextSmall = findViewById(R.id.image_next_small);
        mImagePreviousSmall = findViewById(R.id.image_previous_small);
        mImageSongSmall = findViewById(R.id.image_song_small);
        mTextArtistAndTitle = findViewById(R.id.text_artist_and_title);
        mTextArtistAndTitle.setSelected(true);
    }

    private void boundService() {
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (iBinder instanceof MusicPlayerService.MusicBinder) {
                    mIsConnect = true;
                    mPlayerService = ((MusicPlayerService.MusicBinder) iBinder).getService();
                    mPlayerService.setListenerActivity(MainActivity.this);
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

    private void addSoundCloudFragment() {
        if (mSongsFragment == null) {
            mSongsFragment = new SongsFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_left_in, R.anim.anim_left_out,
                        R.anim.anim_right_in, R.anim.anim_right_out)
                .replace(R.id.container, mSongsFragment)
                .commit();
    }

    public void addMySongFragment() {
        if (mMySongsFragment == null) {
            mMySongsFragment = new MySongsFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_left_in, R.anim.anim_left_out,
                        R.anim.anim_right_in, R.anim.anim_right_out)
                .replace(R.id.container, mMySongsFragment)
                .commit();
    }

    public void addDetailAlbumFragment() {
        if (mDetailAlbumFragment == null) {
            mDetailAlbumFragment = new DetailAlbumFragment();
        }
        mDetailAlbumFragment.setCategory(getCategory());
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_left_in, R.anim.anim_left_out,
                        R.anim.anim_right_in, R.anim.anim_right_out)
                .replace(R.id.container, mDetailAlbumFragment)
                .commit();
    }

    public void addDetailArtistFragment() {
        if (mDetailArtistFragment == null) {
            mDetailArtistFragment = new DetailArtistFragment();
        }
        mDetailArtistFragment.setCategory(getCategory());
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_left_in, R.anim.anim_left_out,
                        R.anim.anim_right_in, R.anim.anim_right_out)
                .replace(R.id.container, mDetailArtistFragment)
                .commit();
    }

    public void setOnline(boolean online) {
        mIsOnline = online;
    }

    public boolean isOnline() {
        return mIsOnline;
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
                addSoundCloudFragment();
                mToolbar.setTitle(R.string.sound_cloud_music);
                break;
            case R.id.nav_my_music:
                addMySongFragment();
                mToolbar.setTitle(R.string.my_music);
                break;
            case R.id.nav_exit_music:
                stopForegroundServiceMusic();
                mPlayerService.stopSong();
                finish();
            default:
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void stopForegroundServiceMusic() {
        Intent intent = new Intent(this, MusicPlayerService.class);
        intent.setAction(NotificationAction.STOP_FOREGROUND_ACTION);
        startService(intent);
    }

    public void setVisibleController(boolean isVisible) {
        if (isVisible) {
            mSmallControlView.setVisibility(View.VISIBLE);
            return;
        }
        mSmallControlView.setVisibility(View.GONE);
    }

    public void setIndexCurrentSong(int position) {
        if (!mIsConnect) {
            mPlayerService.setIndexSongCurrent(0);
        }
        mPlayerService.setIndexSongCurrent(position);
    }

    public void setCurrentSongs(List<Song> songs) {
        mPlayerService.setCurrentSongs(songs);
    }

    public void playSong(int position) {
        mPlayerService.playSong(position);
    }

    private void loadSongAvatar(ImageView image, Song song) {
        Glide.with(this).load(song.getArtworkUrl())
                .placeholder(R.drawable.ic_head_phone_blue)
                .into(image);
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

    public void startMusicForegroundService() {
        if (mIsForegroundRunning) {
            return;
        }
        Intent startIntent = new Intent(this, MusicPlayerService.class);
        startIntent.setAction(NotificationAction.START_FOREGROUND_ACTION);
        startService(startIntent);
        mIsForegroundRunning = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play_small:
                changeStateFromController();
                updateControlUI();
                break;
            case R.id.image_next_small:
                nextFromController();
                updateControlUI();
                break;
            case R.id.image_previous_small:
                previousFromController();
                updateControlUI();
                break;
            case R.id.play_small_view:
                showDetailMusic();
                break;
            default:
                break;
        }
    }

    private void previousFromController() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.previousSong();
    }

    private void nextFromController() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.nextSong();
    }

    private void changeStateFromController() {
        if (!mIsConnect) {
            return;
        }
        mPlayerService.playSong();
        if (mPlayerService.isOnlyPlaying()) {
            mImagePlaySmall.setImageLevel(StateLevel.PAUSE);
            return;
        }
        mImagePlaySmall.setImageLevel(StateLevel.PLAY);
    }

    private int getLevelImagePlay() {
        if(mPlayerService == null){
            return StateLevel.PLAY;
        }
        if (mPlayerService.isOnlyPlaying()) {
            return StateLevel.PAUSE;
        } else {
            return StateLevel.PLAY;
        }
    }

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void updateUI() {
        Log.d(TAG, "updateUI: ");
        updateControlUI();
    }

    public void updateControlUI() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mImagePlaySmall.setImageLevel(getLevelImagePlay());
                mTextArtistAndTitle
                        .setText(StringUtil.getTextForControl(
                                getCurrentSong().getUsername(),
                                getCurrentSong().getTitle()));
                loadSongAvatar(mImageSongSmall, getCurrentSong());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        mIsConnect = false;
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPlayerService.setListenerActivity(this);
        if (!mIsConnect || getCurrentSong() == null) {
            return;
        }
        Log.d(TAG, "onRestart: ");
        updateControlUI();
        //updateUI();
    }

    public Song getCurrentSong() {
        return mPlayerService.getCurrentSong();
    }

    private Category mCategory;

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public void playSongWhenClickItem(int position, List<Song> songs) {
        setIndexCurrentSong(position);
        setCurrentSongs(songs);
        playSong(position);
        setVisibleController(true);
        //updateUI();
        startMusicForegroundService();
        showDetailMusic();
    }

    public void clickAlbumItem(Category category){
        setCategory(category);
        addDetailAlbumFragment();
    }
}
