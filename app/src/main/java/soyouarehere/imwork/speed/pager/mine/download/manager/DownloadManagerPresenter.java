package soyouarehere.imwork.speed.pager.mine.download.manager;
import io.reactivex.disposables.Disposable;
import soyouarehere.imwork.speed.app.net.rxjava.BaseObserver;
public class DownloadManagerPresenter extends DownloadManagerContract.Presenter {


    @Override
    void getMovieList(String page, String size) {

        mModel.getMovieList(page,size).subscribe(new BaseObserver<MovieResponse>(){

            @Override
            public void onSuccess(MovieResponse movieResponse) {
                mView.OnSuccess(movieResponse);
            }

            @Override
            public void onError(String msg) {
                mView.Onfailed(msg);
            }

            @Override
            public void onRequestStart(Disposable disposable) {
                mCompositeSubscription.add(disposable);
            }
        });
    }
}
