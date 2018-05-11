package com.framgia.tuannmb.omusic.screen.mysong.artist;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface ArtistContract {
    interface View {
        void showArtistsSuccess(List<Category> artists);
    }

    interface Presenter extends BasePresenter<ArtistContract.View> {
        void getArtists();
    }
}
