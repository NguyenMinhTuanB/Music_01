package com.framgia.tuannmb.omusic.utils.music;

import android.support.annotation.IntDef;

@IntDef({MediaState.IDLE, MediaState.PLAYING, MediaState.PAUSE,
        MediaState.PREPARE, MediaState.COMPLETE})
public @interface MediaState {
    int IDLE = -1;
    int PLAYING = 0;
    int PAUSE = 1;
    int PREPARE = 2;
    int COMPLETE = 3;
}
