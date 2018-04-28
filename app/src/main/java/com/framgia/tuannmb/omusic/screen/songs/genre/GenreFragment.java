package com.framgia.tuannmb.omusic.screen.songs.genre;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.tuannmb.omusic.App;
import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.detailmusic.MusicDetailActivity;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;
import com.framgia.tuannmb.omusic.screen.songs.SongsAdapter;
import com.framgia.tuannmb.omusic.utils.Constant;
import com.framgia.tuannmb.omusic.utils.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends BaseFragment implements GenreContract.View,
        SongsAdapter.SongItemClickListener {
    private SongsAdapter mSongsAdapter;

    private GenreContract.Presenter mPresenter;

    public static GenreFragment newInstance(String genre) {
        GenreFragment genreFragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(StringUtil.GENRE, genre);
        genreFragment.setArguments(args);
        return genreFragment;
    }

    @Override
    protected void initializeComponents() {
        RecyclerView recyclerSongs = getView().findViewById(R.id.recycler_songs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerSongs.setLayoutManager(layoutManager);
        mSongsAdapter = new SongsAdapter(new ArrayList<Song>());
        mSongsAdapter.setSongItemClickListener(this);
        recyclerSongs.setAdapter(mSongsAdapter);
        mSongsAdapter.notifyDataSetChanged();
        mPresenter = new GenrePresenter();
        mPresenter.setView(this);
        String genre = getArguments().getString(StringUtil.GENRE);
        mPresenter.loadMusic(genre, Constant.LIMIT_20, Constant.OFFSET_20);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    public void showMusicSuccess(List<Song> songs) {
        mSongsAdapter.updateSongs(songs);
    }

    @Override
    public void showMusicFailure(String message) {

    }

    @Override
    public void onClickListener(Song song) {
//        Intent intent = new Intent(getContext(), MusicDetailActivity.class);
//        intent.putExtra("song", song);
//        startActivity(intent);
    }

    @Override
    public void onClickListener(int position, List<Song> songs) {
        App.sSongs = songs;
        Intent intent = new Intent(getContext(), MusicDetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
