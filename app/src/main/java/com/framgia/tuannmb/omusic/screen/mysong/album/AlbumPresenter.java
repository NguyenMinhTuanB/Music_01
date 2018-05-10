package com.framgia.tuannmb.omusic.screen.mysong.album;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;

import java.util.List;

public class AlbumPresenter implements AlbumContract.Presenter {
    private static final String TAG = AlbumPresenter.class.getSimpleName();
    private AlbumContract.View mView;
    private SongRepository mSongRepository;

    public AlbumPresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void setView(AlbumContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getMyAlbums() {
        List<Category> albums = mSongRepository.getAlbums();
        mView.showAlbumsSuccess(albums);
    }
}
