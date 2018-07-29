package soyouarehere.imwork.speed.app.net.rxjava;


import android.content.Context;
import android.content.Intent;
import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import soyouarehere.imwork.speed.app.net.BaseEntity;


public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private Context mContext;
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public BaseObserver(Context context) {
        this.mContext = context;
    }

    public BaseObserver() {
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
            if (tBaseEntity.getStatus()==0){
                onSuccess(tBaseEntity.getData());
            }else {
                onError(tBaseEntity.getResult().getMsg());
            }
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart(d);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {

                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    onError("网络开小差，请稍后再试");
                    break;
                case UNAUTHORIZED:

                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                default:
                    //均视为网络错误
                    onError("网络开小差，请稍后再试");
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {

            //均视为解析错误
            onError("数据异常");
        } else if (e instanceof ConnectException) {
            //均视为网络错误
            onError("连接失败");
        } else if (e instanceof UnknownHostException
                || e instanceof SocketTimeoutException) {
            onError("网络开小差，请稍后再试");
        } else {
            //未知错误
            onError("发生未知错误");
        }
        onComplete();
    }

    @Override
    public void onComplete() {

    }

    /**
     * 成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 异常
     *
     * @param msg
     */
    public abstract void onError(String msg);

    public abstract void onRequestStart(Disposable disposable);

}
