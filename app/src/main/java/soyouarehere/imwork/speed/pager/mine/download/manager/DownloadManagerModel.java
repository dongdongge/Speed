package soyouarehere.imwork.speed.pager.mine.download.manager;

import io.reactivex.Observable;
import soyouarehere.imwork.speed.app.net.ApiServer;
import soyouarehere.imwork.speed.app.net.BaseEntity;
import soyouarehere.imwork.speed.app.net.RetrofitUtil;
import soyouarehere.imwork.speed.app.net.rxjava.RxHelp;

public class DownloadManagerModel implements DownloadManagerContract.Model {
    @Override
    public Observable<BaseEntity<MovieResponse>> getMovieList(String page, String size) {
        return RetrofitUtil.build().getApi(ApiServer.class).getMovieList(page,size)
                .compose(RxHelp.<BaseEntity<MovieResponse>>applySchedulers()
        );
    }
}
