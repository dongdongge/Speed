package soyouarehere.imwork.speed.pager.mine.download.resouce;

import java.util.List;

import io.reactivex.disposables.Disposable;
import soyouarehere.imwork.speed.app.net.rxjava.BaseObserver;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public class ServiceResourcePresenter extends ServiceResourceContract.Presenter {

    @Override
    void getServiceResourceList() {
        mModel.getServiceResourceList().subscribe(new BaseObserver<List<ResourceRe>>() {
            @Override
            public void onSuccess(List<ResourceRe> reList) {
                mView.success(reList);
            }

            @Override
            public void onError(String msg) {
                mView.fail(msg);
            }

            @Override
            public void onRequestStart(Disposable disposable) {

            }
        });
    }
}
