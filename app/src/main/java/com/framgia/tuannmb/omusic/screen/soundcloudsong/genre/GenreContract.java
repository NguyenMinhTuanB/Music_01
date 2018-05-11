package com.framgia.tuannmb.omusic.screen.soundcloudsong.genre;

import android.content.Context;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface GenreContract {
    interface View {
        void showMusicSuccess(List<Song> songs);

        void showMusicFailure(String message);

        void downloadSuccess();
    }

    interface Presenter extends BasePresenter<GenreContract.View> {
        void loadMusic(String genre, int limit, int offSet);

        List<Song> getSongs();

        void downloadSong(Context context, Song song);
    }
}
