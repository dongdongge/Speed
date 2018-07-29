package soyouarehere.imwork.speed.pager.mine.download.manager;

import android.os.Bundle;

import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;

public class DownloadManagerActivity extends BaseActivity<DownloadManagerPresenter,DownloadManagerModel> implements DownloadManagerContract.View<MovieResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_download_manager;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        mPresenter.getMovieList("xxx","xxx");
    }

    @Override
    public void Onfailed(String msg) {

    }

    @Override
    public void OnSuccess(MovieResponse movieResponse) {

    }
}
