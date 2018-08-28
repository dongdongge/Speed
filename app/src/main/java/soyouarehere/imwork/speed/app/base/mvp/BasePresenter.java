package soyouarehere.imwork.speed.app.base.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 基类P，处理数据逻辑
 */

public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    public M mModel;
    public V mView;
    protected CompositeDisposable mCompositeSubscription;
    /**
     * 弱引用View
     * */
    public Reference<V> mViewRef;
    public Reference<M> mModelRef;

    public void setMV(M m, V v) {
//        this.mModel = m;
//        this.mView = v;
        //弱引用的方式
        this.mView = getView();
        this.mModel = getModel();
    }

    public void attachView(V mView, M MModel) {
        mCompositeSubscription = new CompositeDisposable();
        mViewRef = new WeakReference<>(mView);
        mModelRef = new WeakReference<>(MModel);
    }

    public V getView() {
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }

    public M getModel() {
        if (mModelRef != null) {
            return mModelRef.get();
        }
        return null;
    }

    public void dettach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mModelRef.clear();
            mModelRef = null;
            mViewRef = null;
        }
        //rx_java注意isDisposed是返回是否取消订阅
        if (mCompositeSubscription != null && !mCompositeSubscription.isDisposed()) {
            mCompositeSubscription.dispose();
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }


}
