package com.framgia.tuannmb.omusic.screen.mysong;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.GenreType;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.mysong.album.AlbumFragment;
import com.framgia.tuannmb.omusic.screen.mysong.allmusic.AllMusicFragment;
import com.framgia.tuannmb.omusic.screen.mysong.artist.ArtistFragment;

public class MySongsFragment extends BaseFragment implements MySongsContract.View {
    @Override
    protected void initializeComponents() {
        ViewPager viewPager = getView().findViewById(R.id.view_pager_music);
        setUpViewPager(viewPager);
        TabLayout tabLayout = getView().findViewById(R.id.tab_layout_genre);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_music;
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerMyMusicAdapter adapter =
                new ViewPagerMyMusicAdapter(getChildFragmentManager());
        adapter.addFragment(new AllMusicFragment(), GenreType.MY_ALL_MUSIC);
        adapter.addFragment(new AlbumFragment(), GenreType.ALBUM);
        adapter.addFragment(new ArtistFragment(), GenreType.ARTIST);
        viewPager.setAdapter(adapter);
    }
}
