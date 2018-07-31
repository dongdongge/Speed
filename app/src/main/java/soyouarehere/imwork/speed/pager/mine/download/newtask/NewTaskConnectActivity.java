package soyouarehere.imwork.speed.pager.mine.download.newtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
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
        if (url == null || url.length() < 5) {
            LogUtil.e("url 不合法");
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("url",url);
            setResult(261,intent);
            finish();
        }


    }
}
