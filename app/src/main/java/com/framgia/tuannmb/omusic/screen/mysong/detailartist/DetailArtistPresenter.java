package com.framgia.tuannmb.omusic.screen.mysong.detailartist;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;

import java.util.List;

public class DetailArtistPresenter implements DetailArtistContract.Presenter {
    private DetailArtistContract.View mView;
    private SongRepository mSongRepository;

    public DetailArtistPresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void getMySongsByArtist(String artist) {
        List<Song> songs = mSongRepository.getMySongsByArtist(artist);
        mView.showSongsSuccess(songs);
    }

    @Override
    public void setView(DetailArtistContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
