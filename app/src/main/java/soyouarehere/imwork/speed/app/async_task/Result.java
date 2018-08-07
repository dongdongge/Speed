package soyouarehere.imwork.speed.app.async_task;

public class Result <T> {
    String result;
    T t;

    public Result(String result, T t) {
        this.result = result;
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
