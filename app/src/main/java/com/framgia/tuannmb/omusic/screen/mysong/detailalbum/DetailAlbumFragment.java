package com.framgia.tuannmb.omusic.screen.mysong.detailalbum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;
import com.framgia.tuannmb.omusic.screen.mysong.allmusic.MySongsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailAlbumFragment extends BaseFragment
        implements DetailAlbumContract.View, MySongsAdapter.SongItemClickListener, View.OnClickListener {
    private DetailAlbumContract.Presenter mPresenter;
    private MySongsAdapter mMySongsAdapter;
    private Category mCategory;

    private ImageView mImageBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DetailAlbumPresenter();
        mPresenter.setView(this);
        mMySongsAdapter = new MySongsAdapter(new ArrayList<Song>());
        mMySongsAdapter.setSongItemClickListener(this);
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    @Override
    protected void initializeComponents() {
        mImageBack = getView().findViewById(R.id.image_back);
        mImageBack.setOnClickListener(this);
        TextView textAlbumName = getView().findViewById(R.id.text_album);
        textAlbumName.setText(getCategory().getName().toUpperCase());
        RecyclerView mRecyclerDetailAlbum = getView().findViewById(R.id.recycler_songs_of_album);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerDetailAlbum.setLayoutManager(layoutManager);
        mRecyclerDetailAlbum.setAdapter(mMySongsAdapter);
        mMySongsAdapter.notifyDataSetChanged();
        mPresenter.getMySongsByAlbum(getCategory().getName());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_album_detail;
    }

    @Override
    public void onItemClick(int position, List<Song> songs) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).playSongWhenClickItem(position, songs);
        }
    }

    @Override
    public void showSongsSuccess(List<Song> songs) {
        mMySongsAdapter.updateSongs(songs);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                backMySongFragment();
                break;
            default:
                break;
        }
    }

    private void backMySongFragment() {
        if (getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).addMySongFragment();
        }
    }
}
