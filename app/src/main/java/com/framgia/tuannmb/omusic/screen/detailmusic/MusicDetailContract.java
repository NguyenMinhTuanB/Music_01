package com.framgia.tuannmb.omusic.screen.detailmusic;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

public interface MusicDetailContract {
    interface View {

    }

    interface Presenter extends BasePresenter<MusicDetailContract.View> {
        void play();

        void shuffle();

        void repeat();

        void previousSong();

        void nextSong();

        void stopSong();

        void updateSong();

        void setInfoSong(Song song);
    }
}
