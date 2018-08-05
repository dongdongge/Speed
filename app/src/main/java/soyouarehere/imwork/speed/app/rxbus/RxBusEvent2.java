package soyouarehere.imwork.speed.app.rxbus;

public class RxBusEvent2<T> {

    private T t;
    String tag;

    public RxBusEvent2(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
