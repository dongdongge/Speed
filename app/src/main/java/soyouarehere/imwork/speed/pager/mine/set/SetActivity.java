package soyouarehere.imwork.speed.pager.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskManager;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.setClearSpace)
    Button setClearSpace;
    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        setClearSpace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setClearSpace:
                showToast();
                break;
        }
    }

    private void showToast(){
        new CustomAlertDialog(this, true, true, "是否清除所有的下载记录包括下载文件", new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {
                clearSpace();
            }

            @Override
            public void clickCancel() {

            }
        }).show();
    }

    private void clearSpace(){
        Map<String,?> mapFileList = PreferenceUtil.getDownloadFileInfoAll(this);
        LogUtil.e(mapFileList);
        if (mapFileList == null || mapFileList.isEmpty()){
            return;
        }
        Gson gson = new Gson();
        for (String str : mapFileList.keySet()) {
            DownloadFileInfo info =gson.fromJson(mapFileList.get(str).toString(),DownloadFileInfo.class);
            File file = new File(info.getFilePath(),info.getFileName());
            if (file.exists()){
                file.delete();
            }
            TaskManager.getInstance().removeBrokenRunnable(info.getFileName());
        }
        PreferenceUtil.clearDownloadFileInfo(BaseApplication.getInstance());

    }
}
