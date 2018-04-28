package com.framgia.tuannmb.omusic.screen.songs.genre;

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
            case TabLayoutPosition.TAB_ONE:
                return GenreFragment.newInstance(GenreType.ALL_MUSIC);
            case TabLayoutPosition.TAB_TW0:
                return GenreFragment.newInstance(GenreType.ALL_AUDIO);
            case TabLayoutPosition.TAB_THREE:
                return GenreFragment.newInstance(GenreType.AMBIENT);
            case TabLayoutPosition.TAB_FOUR:
                return GenreFragment.newInstance(GenreType.CLASSICAL);
            case TabLayoutPosition.TAB_FIVE:
                return GenreFragment.newInstance(GenreType.COUNTRY);
            case TabLayoutPosition.TAB_SIX:
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
            case TabLayoutPosition.TAB_ONE:
                return GenreType.ALL_MUSIC;
            case TabLayoutPosition.TAB_TW0:
                return GenreType.ALL_AUDIO;
            case TabLayoutPosition.TAB_THREE:
                return GenreType.AMBIENT;
            case TabLayoutPosition.TAB_FOUR:
                return GenreType.CLASSICAL;
            case TabLayoutPosition.TAB_FIVE:
                return GenreType.COUNTRY;
            case TabLayoutPosition.TAB_SIX:
                return GenreType.ALTERNATIVEROCK;
            default:
                return GenreType.ALL_MUSIC;
        }
    }
}
