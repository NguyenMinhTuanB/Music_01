package com.framgia.tuannmb.omusic.screen.mysong.album;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface AlbumContract {
    interface View {
        void showAlbumsSuccess(List<Category> albums);
    }

    interface Presenter extends BasePresenter<AlbumContract.View> {
        void getMyAlbums();
    }
}
