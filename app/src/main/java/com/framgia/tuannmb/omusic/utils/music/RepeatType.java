package com.framgia.tuannmb.omusic.utils.music;

import android.support.annotation.IntDef;

@IntDef({RepeatType.REPEAT_OFF, RepeatType.REPEAT_ONE, RepeatType.REPEAT_ALL})
public @interface RepeatType {
    int REPEAT_OFF = 0;
    int REPEAT_ONE = 1;
    int REPEAT_ALL = 2;
}
