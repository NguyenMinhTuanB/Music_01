package com.framgia.tuannmb.omusic.screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseRecyclerViewAdapter<V extends BaseRecyclerViewAdapter.BaseViewHolder>
        extends RecyclerView.Adapter<V> {

    public BaseRecyclerViewAdapter() {
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
