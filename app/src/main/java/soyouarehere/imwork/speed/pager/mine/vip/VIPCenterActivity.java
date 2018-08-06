package soyouarehere.imwork.speed.pager.mine.vip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.base.mvp.BaseActivity;

public class VIPCenterActivity extends BaseActivity {

    @BindView(R.id.vip_center_bt_test)
    Button vip_center_bt_test;

    @Override
    public void DataLoadError() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vipcenter;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        vip_center_bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxJavaTest();
            }
        });
    }

    public void RxJavaTest(){

    }





}
