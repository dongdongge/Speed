package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;

public class ServiceResourceActivity extends BaseActivity<ServiceResourceContract.Presenter,ServiceResourceContract.Module> implements ServiceResourceContract.View{

    @BindView(R.id.service_rcv_resource_list)
    RecyclerView recyclerView;
    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_resource;
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void success(List list) {

    }

    @Override
    public void fail(String errMsg) {

    }
}
