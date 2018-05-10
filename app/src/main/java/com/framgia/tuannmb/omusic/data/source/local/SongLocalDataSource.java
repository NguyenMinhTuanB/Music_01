package com.framgia.tuannmb.omusic.data.source.local;

import android.content.Context;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;

import java.util.List;

public class SongLocalDataSource implements SongDataSource.LocalDataSource {
    private static SongLocalDataSource sSongLocalDataSource;
    private MySongManager mMySongManager;

    private SongLocalDataSource(Context context) {
        mMySongManager = MySongManager.getInstance(context);
    }

    public static void init(Context context) {
        if (sSongLocalDataSource == null) {
            sSongLocalDataSource = new SongLocalDataSource(context);
        }
    }

    public static SongLocalDataSource getInstance() {
        return sSongLocalDataSource;
    }


    @Override
    public void getSongsLocal(String category,
                              SongDataSource.OnFetchDataListener<Song> listener) {

    }

    @Override
    public List<Category> getAlbums() {
        return mMySongManager.getAlbums();
    }

    @Override
    public List<Category> getArtists() {
        return mMySongManager.getArtists();
    }

    @Override
    public List<Song> getAllMySongs() {
        return mMySongManager.getAllMySongs();
    }

    @Override
    public List<Song> getMySongsByAlbum(String album) {
        return mMySongManager.getMySongByAlbum(album);
    }

    @Override
    public List<Song> getMySongsByArtist(String artist) {
        return mMySongManager.getMySongByArtist(artist);
    }
}
