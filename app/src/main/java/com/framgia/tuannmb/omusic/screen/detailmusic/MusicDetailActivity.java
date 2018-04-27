package com.framgia.tuannmb.omusic.screen.detailmusic;

import com.framgia.tuannmb.omusic.R;
import com.framgia.tuannmb.omusic.screen.BaseActivity;

public class MusicDetailActivity extends BaseActivity implements MusicDetailContract.View {
    private MusicDetailContract.Presenter mPresenter;
    @Override
    protected void initializeComponents() {
        mPresenter = new MusicDetailPresenter();
        mPresenter.setView(this);
    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_detail_music;
    }
}
