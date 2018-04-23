package com.framgia.tuannmb.omusic.screen;

public interface BasePresenter<T> {
    void setView(T view);

    void start();

    void stop();
}
