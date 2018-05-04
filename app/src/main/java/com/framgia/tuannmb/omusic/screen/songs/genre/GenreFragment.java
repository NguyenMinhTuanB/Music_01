package com.framgia.tuannmb.omusic.screen.songs.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.GenreType;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.songs.SongsAdapter;
import com.framgia.tuannmb.omusic.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends BaseFragment implements GenreContract.View,
        SongsAdapter.SongItemClickListener {
    public static final String ARGUMENT_GENRE = "genre";
    private SongsAdapter mSongsAdapter;
    private GenreContract.Presenter mPresenter;

    public static GenreFragment newInstance(String genre) {
        GenreFragment genreFragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_GENRE, genre);
        genreFragment.setArguments(args);
        return genreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GenrePresenter();
        mPresenter.setView(this);
        mSongsAdapter = new SongsAdapter(new ArrayList<Song>());
        mSongsAdapter.setSongItemClickListener(this);
    }

    @Override
    protected void initializeComponents() {
        RecyclerView recyclerSongs = getView().findViewById(R.id.recycler_songs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerSongs.setLayoutManager(layoutManager);
        recyclerSongs.setAdapter(mSongsAdapter);
        mSongsAdapter.notifyDataSetChanged();
        loadMusic();
    }

    private String getGenre() {
        if (getArguments() == null || getArguments().getString(ARGUMENT_GENRE) == null) {
            return GenreType.ALL_MUSIC;
        }
        return getArguments().getString(ARGUMENT_GENRE);
    }

    private void loadMusic() {
        mPresenter.loadMusic(getGenre(), Constant.LIMIT_20, Constant.OFFSET_20);
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
    public void onItemClick(int position, List<Song> songs) {
        //TODO
    }
}
