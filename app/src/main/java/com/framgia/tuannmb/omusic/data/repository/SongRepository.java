package com.framgia.tuannmb.omusic.data.repository;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;
import com.framgia.tuannmb.omusic.data.source.local.SongLocalDataSource;
import com.framgia.tuannmb.omusic.data.source.remote.SongRemoteDataSource;

import java.util.List;

public class SongRepository implements SongDataSource.RemoteDataSource,
        SongDataSource.LocalDataSource {
    private static SongRepository sSongRepository;
    private SongDataSource.LocalDataSource mLocalDataSource;
    private SongDataSource.RemoteDataSource mRemoteDataSource;

    public SongRepository(SongDataSource.LocalDataSource localDataSource,
                          SongDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static SongRepository getInstance() {
        if (sSongRepository == null) {
            sSongRepository = new SongRepository(
                    SongLocalDataSource.getInstance(),
                    SongRemoteDataSource.getInstance());
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

    @Override
    public void getSongsLocal(String category, SongDataSource.OnFetchDataListener<Song> listener) {

    }

    @Override
    public List<Category> getAlbums() {
        return mLocalDataSource.getAlbums();
    }

    @Override
    public List<Category> getArtists() {
        return mLocalDataSource.getArtists();
    }

    @Override
    public List<Song> getAllMySongs() {
        return mLocalDataSource.getAllMySongs();
    }

    @Override
    public List<Song> getMySongsByAlbum(String album) {
        return mLocalDataSource.getMySongsByAlbum(album);
    }

    @Override
    public List<Song> getMySongsByArtist(String artist) {
        return mLocalDataSource.getMySongsByArtist(artist);
    }
}
