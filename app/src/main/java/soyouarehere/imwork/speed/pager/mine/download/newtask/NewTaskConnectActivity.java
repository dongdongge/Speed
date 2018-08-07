package soyouarehere.imwork.speed.pager.mine.download.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableJust;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
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

    @Override
    public void create(Bundle savedInstanceState) {
        bt_start_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_input_content.getText().toString().trim();
                checkUrlValid(str);
            }
        });
    }

    public void checkUrlValid(String url) {
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
        TaskManager.getInstance().checkDownUrl(urlString, new TaskManager.CheckUrlCallBack() {
            @Override
            public void onSuccess(DownloadFileInfo fileInfo) {
                Intent intent = new Intent();
                intent.putExtra("url", fileInfo.getUrl());
                intent.putExtra("DownloadFileInfo", new Gson().toJson(fileInfo));
                setResult(261, intent);
                finish();
            }
            @Override
            public void fail(String msg) {
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
