package com.framgia.tuannmb.omusic;

import com.framgia.tuannmb.omusic.data.source.local.SongLocalDataSource;

public class App extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SongLocalDataSource.init(this);
    }
}
