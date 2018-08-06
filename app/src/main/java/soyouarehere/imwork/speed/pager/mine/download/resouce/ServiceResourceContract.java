package soyouarehere.imwork.speed.pager.mine.download.resouce;

import java.util.List;

import io.reactivex.Observable;
import soyouarehere.imwork.speed.app.base.mvp.BaseModel;
import soyouarehere.imwork.speed.app.base.mvp.BasePresenter;
import soyouarehere.imwork.speed.app.base.mvp.BaseView;
import soyouarehere.imwork.speed.app.net.BaseEntity;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public interface ServiceResourceContract {
    interface View<T> extends BaseView{
        void success(List<ResourceRe> reList);
        void fail(String errMsg);
    }
    interface Module extends BaseModel{
        Observable<BaseEntity<List<ResourceRe>>>  getServiceResourceList();
    }
    abstract class Presenter extends BasePresenter<Module,View>{
        abstract void getServiceResourceList();
    }
}
