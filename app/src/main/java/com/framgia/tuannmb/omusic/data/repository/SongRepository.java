package com.framgia.tuannmb.omusic.data.repository;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;
import com.framgia.tuannmb.omusic.data.source.remote.SongRemoteDataSource;

public class SongRepository implements SongDataSource.RemoteDataSource {
    private static SongRepository sSongRepository;
    private SongDataSource.RemoteDataSource mRemoteDataSource;

    private SongRepository(SongDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static SongRepository getInstance() {
        if (sSongRepository == null) {
            sSongRepository = new SongRepository(SongRemoteDataSource.getInstance());
        }
        return sSongRepository;
    }

    @Override
    public void getSongsRemote(String genre, int limit, int offSet,
                               SongDataSource.OnFetchDataListener<Song> listener) {
        if (mRemoteDataSource == null) {
            return;
        }
        mRemoteDataSource.getSongsRemote(genre, limit, offSet, listener);
    }
}
