package com.framgia.tuannmb.omusic.utils.music;

import android.support.annotation.IntDef;

@IntDef({MediaState.IDLE, MediaState.PLAYING, MediaState.PAUSED,
        MediaState.PREPARE, MediaState.STOPPED})
public @interface MediaState {
    int IDLE = -1;
    int PLAYING = 0;
    int PAUSED = 1;
    int PREPARE = 2;
    int STOPPED = 3;
}
