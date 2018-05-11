package com.framgia.tuannmb.omusic.screen.mysong.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends BaseFragment implements AlbumContract.View,
        AlbumsAdapter.AlbumItemListener {
    private AlbumContract.Presenter mPresenter;
    private AlbumsAdapter mAlbumsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AlbumPresenter();
        mPresenter.setView(this);
        mAlbumsAdapter = new AlbumsAdapter(new ArrayList<Category>());
        mAlbumsAdapter.setAlbumItemListener(this);
    }

    @Override
    protected void initializeComponents() {

        RecyclerView mRecyclerAlbum = getView().findViewById(R.id.recycler_album);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerAlbum.setLayoutManager(layoutManager);
        mRecyclerAlbum.setAdapter(mAlbumsAdapter);
        mAlbumsAdapter.notifyDataSetChanged();
        mPresenter.getMyAlbums();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_album;
    }

    @Override
    public void onItemClick(Category category) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).clickAlbumItem(category);
        }
    }

    @Override
    public void showAlbumsSuccess(List<Category> albums) {
        mAlbumsAdapter.updateAlbums(albums);
    }
}
