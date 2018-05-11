package com.framgia.tuannmb.omusic.downloadsong;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadSongService extends IntentService {
    private static final String PARENT_PATH =
            Environment.getExternalStorageDirectory() + "/"
                    + Environment.DIRECTORY_MUSIC + "/";
    private static final String FORMAT_SONG = ".mp3";

    public DownloadSongService() {
        super(null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        String urlSong = intent.getStringExtra(DownloadKey.URL_DOWNLOAD);
        String title = intent.getStringExtra(DownloadKey.SONG_TITLE);
        downloadSong(urlSong, title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void downloadSong(String urlSong, String title) {
        String destPath = PARENT_PATH + title + FORMAT_SONG;
        File file = new File(destPath);
        if (file.exists()) {
            file.delete();
        }
        try {
            URL url = new URL(urlSong);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(file);
            byte data[] = new byte[1024];
            long total = 0;
            int length;
            while ((length = input.read(data)) != -1) {
                total += length;
                //can publishing progress
                output.write(data, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
