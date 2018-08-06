package soyouarehere.imwork.speed.app.base.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import soyouarehere.imwork.speed.util.GenericUtil;
import soyouarehere.imwork.speed.util.log.LogUtil;

/**
 * Created by tang.wangqiang on 2018/4/10.
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements BaseView {

    protected CompositeDisposable mSubscription;//rx-java管理
    protected P mPresenter;
    protected M mModel;
    protected View convertView;
    protected boolean isInitView = false;//是否与View建立起映射关系
    private static final String LOGTAG = "BaseFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeDisposable();
        mPresenter = GenericUtil.getType(this, 0);
        mModel = GenericUtil.getType(this, 1);
        if (mPresenter != null && mModel != null) {
            mPresenter.attachView((BaseView) this, mModel);
            mPresenter.setMV(mModel, this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (convertView==null){
            convertView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, convertView);
            isInitView = true;
            initView();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) convertView.getParent();
        if (parent != null) {
            parent.removeView(convertView);
        }
        return convertView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e(LOGTAG,"onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void initView();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isDisposed()) {//rx_java注意isDisposed是返回是否取消订阅
            mSubscription.dispose();
            mSubscription.clear();
            mSubscription = null;
        }
    }
}
