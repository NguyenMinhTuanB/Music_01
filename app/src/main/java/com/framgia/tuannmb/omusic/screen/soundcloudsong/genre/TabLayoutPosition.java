package com.framgia.tuannmb.omusic.screen.soundcloudsong.genre;

import android.support.annotation.IntDef;

@IntDef({TabLayoutPosition.TAB_ALL_MUSIC, TabLayoutPosition.TAB_ALL_AUDIO,
        TabLayoutPosition.AMBIENT, TabLayoutPosition.CLASSICAL,
        TabLayoutPosition.COUNTRY, TabLayoutPosition.ALTERNATIVEROCK})
public @interface TabLayoutPosition {
    int TAB_ALL_MUSIC = 0;
    int TAB_ALL_AUDIO = 1;
    int AMBIENT = 2;
    int CLASSICAL = 3;
    int COUNTRY = 4;
    int ALTERNATIVEROCK = 5;
    int TOTAL_TAB = 6;
}
