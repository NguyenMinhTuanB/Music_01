package com.framgia.tuannmb.omusic.screen.soundcloud;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private List<Song> mSongs;
    private LayoutInflater mLayoutInflater;

    public SongsAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAvatarSong;
        private TextView mTextSongName;
        private TextView mTextArtist;
        private TextView mTextDuration;
        private ImageView mDownloadSong;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatarSong = itemView.findViewById(R.id.image_song_avatar);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mDownloadSong = itemView.findViewById(R.id.image_download);
        }
    }
}
