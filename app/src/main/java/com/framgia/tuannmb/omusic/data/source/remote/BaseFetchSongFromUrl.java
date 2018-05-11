package com.framgia.tuannmb.omusic.data.source.remote;

import android.os.AsyncTask;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;
import com.framgia.tuannmb.omusic.utils.Constant;
import com.framgia.tuannmb.omusic.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFetchSongFromUrl extends AsyncTask<String, Void, List<Song>> {
    protected SongDataSource.OnFetchDataListener<Song> mListener;

    protected abstract JSONObject getJsonSong(JSONObject jsonObject) throws JSONException;

    public BaseFetchSongFromUrl(SongDataSource.OnFetchDataListener<Song> listener) {
        mListener = listener;
    }

    @Override
    protected List<Song> doInBackground(String... strings) {
        try {
            JSONObject jsonObject = new JSONObject(getJSONStringFromURL(strings[0]));
            return getSongsFromJsonObject(jsonObject);
        } catch (JSONException e) {
            mListener.onFetchDataFailure(e.getMessage());
        } catch (IOException e) {
            mListener.onFetchDataFailure(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        if (mListener != null) {
            mListener.onFetchDataSuccess(songs);
        }
    }

    protected List<Song> getSongsFromJsonObject(JSONObject jsonObject) throws JSONException {
        List<Song> songs = new ArrayList<>();
        JSONArray jsonCollection = jsonObject.getJSONArray(Song.SongEntity.COLLECTION);
        for (int i = 0; i < jsonCollection.length(); i++) {
            JSONObject jsonSong = getJsonSong(jsonCollection.getJSONObject(i));
            Song song = parseJsonObjectToSongObject(jsonSong);
            if (song != null) {
                songs.add(song);
            }
        }
        return songs;
    }

    protected Song parseJsonObjectToSongObject(JSONObject jsonSong) {
        Song song = new Song();
        try {
            JSONObject jsonUser = jsonSong.getJSONObject(Song.SongEntity.USER);
            String artworkUrl = jsonSong.getString(Song.SongEntity.ARTWORK_URL);
            String avatarUrl = jsonUser.getString(Song.SongEntity.AVATAR_URL);
            if (artworkUrl.equals(Constant.NULL_RESULT)) {
                artworkUrl = avatarUrl;
            }
            song.setArtworkUrl(artworkUrl);
            song.setAvatarUrl(parseArtworkUrlToBetter(avatarUrl));
            song.setDescription(jsonSong.getString(Song.SongEntity.DESCRIPTION));
            song.setDownloadable(jsonSong.getBoolean(Song.SongEntity.DOWNLOADABLE));
            song.setDownloadUrl(StringUtil.convertUrlDownloadSong(jsonSong.getString(Song.SongEntity.DOWNLOAD_URL)));
            song.setDuration(jsonSong.getLong(Song.SongEntity.DURATION));
            song.setId(jsonSong.getInt(Song.SongEntity.ID));
            song.setLikesCount(jsonSong.getInt(Song.SongEntity.LIKES_COUNT));
            song.setPlaybackCount(jsonSong.getInt(Song.SongEntity.PLAYBACK_COUNT));
            song.setTitle(jsonSong.getString(Song.SongEntity.TITLE));
            song.setUri(StringUtil.getUrlStreamSong(jsonSong.getString(Song.SongEntity.URI)));
            song.setUsername(jsonUser.getString(Song.SongEntity.USERNAME));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return song;
    }

    protected String parseArtworkUrlToBetter(String artworkUrl) {
        if (artworkUrl != null) {
            return artworkUrl.replace(Song.SongEntity.LARGE_IMAGE_SIZE,
                    Song.SongEntity.BETTER_IMAGE_SIZE);
        }
        return null;
    }

    protected String getJSONStringFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        httpURLConnection.setReadTimeout(Constant.READ_TIME_OUT);
        httpURLConnection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE);
        }
        br.close();
        httpURLConnection.disconnect();
        return sb.toString();
    }
}
