package soyouarehere.imwork.speed.pager.set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;
import soyouarehere.imwork.speed.pager.mine.download.CustomAlertDialog;
import soyouarehere.imwork.speed.util.PhoneLauncherUtils;
import soyouarehere.imwork.speed.util.PreferenceUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.setClearSpace)
    Button setClearSpace;

    @BindView(R.id.setPhoneSpaceSize)
    Button setPhoneSpaceSize;

    @BindView(R.id.setServiceHttp)
    Button setServiceHttp;

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
        setPhoneSpaceSize.setOnClickListener(this);
        setServiceHttp.setOnClickListener(this);
        String serviceAddress = PreferenceUtil.getConfigBaseUrl(BaseApplication.getInstance());
        setServiceHttp.setText("设置服务器地址  (当前地址："+serviceAddress+")");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setClearSpace:
                showToastS("是否清除所有的下载记录包括下载文件", "清除");
                break;
            case R.id.setPhoneSpaceSize:
                showToastS(SetHelp.getPhoneSize(), "");
                break;
            case R.id.setServiceHttp:
                showCustomEditText();
                break;
            default:
                break;
        }
    }
    /**
     *
     * 更换服务器地址的弹窗
     * */
    private void showCustomEditText(){
        new CustomEditTextDialog(this, new CustomEditTextDialog.OnClickDialogListener() {
            @Override
            public void clickSure(String content) {
                LogUtil.e(content);
                PreferenceUtil.putConfigBaseUrl(BaseApplication.getInstance(),content);
                PhoneLauncherUtils.launcher3();
            }
            @Override
            public void clickCancle() {

            }
        }).show();
    }


    private void showToastS(String msg, String id) {
        new CustomAlertDialog(this, true, true, msg, new CustomAlertDialog.OnClickInterface() {
            @Override
            public void clickSure() {
                if (id.equals("清除")) {
                    SetHelp.clearSpace();
                }
            }

            @Override
            public void clickCancel() {

            }
        }).show();
    }
}
