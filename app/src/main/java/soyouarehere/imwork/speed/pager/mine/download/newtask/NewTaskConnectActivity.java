package soyouarehere.imwork.speed.pager.mine.download.newtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.pager.mine.download.CustomBottomDialog;
import soyouarehere.imwork.speed.pager.mine.download.DownloadActivity;
import soyouarehere.imwork.speed.pager.mine.download.task.DownloadFileInfo;
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

//        if (url == null || url.length() < 5) {
//            LogUtil.e("url 不合法");
//            return;
//        } else {
//            Intent intent = new Intent();
//            intent.putExtra("url",url);
//            setResult(261,intent);
//            finish();
//        }
    }

    /**
     * 检查url
     * 创建downloadInfo
     * 检查是否存在该文件，
     */
    public void createDownloadFileInfo(String urlString) throws IOException {
        if (!UrlUtils.checkUrl(urlString)) {
            return;
        }
        //创建FileName
        String fileName = UrlUtils.getFileNameFromUrl(urlString);
        if (fileName == null) {
            return;
        }
        DownloadFileInfo fileInfo = new DownloadFileInfo(urlString);
        // 检查是否存在该文件
//        File file = BaseApplication.getInstance().getExternalCacheDir();
        Map<String, String> stringMap = FileUtil.findAllFiles(BaseApplication.getInstance().getExternalCacheDir().getPath());
        if (!stringMap.containsKey(fileName)) {
            File file = new File(stringMap.get(fileName));
            file.createNewFile();
            fileInfo.setFilePath(BaseApplication.getInstance().getExternalCacheDir().getPath());
            fileInfo.setFileName(fileName);
            fileInfo.setProgress(0);
            fileInfo.setShowSize("0");
            fileInfo.setShowProgress("0");
            fileInfo.setTotal(0);
            Intent intent = new Intent();
//        intent.putExtra("url", url);
            setResult(261, intent);
            finish();
        } else {
            new CustomAlertDialog(this, true, true, "任务已经在下载列表中", new CustomAlertDialog.OnClickInterface() {
                @Override
                public void clickSure() {

                }
            }).show();
        }
    }

}
