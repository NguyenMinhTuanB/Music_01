package com.framgia.tuannmb.omusic.data.source;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.List;

public interface SongDataSource {
    interface RemoteDataSource {
        void getSongsRemote(String genre, int limit, int offSet,
                            OnFetchDataListener<Song> listener);
    }

    interface LocalDataSource {
        void getSongsLocal(String category, OnFetchDataListener<Song> listener);

        List<Category> getAlbums();

        List<Category> getArtists();

        List<Song> getAllMySongs();

        List<Song> getMySongsByAlbum(String album);

        List<Song> getMySongsByArtist(String artist);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String message);
    }
}
