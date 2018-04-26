package com.framgia.tuannmb.omusic.screen.songs.genre;

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

public class GenreFragment extends BaseFragment implements GenreContract.View {
    private SongsAdapter mSongsAdapter;

    private GenreContract.Presenter mPresenter;

    @Override
    protected void initializeComponents() {
        RecyclerView recyclerSongs = getView().findViewById(R.id.recycler_songs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerSongs.setLayoutManager(layoutManager);
        mSongsAdapter = new SongsAdapter(new ArrayList<Song>());
        recyclerSongs.setAdapter(mSongsAdapter);
        mSongsAdapter.notifyDataSetChanged();
        mPresenter = new GenrePresenter();
        mPresenter.setView(this);
        mPresenter.loadMusic(GenreType.ALL_MUSIC, Constant.LIMIT_20, Constant.OFFSET_20);
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
}
