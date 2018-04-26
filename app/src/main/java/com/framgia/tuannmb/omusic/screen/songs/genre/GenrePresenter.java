package com.framgia.tuannmb.omusic.screen.songs.genre;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;

import java.util.List;

public class GenrePresenter implements GenreContract.Presenter,
        SongDataSource.OnFetchDataListener<Song> {
    private GenreContract.View mView;
    private SongRepository mSongRepository;

    public GenrePresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void loadMusic(String genre, int limit, int offSet) {
        mSongRepository.getSongsRemote(genre, limit, offSet, this);
    }

    @Override
    public void onFetchDataSuccess(List<Song> data) {
        mView.showMusicSuccess(data);
    }

    @Override
    public void onFetchDataFailure(String message) {

    }
}
