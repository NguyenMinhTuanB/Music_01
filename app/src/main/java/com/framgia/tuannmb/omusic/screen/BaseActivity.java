package com.framgia.tuannmb.omusic.screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutActivity());
        initializeComponents();
        registerListeners();
    }

    protected abstract void registerListeners();

    protected abstract void initializeComponents();

    protected abstract int getLayoutActivity();
}
