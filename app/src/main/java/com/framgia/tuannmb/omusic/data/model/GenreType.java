package com.framgia.tuannmb.omusic.data.model;

import android.support.annotation.StringDef;

import static com.framgia.tuannmb.omusic.data.model.GenreType.ALL_AUDIO;
import static com.framgia.tuannmb.omusic.data.model.GenreType.ALL_MUSIC;
import static com.framgia.tuannmb.omusic.data.model.GenreType.ALTERNATIVEROCK;
import static com.framgia.tuannmb.omusic.data.model.GenreType.AMBIENT;
import static com.framgia.tuannmb.omusic.data.model.GenreType.CLASSICAL;
import static com.framgia.tuannmb.omusic.data.model.GenreType.COUNTRY;

@StringDef({ALL_MUSIC, ALL_AUDIO, ALTERNATIVEROCK, AMBIENT, CLASSICAL, COUNTRY})
public @interface GenreType {
    String ALL_MUSIC = "all-music";
    String ALL_AUDIO = "all-audio";
    String ALTERNATIVEROCK = "alternativerock";
    String AMBIENT = "ambient";
    String CLASSICAL = "classical";
    String COUNTRY = "country";

    String MY_ALL_MUSIC = "All Music";
    String ALBUM = "Album";
    String ARTIST = "Artist";
}
