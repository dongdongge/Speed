package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * @author li.xiaodong
 * @desc
 * @time 2018/8/14 16:30
 */
public class ServiceResourceActivity extends BaseActivity<ServiceResourcePresenter, ServiceResourceModel> implements ServiceResourceContract.View {

    @BindView(R.id.service_rcv_resource_list)
    RecyclerView recyclerView;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_resource;
    }

    List<ResourceFile> resourceFiles;
    ServiceResourceAdapter serviceResourceAdapter;

    @Override
    public void create(Bundle savedInstanceState) {
        showLoading();
        mPresenter.getServiceResourceList();
        resourceFiles = new ArrayList<>();
        serviceResourceAdapter = new ServiceResourceAdapter(this, resourceFiles, new ServiceResourceAdapter.ServiceResourceListener() {
            @Override
            public void callBack(ResourceFile file) {
                LogUtil.e("你点击了", file.toString());
            }
        });
        recyclerView.setAdapter(serviceResourceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void success(List list) {
        dissLoading();
        if (list.size()==0){
            showToast("数据为空");
        }
        resourceFiles.clear();
        resourceFiles.addAll(list);
        serviceResourceAdapter.updateAll();
        LogUtil.e("接受到数据", list.toString(), "大小", list.size());
    }

    @Override
    protected String setToolbarTitle() {
        return "服务器资源列表";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resourceFiles!=null){
            resourceFiles.clear();
            resourceFiles = null;
        }
        if (serviceResourceAdapter!=null){
            serviceResourceAdapter=null;
        }
    }

    @Override
    public void fail(String errMsg) {
        dissLoading();
        showToast(errMsg);
    }
}
