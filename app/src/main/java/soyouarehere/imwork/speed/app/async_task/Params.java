package soyouarehere.imwork.speed.app.async_task;

public class Params<T> {
    String url;
    T t;

    public Params(String url, T t) {
        this.url = url;
        this.t = t;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
