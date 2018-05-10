package com.framgia.tuannmb.omusic.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.framgia.tuannmb.omusic.data.model.Category;
import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MySongManager {
    private static final String FILE_FORMAT = "!=0";
    private Context mContext;
    private static MySongManager sMySongManager;

    private MySongManager(Context context) {
        mContext = context;
    }

    public static MySongManager getInstance(Context context) {
        if (sMySongManager == null) {
            sMySongManager = new MySongManager(context);
        }
        return sMySongManager;
    }

    public List<Song> getAllMySongs() {
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projections = new String[]{
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.ALBUM
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + FILE_FORMAT;
        Cursor c = mContext.getContentResolver().query(songUri,
                projections, selection, null, null);
        return getAllSongFromCursor(c);
    }

    private List<Song> getAllSongFromCursor(Cursor c) {
        List<Song> songs = new ArrayList<>();
        if (c == null) {
            return songs;
        }
        c.moveToFirst();
        int titleIndex = c.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int dataIndex = c.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        int artistIndex = c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
        int durationIndex = c.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
        int albumIndex = c.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        while (!c.isAfterLast()) {
            Song song = new Song();
            String title = c.getString(titleIndex);
            String uri = c.getString(dataIndex);
            String artist = c.getString(artistIndex);
            int duration = c.getInt(durationIndex);
            String album = c.getString(albumIndex);

            song.setTitle(title);
            song.setUri(uri);
            song.setUsername(artist);
            song.setDuration(duration);
            song.setAlbum(album);
            songs.add(song);
            c.moveToNext();
        }
        c.close();
        return songs;
    }

    public List<Category> getArtists() {
        List<Category> artists = new ArrayList<>();
        List<String> artistNames = getArtistNames();
        for (int i = 0; i < artistNames.size(); i++) {
            String artistName = artistNames.get(i);
            Category category = new Category(artistName,
                    getMySongByArtist(artistName).size());
            artists.add(category);
        }
        return artists;
    }

    public List<Song> getMySongByArtist(String artist) {
        List<Song> songs = new ArrayList<>();
        List<Song> myAllSongs = getAllMySongs();
        for (int i = 0; i < myAllSongs.size(); i++) {
            Song song = myAllSongs.get(i);
            if (song.getUsername().equals(artist)) {
                songs.add(song);
            }
        }
        return songs;
    }

    public List<Category> getAlbums() {
        List<Category> albums = new ArrayList<>();
        List<String> albumNames = getAlbumNames();
        for (int i = 0; i < albumNames.size(); i++) {
            String albumName = albumNames.get(i);
            Category category = new Category(albumName,
                    getMySongByAlbum(albumName).size());
            albums.add(category);
        }
        return albums;
    }

    public List<Song> getMySongByAlbum(String album) {
        List<Song> songs = new ArrayList<>();
        List<Song> myAllSongs = getAllMySongs();
        for (int i = 0; i < myAllSongs.size(); i++) {
            Song song = myAllSongs.get(i);
            if (song.getAlbum().equals(album)) {
                songs.add(song);
            }
        }
        return songs;
    }

    public List<String> getAlbumNames() {
        List<String> albumNames = new ArrayList<>();
        List<Song> myAllSongs = getAllMySongs();
        for (int i = 0; i < myAllSongs.size(); i++) {
            if (albumNames.indexOf(myAllSongs.get(i).getAlbum()) < 0) {
                albumNames.add(myAllSongs.get(i).getAlbum());
            }
        }
        return albumNames;
    }

    public List<String> getArtistNames() {
        List<String> artistNames = new ArrayList<>();
        List<Song> myAllSongs = getAllMySongs();
        for (int i = 0; i < myAllSongs.size(); i++) {
            if (artistNames.indexOf(myAllSongs.get(i).getUsername()) < 0) {
                artistNames.add(myAllSongs.get(i).getUsername());
            }
        }
        return artistNames;
    }
}
