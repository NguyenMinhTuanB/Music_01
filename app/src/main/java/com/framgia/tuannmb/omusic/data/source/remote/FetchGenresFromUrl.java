package com.framgia.tuannmb.omusic.data.source.remote;

import com.framgia.tuannmb.omusic.data.model.Song;
import com.framgia.tuannmb.omusic.data.source.SongDataSource;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchGenresFromUrl extends BaseFetchSongFromUrl {
    public FetchGenresFromUrl(SongDataSource.OnFetchDataListener<Song> listener) {
        super(listener);
    }

    @Override
    protected JSONObject getJsonSong(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(Song.SongEntity.TRACK);
    }
}
