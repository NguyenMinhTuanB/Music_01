package com.framgia.tuannmb.omusic.screen.mysong.detailalbum;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;

import java.util.List;

public class DetailAlbumPresenter implements DetailAlbumContract.Presenter {
    private DetailAlbumContract.View mView;
    private SongRepository mSongRepository;

    public DetailAlbumPresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void setView(DetailAlbumContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getMySongsByAlbum(String album) {
        List<Song> songs = mSongRepository.getMySongsByAlbum(album);
        mView.showSongsSuccess(songs);
    }
}
