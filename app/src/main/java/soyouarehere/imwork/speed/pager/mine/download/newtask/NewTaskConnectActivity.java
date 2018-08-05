package soyouarehere.imwork.speed.pager.mine.download.newtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.CustomBottomDialog;
import soyouarehere.imwork.speed.pager.mine.download.DownloadActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
import soyouarehere.imwork.speed.util.FileSizeUtil;
import soyouarehere.imwork.speed.util.FileUtil;
import soyouarehere.imwork.speed.util.UrlUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createDownloadFileInfo(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 检查url
     * 创建downloadInfo
     * 检查是否存在该文件，
     */
    public void createDownloadFileInfo(String urlString) throws IOException {
        if (!UrlUtils.checkUrl(urlString)) {
            LogUtil.e("检查url合法失败");
            return;
        }
        //创建FileName
        String fileName = UrlUtils.getFileNameFromUrl(urlString);
        if (fileName == null) {
            LogUtil.e("根据url创建文件名字失败");
            return;
        }
        DownloadFileInfo fileInfo = createNewFile(urlString, fileName);
        if (fileInfo == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showAlertDialog("createNewFile 文件失败");
                }
            });
        } else {
            Intent intent = new Intent();
            intent.putExtra("url", fileInfo.getUrl());
            intent.putExtra("DownloadFileInfo", new Gson().toJson(fileInfo));
            setResult(261, intent);
            finish();
        }
    }

    public DownloadFileInfo createNewFile(String url, String fileName) {
        DownloadFileInfo fileInfo = new DownloadFileInfo(url);
        fileInfo.setFileName(fileName);
        //创建文件名 检测文件夹中是否存在该文件，
        File fileAdress = BaseApplication.getInstance().getExternalCacheDir();
        Map<String, String> fileMap = FileUtil.findAllFiles(fileAdress.getPath());
        if (!fileMap.containsKey(fileName)) {
            File file = new File(fileAdress,fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LogUtil.e("创建文件失败");
                    e.printStackTrace();
                    return null;
                }
            }
            // 不存在就是新的文件
            fileInfo.setFilePath(fileAdress.getPath());
            fileInfo.setProgress(0);
            fileInfo.setShowProgressSize("0B");
            fileInfo.setShowProgress("0");
            long fileLength = getContentLength(fileInfo.getUrl());
            if (fileLength == -1) {
                LogUtil.e("获取文件大小失败");
                return null;
            }
            fileInfo.setTotal(fileLength);
            fileInfo.setShowSize(FileSizeUtil.FormetFileSize(fileLength));
        } else {
            LogUtil.e("该文件已经存在", fileMap.get(fileName));
            return null;
        }
        return fileInfo;
    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.code()==200) {
                long contentLength = response.body().contentLength();
                LogUtil.e("获取文件大小"+contentLength);
                response.close();
                return contentLength == 0 ? -1 : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public void showAlertDialog(String msg) {
        new CustomAlertDialog(this, true, true, msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {

            }
        }).show();
    }

}
