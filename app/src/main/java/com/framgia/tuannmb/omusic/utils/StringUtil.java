package com.framgia.tuannmb.omusic.utils;

import com.framgia.tuannmb.omusic.BuildConfig;

public class StringUtil {
    public static String covertUrlFetchMusicGenre(String genre, int limit, int offset) {
        return String.format("%s%s%s&%s=%s&%s=%d&%s=%d", Constant.BASE_URL,
                Constant.PARA_MUSIC_GENRE, genre, Constant.CLIENT_ID,
                BuildConfig.API_KEY, Constant.LIMIT, limit, Constant.PARA_OFFSET, offset);
    }
}
