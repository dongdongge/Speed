package soyouarehere.imwork.speed.app.rxbus;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
/**
 * Created by li.xiaodong on 2018/8/3.
 */

public class RxBus2 {

    private  FlowableProcessor<Object> mBus;

    public RxBus2() {
        mBus = PublishProcessor.create().toSerialized();
    }
    private static class Help{
      private static RxBus2 rxBus2 = new RxBus2();
    }

    public static RxBus2 getInstance(){
        return Help.rxBus2;
    }

    public void post(@NonNull Object object){
        mBus.onNext(object);
    }
    public <T>Flowable<T> register(Class<T> clz){
        return mBus.ofType(clz).distinctUntilChanged().throttleWithTimeout(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach();
    }
    public void unRegisterAll(){
        mBus.onComplete();
    }
    public boolean hasSubscribers(){
        return mBus.hasSubscribers();
    }
//---------------------------------------------------------------------------------
    /**
     *
     * RxBus.getInstance().post(new MsgEvent(11,45,"今天天气很好"));发送
     * */
    /**
     *
     *         RxBus.getInstance().register(MsgEvent.class).subscribe(new Consumer<MsgEvent>() {
                    @Override
                    public void accept(MsgEvent msg) throws Exception {
                            if (msg.getRequest() == 11) {
                                tv.setText(msg.getMsg());
                            }
                    }
                });
     *
     *
     * */

}
