package soyouarehere.imwork.speed.pager.mine.download.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.task.bean.DownloadFileInfo;
import soyouarehere.imwork.speed.pager.mine.download.task.MyThreadPoolExecutor;
import soyouarehere.imwork.speed.pager.mine.download.task.TaskManager;

public class NewTaskConnectActivity extends BaseActivity {

    @BindView(R.id.et_input_content)
    EditText et_input_content;
    @BindView(R.id.bt_start_download)
    Button bt_start_download;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_task_connect;
    }

    /*  http://192.168.22.30:8080/static/file/download/我不是药神纪录片.mp4
        http://192.168.3.2:8080/static/file/broken/download/万界仙踪-37.mp4
        http://192.168.22.30:8080/static/file/broken/download/G海策-08.mp4
    */
    String httpAdress1 = "http://192.168.3.2:8080";
    String httpAdress2 = "http://192.168.3.2:8080";
    @Override
    public void create(Bundle savedInstanceState) {
        bt_start_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_input_content.getText().toString().trim();
                if (!str.contains("http://")){
                    str = httpAdress1+str;
                }
                checkUrlValid(str);
            }
        });
    }

    public void checkUrlValid(String url) {
        showLoading();
        MyThreadPoolExecutor.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                createDownloadFileInfo(url);
            }
        });
    }

    /**
     * 检查url
     * 创建downloadInfo
     * 检查是否存在该文件，
     */
    public void createDownloadFileInfo(String urlString){
        TaskManager.getInstance().prepareTask(urlString, new TaskManager.PrepareCallBack() {
            @Override
            public void onSuccess(DownloadFileInfo fileInfo) {
                dissLoading();
                Intent intent = new Intent();
                intent.putExtra("url", fileInfo.getUrl());
                intent.putExtra("DownloadFileInfo", new Gson().toJson(fileInfo));
                setResult(261, intent);
                finish();
            }
            @Override
            public void fail(String msg) {
                dissLoading();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(msg);
                    }
                });
            }
        });
    }

    public void showAlertDialog(String msg) {
        new CustomAlertDialog(this, true, true, msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {

            }
        }).show();
    }

}
