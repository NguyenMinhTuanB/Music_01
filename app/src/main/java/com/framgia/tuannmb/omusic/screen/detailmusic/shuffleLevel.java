package com.framgia.tuannmb.omusic.screen.detailmusic;

import android.support.annotation.IntDef;

@IntDef({shuffleLevel.SHUFFLE_OFF, shuffleLevel.SHUFFLE_ON})
public @interface shuffleLevel {
    int SHUFFLE_OFF = 0;
    int SHUFFLE_ON = 1;
}
