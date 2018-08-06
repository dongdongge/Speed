package soyouarehere.imwork.speed.pager.mine.download.resouce;

import java.util.List;

import io.reactivex.Observable;
import soyouarehere.imwork.speed.app.net.ApiServer;
import soyouarehere.imwork.speed.app.net.BaseEntity;
import soyouarehere.imwork.speed.app.net.RetrofitUtil;
import soyouarehere.imwork.speed.app.net.rxjava.RxHelp;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public class ServiceResourceModel implements ServiceResourceContract.Module {
    @Override
    public Observable<BaseEntity<List<ResourceRe>>> getServiceResourceList() {
        return RetrofitUtil
                .build()
                .getApi(ApiServer.class)
                .getServiceResourceList()
                .compose(RxHelp.<BaseEntity<List<ResourceRe>>>applySchedulers());
    }
}
