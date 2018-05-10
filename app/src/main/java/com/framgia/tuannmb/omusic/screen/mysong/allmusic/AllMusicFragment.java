package com.framgia.tuannmb.omusic.screen.mysong.allmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AllMusicFragment extends BaseFragment implements
        AllMusicContract.View, MySongsAdapter.SongItemClickListener {

    private AllMusicContract.presenter mPresenter;
    private MySongsAdapter mMySongsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AllMusicPresenter();
        mPresenter.setView(this);
        mMySongsAdapter = new MySongsAdapter(new ArrayList<Song>());
        mMySongsAdapter.setSongItemClickListener(this);
    }

    @Override
    protected void initializeComponents() {
        RecyclerView mRecyclerAllMusic = getView().findViewById(R.id.recycler_my_song);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerAllMusic.setLayoutManager(layoutManager);
        mRecyclerAllMusic.setAdapter(mMySongsAdapter);
        mMySongsAdapter.notifyDataSetChanged();
        mPresenter.getMyAllSongs();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_all_music;
    }

    @Override
    public void showMusicSuccess(List<Song> songs) {
        mMySongsAdapter.updateSongs(songs);
    }

    @Override
    public void onItemClick(int position, List<Song> songs) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).playSongWhenClickItem(position, songs);
        }
    }
}
