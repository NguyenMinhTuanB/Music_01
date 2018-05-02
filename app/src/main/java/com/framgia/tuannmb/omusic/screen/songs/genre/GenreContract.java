package com.framgia.tuannmb.omusic.screen.songs.genre;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface GenreContract {
    interface View {
        void showMusicSuccess(List<Song> songs);

        void showMusicFailure(String message);
    }

    interface Presenter extends BasePresenter<GenreContract.View> {
        void loadMusic(String genre, int limit, int offSet);

        List<Song> getSongs();
    }
}
