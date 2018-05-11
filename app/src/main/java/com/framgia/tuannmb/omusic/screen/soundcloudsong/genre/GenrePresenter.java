package com.framgia.tuannmb.omusic.screen.soundcloudsong.genre;

import android.content.Context;
import android.content.Intent;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.repository.SongRepository;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;
import com.framgia.tuannmb.omusic.downloadsong.DownloadKey;
import com.framgia.tuannmb.omusic.downloadsong.DownloadSongService;

import java.util.List;

public class GenrePresenter implements GenreContract.Presenter,
        SongDataSource.OnFetchDataListener<Song> {

    private List<Song> mSongs;
    private GenreContract.View mView;
    private SongRepository mSongRepository;

    public GenrePresenter() {
        mSongRepository = SongRepository.getInstance();
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void loadMusic(String genre, int limit, int offSet) {
        mSongRepository.getSongsRemote(genre, limit, offSet, this);
    }

    @Override
    public List<Song> getSongs() {
        return mSongs;
    }

    @Override
    public void downloadSong(Context context, Song song) {
        Intent intent = new Intent(context, DownloadSongService.class);
        intent.putExtra(DownloadKey.URL_DOWNLOAD, song.getDownloadUrl());
        intent.putExtra(DownloadKey.SONG_TITLE, song.getTitle());
        context.startService(intent);
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public void onFetchDataSuccess(List<Song> data) {
        mView.showMusicSuccess(data);
        setSongs(data);
    }

    @Override
    public void onFetchDataFailure(String message) {

    }
}
