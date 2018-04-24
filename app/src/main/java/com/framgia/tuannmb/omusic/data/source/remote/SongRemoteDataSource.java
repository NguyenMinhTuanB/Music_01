package com.framgia.tuannmb.omusic.data.source.remote;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;
import com.framgia.tuannmb.omusic.utils.StringUtil;

public class SongRemoteDataSource implements SongDataSource.RemoteDataSource {
    private static SongRemoteDataSource sSongRemoteDataSource;

    private SongRemoteDataSource() {
    }

    public static SongRemoteDataSource getInstance() {
        if (sSongRemoteDataSource == null) {
            sSongRemoteDataSource = new SongRemoteDataSource();
        }
        return sSongRemoteDataSource;
    }

    @Override
    public void getSongsRemote(String genre, int limit, int offSet,
                               SongDataSource.OnFetchDataListener<Song> listener) {
        new FetchGenresFromUrl(listener)
                .execute(StringUtil.covertUrlFetchMusicGenre(genre, limit, offSet));
    }
}
