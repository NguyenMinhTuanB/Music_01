package com.framgia.tuannmb.omusic.data.model;

public class Category {
    private String mName;
    private int mSongTotal;

    public Category() {
    }

    public Category(String name, int songTotal) {
        mName = name;
        mSongTotal = songTotal;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getSongTotal() {
        return mSongTotal;
    }

    public void setSongTotal(int songTotal) {
        mSongTotal = songTotal;
    }
}
