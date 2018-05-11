package com.framgia.tuannmb.omusic.screen.mysong.artist;

import android.util.Log;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;

import java.util.List;

public class ArtistPresenter implements ArtistContract.Presenter {
    private static final String TAG = ArtistPresenter.class.getSimpleName();
    private ArtistContract.View mView;
    private SongRepository mSongRepository;

    public ArtistPresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void getArtists() {
        List<Category> artists = mSongRepository.getArtists();
        Log.d(TAG, "getArtists: "+artists.size());
        mView.showArtistsSuccess(artists);
    }

    @Override
    public void setView(ArtistContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
