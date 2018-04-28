package com.framgia.tuannmb.omusic.screen.detailmusic;

import android.support.annotation.IntDef;

@IntDef({LevelList.PLAY, LevelList.PAUSE, LevelList.NON_SHUFFLE,
        LevelList.SHUFFLE, LevelList.NO_LOOP, LevelList.LOOP_ONE, LevelList.LOOP_LIST})
public @interface LevelList {
    int PLAY = 0;
    int PAUSE = 1;

    int NON_SHUFFLE = 2;
    int SHUFFLE = 3;

    int NO_LOOP = 4;
    int LOOP_ONE = 5;
    int LOOP_LIST = 6;
}
