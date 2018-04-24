package com.framgia.tuannmb.omusic.screen.songs;

import android.support.v7.widget.RecyclerView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.screen.BaseFragment;

public class SongsFragment extends BaseFragment implements SongsContract.View {
    private RecyclerView mRecyclerViewMusics;
    @Override
    protected void initializeComponents() {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_sound_cloud_music;
    }
}
