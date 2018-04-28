package com.framgia.tuannmb.omusic.utils;

import com.framgia.tuannmb.omusic.BuildConfig;

public class StringUtil {
    public static final String GENRE = "genre";

    public static String covertUrlFetchMusicGenre(String genre, int limit, int offset) {
        return String.format("%s%s%s&%s=%s&%s=%d&%s=%d", Constant.BASE_URL,
                Constant.PARA_MUSIC_GENRE, genre, Constant.CLIENT_ID,
                BuildConfig.API_KEY, Constant.LIMIT, limit, Constant.PARA_OFFSET, offset);
    }

    public static String parseMilliSecondsToTimer(long milliseconds) {
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        return hours > 0 ? String.format("%02d:%02d:%02d",
                hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    public static String getUrlStreamTrack(String uriTrack) {
        return String.format("%s/%s?%s=%s", uriTrack, Constant.PARA_STREAM,
                Constant.CLIENT_ID, BuildConfig.API_KEY);
    }
}
