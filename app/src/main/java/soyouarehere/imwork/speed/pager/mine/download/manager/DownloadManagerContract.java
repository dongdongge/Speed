package soyouarehere.imwork.speed.pager.mine.download.manager;

import io.reactivex.Observable;
import soyouarehere.imwork.speed.app.base.mvp.BaseModel;
import soyouarehere.imwork.speed.app.base.mvp.BasePresenter;
import soyouarehere.imwork.speed.app.base.mvp.BaseView;
import soyouarehere.imwork.speed.app.net.BaseEntity;

public interface DownloadManagerContract {

    interface View<T> extends BaseView{
        void Onfailed(String msg);
        void OnSuccess(T t);
    }
    interface Model extends BaseModel {
        Observable<BaseEntity<MovieResponse>> getMovieList(String page,String size);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getMovieList(String page,String size);
    }

}
