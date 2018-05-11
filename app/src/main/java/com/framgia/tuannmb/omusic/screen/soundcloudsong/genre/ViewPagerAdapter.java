package com.framgia.tuannmb.omusic.screen.soundcloudsong.genre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.tuannmb.omusic.data.model.GenreType;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TabLayoutPosition.TAB_ALL_MUSIC:
                return GenreFragment.newInstance(GenreType.ALL_MUSIC);
            case TabLayoutPosition.TAB_ALL_AUDIO:
                return GenreFragment.newInstance(GenreType.ALL_AUDIO);
            case TabLayoutPosition.AMBIENT:
                return GenreFragment.newInstance(GenreType.AMBIENT);
            case TabLayoutPosition.CLASSICAL:
                return GenreFragment.newInstance(GenreType.CLASSICAL);
            case TabLayoutPosition.COUNTRY:
                return GenreFragment.newInstance(GenreType.COUNTRY);
            case TabLayoutPosition.ALTERNATIVEROCK:
                return GenreFragment.newInstance(GenreType.ALTERNATIVEROCK);
            default:
                return GenreFragment.newInstance(GenreType.ALL_MUSIC);
        }
    }

    @Override
    public int getCount() {
        return TabLayoutPosition.TOTAL_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TabLayoutPosition.TAB_ALL_MUSIC:
                return GenreType.ALL_MUSIC;
            case TabLayoutPosition.TAB_ALL_AUDIO:
                return GenreType.ALL_AUDIO;
            case TabLayoutPosition.AMBIENT:
                return GenreType.AMBIENT;
            case TabLayoutPosition.CLASSICAL:
                return GenreType.CLASSICAL;
            case TabLayoutPosition.COUNTRY:
                return GenreType.COUNTRY;
            case TabLayoutPosition.ALTERNATIVEROCK:
                return GenreType.ALTERNATIVEROCK;
            default:
                return GenreType.ALL_MUSIC;
        }
    }
}
