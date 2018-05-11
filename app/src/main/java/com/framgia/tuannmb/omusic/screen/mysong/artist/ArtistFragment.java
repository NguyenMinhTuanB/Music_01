package com.framgia.tuannmb.omusic.screen.mysong.artist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends BaseFragment implements ArtistContract.View,
        ArtistsAdapter.ArtistItemListener {
    private ArtistContract.Presenter mPresenter;
    private ArtistsAdapter mArtistsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ArtistPresenter();
        mPresenter.setView(this);
        mArtistsAdapter = new ArtistsAdapter(new ArrayList<Category>());
        mArtistsAdapter.setArtistItemListener(this);
    }

    @Override
    protected void initializeComponents() {
        RecyclerView recyclerArtists = getView().findViewById(R.id.recycler_artist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerArtists.setLayoutManager(layoutManager);
        recyclerArtists.setAdapter(mArtistsAdapter);
        mArtistsAdapter.notifyDataSetChanged();
        mPresenter.getArtists();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_artist;
    }

    @Override
    public void showArtistsSuccess(List<Category> artists) {
        mArtistsAdapter.updateArtists(artists);
    }

    @Override
    public void onClickItem(Category category) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setCategory(category);
            ((MainActivity) getActivity()).addDetailArtistFragment();
        }
    }
}
