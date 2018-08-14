package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class ServiceResourceActivity extends BaseActivity<ServiceResourcePresenter,ServiceResourceModel> implements ServiceResourceContract.View{

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
        mPresenter.getServiceResourceList();
    }

    @Override
    public void success(List list) {
        List<ResourceFile> list1  = list;
        LogUtil.e("接受到数据",list.toString(),"大小",list.size());
    }

    @Override
    public void fail(String errMsg) {

    }
}
