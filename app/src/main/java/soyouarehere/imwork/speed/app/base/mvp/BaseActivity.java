package soyouarehere.imwork.speed.app.base.mvp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import soyouarehere.imwork.speed.R;
import soyouarehere.imwork.speed.app.BaseApplication;
import soyouarehere.imwork.speed.app.base.view.LoadingDialog;
import soyouarehere.imwork.speed.app.base.view.NetErrorView;
import soyouarehere.imwork.speed.util.AppUtils;
import soyouarehere.imwork.speed.util.GenericUtil;
import soyouarehere.imwork.speed.util.NetUtils;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * 基类Activity
 * Created by tang.wangqiang on 2018/4/9.
 */

public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements BaseView {

    //rx-java管理
    protected CompositeDisposable mSubscription;
    protected P mPresenter;
    protected M mModel;
    protected TextView mTitle, mTvRight, mTvBack;
    protected Snackbar snackbar = null;
    protected View mainView;
    private static final String LOGTAG = "BaseActivity";
    /**
     * 权限列表
     */
    private ArrayList<String> mPermissionList;
    public Context mContext;
    private static final int PERMISSION = 0x02;
    protected LoadingDialog loadingDialog;
    protected FrameLayout content_view;
    protected LinearLayout include_toolbar;
    protected NetErrorView view_netError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(LOGTAG,"onCreate");
        mContext = this;
        mSubscription = new CompositeDisposable();
        mPresenter = GenericUtil.getType(this, 0);
        mModel = GenericUtil.getType(this, 1);
        if (mPresenter != null && mModel != null) {
            mPresenter.attachView((BaseView) this, mModel);
            mPresenter.setMV(mModel, this);
        }
        if (getLayoutId() != 0) {
            mainView = LayoutInflater.from(this).inflate(getLayoutId(), null);
            setContentView(R.layout.base_activity_common);
            content_view = findViewById(R.id.content_view);
            view_netError = findViewById(R.id.view_netError);
            include_toolbar = findViewById(R.id.include_toolbar);
            addMainView(mainView);
            //无网络监听
            view_netError.setNetErrorListener(new NetErrorView.NetErrorListener() {
                @Override
                public void netError() {
                    DataLoadError();
                }
            });
        }
        ButterKnife.bind(this, mainView);
        loadingDialog = new LoadingDialog(mContext);
        create(savedInstanceState);
        if (mainView != null && setVisibleToolbar()) {
            include_toolbar.setVisibility(View.VISIBLE);
            mTvBack = findViewById(R.id.tv_back);
            mTitle = findViewById(R.id.tv_title_bar);
            mTvRight = findViewById(R.id.tv_right);
            initToolbar();
        } else {
            include_toolbar.setVisibility(View.GONE);
        }
        //权限请求
        if (getPermission() != null) {
            requestPermission();
        }
        netWork();
    }

    /**
     * 控制头部字体（右边）
     *
     * @return
     */
    protected String setTvRightText() {
        return null;
    }

    protected boolean setVisibleToolbar() {
        return true;
    }

    protected boolean setTvRightVisible() {
        return false;
    }

    /**
     * 点击返回之前需要做一些处理(可拦截)
     */
    protected boolean tvBackClick() {
        return true;
    }

    /**
     * 跳转不带参数
     *
     * @param context
     * @param c
     * @param isFinish
     */
    protected void launch(Context context, Class<? extends BaseActivity> c, boolean isFinish) {
        if (!isFinish) {
            Intent intent = new Intent(context, c);
            context.startActivity(intent);
        }
    }

    /**
     * 跳转带参数
     *
     * @param context
     * @param c
     * @param isFinish
     */
    protected void launch(Context context, Class<? extends BaseActivity> c, boolean isFinish, Map<String, String> map) {
        if (!isFinish) {
            Intent intent = new Intent(context, c);
            if (map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }
            context.startActivity(intent);
            overridePendingTransition(R.anim.base_slide_in_from_left, R.anim.base_slide_in_from_right);
        }
    }

    /**
     * 跳转目标activity 携带参数。携带请求code
     *
     * @param activity
     * @param c
     * @param isFinish
     */
    public void launchStartActivityForResult(Activity activity, Class<? extends BaseActivity> c, boolean isFinish, Map<String, String> map, int requestCode) {
        if (!isFinish) {
            Intent intent = new Intent(activity, c);
            if (map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    intent.putExtra(entry.getKey(), entry.getValue());
                }
            }
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 添加要显示的View
     *
     * @param view
     */
    public void addMainView(View view) {
        ViewGroup viewGroup = content_view;
        viewGroup.addView(view, 1);
    }

    /**
     * 全部隐藏
     */
    protected void hideAll() {
        if (mainView != null) {
            mainView.setVisibility(View.GONE);
        }
        if (view_netError != null) {
            view_netError.setVisibility(View.GONE);
        }
        dissLoading();
    }

    /**
     * 无数据
     *
     * @param res
     * @param msg
     */
    protected void noData(int res, String msg) {
        if (mainView != null) {
            mainView.setVisibility(View.GONE);
        }
        if (view_netError != null) {
            view_netError.setVisibility(View.VISIBLE);
            if (msg != null && !TextUtils.isEmpty(msg) && res != 0) {
                view_netError.setMessage(msg);
                view_netError.setImgrBackGround(res);
            }
        }
        dissLoading();
    }

    /**
     * 无网络
     */
    protected void noInternet() {
        if (mainView != null) {
            mainView.setVisibility(View.GONE);
        }
        if (view_netError != null) {
            view_netError.setVisibility(View.VISIBLE);
        }
        dissLoading();
    }

    /**
     * 显示数据
     */
    protected void showContent() {
        if (mainView != null) {
            mainView.setVisibility(View.VISIBLE);
        }
        if (view_netError != null) {
            view_netError.setVisibility(View.GONE);
        }
        dissLoading();
    }

    /**
     * 数据加载有错
     */
    public abstract void DataLoadError();

    /**
     * toolbar设置
     */
    public void initToolbar() {
        if (mTitle != null) {
            mTitle.setText(setToolbarTitle());
        }
        if (mTvRight != null) {
            mTvRight.setText(setTvRightText());
        }
        if (setTvRightVisible()) {
            mTvRight.setVisibility(View.VISIBLE);
        } else {
            mTvRight.setVisibility(View.GONE);
        }
        if (isBack() && mTvBack.getVisibility() != View.VISIBLE) {
            mTvBack.setVisibility(View.VISIBLE);
        } else {
            mTvBack.setVisibility(View.GONE);
        }
        if (mTvBack != null) {
            mTvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFinishing() && tvBackClick()) {
                        finish();
                    }
                }
            });
        }
        if (mTvRight != null) {
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvRightClick();
                }
            });
        }

    }


    /**
     * 是否需要返回键
     *
     * @return
     */
    protected boolean isBack() {
        return true;
    }

    /**
     * 头部右侧点击事件
     */
    protected void tvRightClick() {

    }

    protected String setToolbarTitle() {
        return null;
    }

    /**
     * 获取布局id
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化布局
     *
     * @param savedInstanceState
     */
    public abstract void create(Bundle savedInstanceState);


    /**
     * 网络加载框
     */
    public void showLoading() {
        LogUtil.e("======");
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog.show();
            LogUtil.e("======");
        }
    }

    public void showLoading(String message) {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog.setTvMessage(message);
            loadingDialog.show();
        }
    }

    public void dissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 网络连接
     *
     * @return
     */
    public boolean isNetWorkConnection() {
        return NetUtils.isNetworkConnected();
    }

    ConnectivityManager cm;

    public void netWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    ///网络不可用的情况下的方法
                    LogUtil.e("网络不可用的情况下的方法");
//                    setShowNetWorkConnectionView();
                }

                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    ///网络可用的情况下的方法
                    LogUtil.e("网络可用");
//                    setHideNetWorkConnectionVIew();
                }
            });

        }
    }

    /**
     * 6.0权限列表
     *
     * @return
     */
    protected String[] getPermission() {
        return null;
    }

    /**
     * 权限请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        if (AppUtils.getAndroidVersion(Build.VERSION_CODES.M)) {
            String[] permissions = getPermission();
            if (mPermissionList == null) {
                mPermissionList = new ArrayList<>();
            }
            mPermissionList.clear();
            if (permissions != null && permissions.length != 0) {
                for (String permission : permissions) {
                    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                        mPermissionList.add(permission);
                    }
                }
                if (mPermissionList != null && mPermissionList.size() != 0) {
                    requestPermissions(mPermissionList.toArray(new String[mPermissionList.size()]), PERMISSION);
                }
            }
        }
    }

    /**
     * 参数1：requestCode-->是requestPermissions()方法传递过来的请求码。
     * 参数2：permissions-->是requestPermissions()方法传递过来的需要申请权限
     * 参数3：grantResults-->是申请权限后，系统返回的结果，PackageManager.PERMISSION_GRANTED表示授权成功，PackageManager.PERMISSION_DENIED表示授权失败。
     * grantResults和permissions是一一对应的
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isNoPremission = true;
        if (requestCode == PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isNoPremission = false;
                    break;
                }
            }
            if (!isNoPremission) {
//                showDialog("未取得相关权限，导致应用无法正常使用。请前往应用权限设置打开权限。",
//                        () -> AppUtils.startDetailsSetting((Activity) mContext));
            }
        }
    }

    /**
     * 判断是否有权限
     *
     * @return
     */
    public boolean hasPermission(String... permission) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                for (String permissions : permission) {
                    result = checkSelfPermission(permissions) == PackageManager.PERMISSION_GRANTED;
                    if (result == false) {
                        break;
                    }
                }

            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                for (String permissions : permission) {
                    result = PermissionChecker.checkSelfPermission(mContext, permissions) == PackageManager.PERMISSION_GRANTED;
                    if (result == false) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public void showToast(String s) {
        if (mTitle != null) {
            snackbar = Snackbar.make(mTitle, s, Snackbar.LENGTH_LONG);
            View mView = snackbar.getView();
            TextView textView = mView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(14);
            snackbar.setAction("确定", new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(LOGTAG,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e(LOGTAG,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(LOGTAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e(LOGTAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e(LOGTAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(LOGTAG,"onDestroy");
        if (mPresenter != null) {
            mPresenter.dettach();
            mPresenter = null;
        }
        if (mSubscription != null && !mSubscription.isDisposed()) {//rx_java注意isDisposed是返回是否取消订阅
            mSubscription.dispose();
            mSubscription.clear();
            mSubscription = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }
}
