package com.framgia.tuannmb.omusic.screen.mysong.artist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {
    private List<Category> mArtists;
    private LayoutInflater mLayoutInflater;
    private ArtistItemListener mArtistItemListener;

    public ArtistsAdapter(List<Category> albums) {
        mArtists = albums;
    }

    public void setArtistItemListener(ArtistItemListener artistItemListener) {
        mArtistItemListener = artistItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mArtists.get(position));
    }

    @Override
    public int getItemCount() {
        return mArtists == null ? 0 : mArtists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextArtistName;
        private TextView mTextTotalSong;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextArtistName = itemView.findViewById(R.id.text_artist_name);
            mTextTotalSong = itemView.findViewById(R.id.text_total_song);
            itemView.setOnClickListener(this);
        }

        public void bindData(Category artist) {
            if (artist == null) {
                return;
            }
            mTextTotalSong.setText(String.valueOf(artist.getSongTotal()));
            mTextArtistName.setText(artist.getName());
        }

        @Override
        public void onClick(View view) {
            mArtistItemListener.onClickItem(mArtists.get(getAdapterPosition()));
        }
    }

    public List<Category> getArtists() {
        return mArtists;
    }

    public void updateArtists(List<Category> artist) {
        mArtists = artist;
        notifyDataSetChanged();
    }

    public interface ArtistItemListener {
        void onClickItem(Category category);
    }
}
