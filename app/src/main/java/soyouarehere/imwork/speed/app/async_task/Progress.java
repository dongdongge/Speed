package soyouarehere.imwork.speed.app.async_task;

public class Progress <T> {
    long progress;
    T t;

    public Progress(long progress, T t) {
        this.progress = progress;
        this.t = t;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
