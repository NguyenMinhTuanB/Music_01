package com.framgia.tuannmb.omusic.screen.songs;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.screen.BaseFragment;
import com.framgia.tuannmb.omusic.screen.songs.genre.ViewPagerAdapter;

public class SongsFragment extends BaseFragment implements SongsContract.View {

    @Override
    protected void initializeComponents() {
        ViewPager viewPager = getView().findViewById(R.id.view_pager_music);
        setUpViewPager(viewPager);
        TabLayout tabLayout = getView().findViewById(R.id.tab_layout_genre);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_songs;
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
