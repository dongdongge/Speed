package soyouarehere.imwork.speed.function.rxbus;

public class RxBusEvent<T> {

    private T t;
    String tag;

    public RxBusEvent(T t) {
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
