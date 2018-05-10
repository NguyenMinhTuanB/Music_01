package com.framgia.tuannmb.omusic.screen.mysong.allmusic;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;

import java.util.List;

public class AllMusicPresenter implements AllMusicContract.presenter {
    private AllMusicContract.View mView;
    private SongRepository mSongRepository;

    public AllMusicPresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void getMyAllSongs() {
        List<Song> songs = mSongRepository.getAllMySongs();
        mView.showMusicSuccess(songs);
    }

    @Override
    public void setView(AllMusicContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
