package soyouarehere.imwork.speed.app.rxbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

//RxRelay是JakeWharton大神写的一个依赖库，Relays 是既是Observable也是Consumer的RxJava 类型，
//它同样能够很容易在non-Rx api和 Rx api之间搭起桥梁，而不必要担心触发终止状态（onComplete 或 onError），
//咱们主要用它解决在在触发 onError / onComplete 后终止订阅关系的问题。所以基于 RxRelay 就可以不必担心订阅关系的破坏
public class RxBus3 {
    // Relay as event bus

    private Relay<RxBusEvent> bus;
    private BusPredicate predicate;

    private RxBus3() {
        // to serialized method making them thread-safe
        PublishRelay<RxBusEvent> publishSubject = PublishRelay.create();
        bus = publishSubject.toSerialized();
        predicate = new BusPredicate();
    }

    // post a event, is to execute accept
    public void post(RxBusEvent o) {
        bus.accept(o);
    }

    // observe, is to register a subscribe
    public Observable<RxBusEvent> toObservable(RxBusEvent event) {
        return bus.filter(predicate.setEvent(event)).observeOn(AndroidSchedulers.mainThread());
    }

    // has observers
    private boolean hasObservers() {
        return bus.hasObservers();
    }

    public static RxBus3 getInstance() {
        return RxBusInner.rxBusInstance;
    }

    private static class RxBusInner {
        static RxBus3 rxBusInstance = new RxBus3();
    }

    private class BusPredicate implements Predicate<RxBusEvent> {

        RxBusEvent event;

        public BusPredicate setEvent(RxBusEvent event) {
            this.event = event;
            return this;
        }

        @Override
        public boolean test(RxBusEvent event) throws Exception {
            // If the event is consistent with the type of event required by the subscriber
            if (event.getEventTag().equals(this.event.getEventTag()))
                return true;
            return false;
        }
    }



/*
    RxBus.getInstance().toObservable(new RxBusEvent(1,"hehe")).subscribe(new Consumer<rxbusevent>() {
        @Override
        public void accept(RxBusEvent event) throws Exception {
            // do thing
        }
        });

    RxBus.getInstance().post(new RxBusEvent(1,"xxx"));
    RxBus.getInstance().post(new RxBusEvent(2,"xxx"));
*/
}
