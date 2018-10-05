package soyouarehere.imwork.speed.function.rxbus;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by li.xiaodong on 2018/8/3.
 */

public class RxBus {

    private  FlowableProcessor<Object> mBus;

    public RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }
    private static class Help{
      private static RxBus rxBus = new RxBus();
    }

    public static RxBus getInstance(){
        return Help.rxBus;
    }

    public void post(@NonNull Object object){
        mBus.hasSubscribers();
        mBus.onNext(object);
    }
    /**
     * ofType               : 进行类型过滤
     * distinctUntilChanged : 过滤掉相邻重复数据,
     * throttleWithTimeout  : 如果在这段时间内,只产生了一条消息,那么只发送这一条;如果发送了许多条消息,只发送在这段时间内的最后一条,
     *  onTerminateDetach   : 当执行了反注册 unsubscribes 或者发送数据序列中断了，解除上游生产者对下游接受者的引用。
     *                        实践：onTerminateDetach 会使 Observable 调用 UnSubscriber 时，对 Subscriber 的引用会被释放，从而避免造成内存泄漏
     * */
    public <T>Flowable<T> register(Class<T> clz){
//        return mBus.ofType(clz).distinctUntilChanged().throttleWithTimeout(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach();
        return mBus.ofType(clz).distinctUntilChanged().observeOn(AndroidSchedulers.mainThread()).onTerminateDetach();
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
