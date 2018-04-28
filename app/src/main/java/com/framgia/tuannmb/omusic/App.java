package com.framgia.tuannmb.omusic;

import android.app.Application;

import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.List;

public class App extends Application {
    public static List<Song> sSongs;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
