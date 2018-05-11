package com.framgia.tuannmb.omusic.screen.mysong.detailalbum;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface DetailAlbumContract {
    interface View {
        void showSongsSuccess(List<Song> songs);
    }

    interface Presenter extends BasePresenter<DetailAlbumContract.View> {
        void getMySongsByAlbum(String album);
    }
}
