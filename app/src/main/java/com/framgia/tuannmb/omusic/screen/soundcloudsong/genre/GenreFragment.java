package com.framgia.tuannmb.omusic.screen.soundcloudsong.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.GenreType;
import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.downloadsong.DownloadSongService;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.main.MainActivity;
import com.framgia.tuannmb.omusic.screen.soundcloudsong.SongsAdapter;
import com.framgia.tuannmb.omusic.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends BaseFragment implements GenreContract.View,
        SongsAdapter.SongItemClickListener {
    public static final String ARGUMENT_GENRE = "genre";
    private static final String TAG = GenreFragment.class.getSimpleName();
    private SongsAdapter mSongsAdapter;
    private GenreContract.Presenter mPresenter;

    private RecyclerView mRecyclerSongs;
    private LinearLayoutManager layoutManager;
    private ProgressBar mProgressBar;
    private int mPastVisibleItems;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private int mOffset;

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
        mOffset = Constant.OFFSET_10;
        mPresenter = new GenrePresenter();
        mPresenter.setView(this);
        mSongsAdapter = new SongsAdapter(new ArrayList<Song>());
        mSongsAdapter.setSongItemClickListener(this);
    }

    @Override
    protected void initializeComponents() {
        mProgressBar = getView().findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerSongs = getView().findViewById(R.id.recycler_songs);
        settingRecyclerView();
        loadMusic(mOffset);
        mRecyclerSongs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    loadMore();
                }
            }
        });
    }

    private void settingRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        mRecyclerSongs.setLayoutManager(layoutManager);
        mRecyclerSongs.setAdapter(mSongsAdapter);
        mSongsAdapter.notifyDataSetChanged();
    }

    private void loadMore() {
        mVisibleItemCount = layoutManager.getChildCount();
        mTotalItemCount = layoutManager.getItemCount();
        Log.d(TAG, "loadMore: " + mTotalItemCount);
        mPastVisibleItems = layoutManager.findFirstVisibleItemPosition();
        if ((mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
            mOffset += Constant.ADD_10;
            loadMusic(mOffset);
        }
    }

    private String getGenre() {
        if (getArguments() == null || getArguments().getString(ARGUMENT_GENRE) == null) {
            return GenreType.ALL_MUSIC;
        }
        return getArguments().getString(ARGUMENT_GENRE);
    }

    private void loadMusic(int offset) {
        mPresenter.loadMusic(getGenre(), Constant.LIMIT_10, offset);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    public void showMusicSuccess(List<Song> songs) {
        mSongsAdapter.updateSongs(songs);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMusicFailure(String message) {

    }

    @Override
    public void downloadSuccess() {
        Toast.makeText(getContext(), "Downloaded success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position, List<Song> songs) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).playSongWhenClickItem(position, songs);
        }
    }

    @Override
    public void onDownloadSongClick(Song song) {
        Toast.makeText(getContext(), "start download", Toast.LENGTH_SHORT).show();
        mPresenter.downloadSong(getContext(), song);
    }
}
