package soyouarehere.imwork.speed.pager.mine.download.resouce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskManager;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.PreferenceUtil;
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
                DownloadFileInfo info = formatDownloadFile(file);
                if (info==null){
                    showAlertDialog("该资源已经在任务列表中",file);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("DownloadFileInfo", new Gson().toJson(info));
                    setResult(261, intent);
                    finish();
                }
            }
        });
        recyclerView.setAdapter(serviceResourceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void showAlertDialog(String msg,ResourceFile file) {
        new CustomAlertDialog(this,"查看下载","重新下载",msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {
                String msg = PreferenceUtil.getDownloadFileInfo(BaseApplication.getInstance(),file.getName());
                Gson gson = new Gson();
                DownloadFileInfo info = gson.fromJson(msg, DownloadFileInfo.class);
                deleteDownloadInfo(file.getName());
                info.setProgress(0);
                info.setShowProgress("");
                TaskManager.getInstance().executeCallableTask(info);
                finish();
            }

            @Override
            public void clickCancel() {
                finish();
            }
        }).show();
    }
    // 删除文件以及下载记录信息;
    private void deleteDownloadInfo(String fileName){
        String filePosition = PreferenceUtil.getDownloadPotion(BaseApplication.getInstance());
        File file = new File(filePosition,fileName);
        if (file.exists()){
            file.delete();
        }
        PreferenceUtil.deleteDownloadFileInfo(BaseApplication.getInstance(),fileName);
    }
    public DownloadFileInfo formatDownloadFile(ResourceFile file){
        DownloadFileInfo info  = new DownloadFileInfo(file.getUrl());
        info.setFileName(file.getName());
        String filePosition = createFile(file.getName());
        if (filePosition==null){
            return null;
        }
        info.setFilePath(filePosition);

        info.setProgress(0);
        info.setShowProgressSize("0B");
        info.setShowProgress("0");
        info.setTotal(file.getFileLength());
        info.setShowSize(FileSizeUtil.FormetFileSize(file.getFileLength()));
        info.setSupportInterrupt(true);
        return info;
    }

    //创建文件名 检测默认的下载文件夹中是否存在该文件，
    private String createFile(String fileName){
        String filePosition = PreferenceUtil.getDownloadPotion(BaseApplication.getInstance());
        if (PreferenceUtil._isExetisDownloadFile(fileName)){
            LogUtil.e("任务存在");
            return null;
        }
        File file = new File(filePosition, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("createNewFile失败");
            return null;
        }
        return filePosition;
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
