package com.framgia.tuannmb.omusic.screen.songs.genre;

import android.support.annotation.IntDef;

@IntDef({TabLayoutPosition.TAB_ONE, TabLayoutPosition.TAB_TW0, TabLayoutPosition.TAB_THREE,
        TabLayoutPosition.TAB_FOUR, TabLayoutPosition.TAB_FIVE, TabLayoutPosition.TAB_SIX})
public @interface TabLayoutPosition {
    int TAB_ONE = 0;
    int TAB_TW0 = 1;
    int TAB_THREE = 2;
    int TAB_FOUR = 3;
    int TAB_FIVE = 4;
    int TAB_SIX = 5;
    int TOTAL_TAB = 6;
}
