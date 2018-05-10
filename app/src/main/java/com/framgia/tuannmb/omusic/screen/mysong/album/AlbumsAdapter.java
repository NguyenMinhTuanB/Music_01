package com.framgia.tuannmb.omusic.screen.mysong.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.data.model.Category;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    private List<Category> mAlbums;
    private LayoutInflater mLayoutInflater;
    private AlbumItemListener mAlbumItemListener;

    public AlbumsAdapter(List<Category> albums) {
        mAlbums = albums;
    }

    public void setAlbumItemListener(AlbumItemListener albumItemListener) {
        mAlbumItemListener = albumItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbums == null ? 0 : mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextAlbumName;
        private TextView mTextTotalSong;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextAlbumName = itemView.findViewById(R.id.text_album_name);
            mTextTotalSong = itemView.findViewById(R.id.text_total_song);
            itemView.setOnClickListener(this);
        }

        public void bindData(Category category) {
            if (category == null) {
                return;
            }
            mTextTotalSong.setText(String.valueOf(category.getSongTotal()));
            mTextAlbumName.setText(category.getName());
        }

        @Override
        public void onClick(View view) {
            mAlbumItemListener.onClickItem(mAlbums.get(getAdapterPosition()));
        }
    }

    public List<Category> getAlbums() {
        return mAlbums;
    }

    public void updateAlbums(List<Category> albums) {
        mAlbums = albums;
        notifyDataSetChanged();
    }

    public interface AlbumItemListener {
        void onClickItem(Category category);
    }
}
