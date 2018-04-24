package com.framgia.tuannmb.omusic.data.source;

import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.List;

public interface SongDataSource {
    interface RemoteDataSource {
        void getSongsRemote(String genre, int limit, int offSet,
                            OnFetchDataListener<Song> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String message);
    }
}
