package com.framgia.tuannmb.omusic.downloadsong;

import android.support.annotation.StringDef;

@StringDef({DownloadKey.URL_DOWNLOAD, DownloadKey.SONG_TITLE})
public @interface DownloadKey {
    String URL_DOWNLOAD = "url_download";
    String SONG_TITLE = "song_title";
}
