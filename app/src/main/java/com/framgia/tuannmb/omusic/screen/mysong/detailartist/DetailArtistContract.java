package com.framgia.tuannmb.omusic.screen.mysong.detailartist;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface DetailArtistContract {
    interface View {
        void showSongsSuccess(List<Song> songs);
    }

    interface Presenter extends BasePresenter<DetailArtistContract.View> {
        void getMySongsByArtist(String artist);
    }
}
