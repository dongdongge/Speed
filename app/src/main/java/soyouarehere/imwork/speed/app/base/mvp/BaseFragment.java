package soyouarehere.imwork.speed.app.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import soyouarehere.imwork.speed.util.GenericUtil;

/**
 * Created by tang.wangqiang on 2018/4/10.
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements BaseView {

    protected CompositeDisposable mSubscription;//rx-java管理
    protected P mPresenter;
    protected M mModel;
    protected View convertView;
    protected boolean isInitView = false;//是否与View建立起映射关系

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
        convertView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, convertView);
        isInitView = true;
        initView();
        return convertView;
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


}
