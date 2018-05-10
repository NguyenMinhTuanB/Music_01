package com.framgia.tuannmb.omusic.screen.mysong.allmusic;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BasePresenter;

import java.util.List;

public interface AllMusicContract {
    interface View {
        void showMusicSuccess(List<Song> mySongs);
    }

    interface presenter extends BasePresenter<AllMusicContract.View> {
        void getMyAllSongs();
    }
}
