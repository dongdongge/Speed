package soyouarehere.imwork.speed.app.rxbus;

public class RxBusEvent <T>{
    private T eventTag;

    private Object eventContent;

    public RxBusEvent() {
    }

    public RxBusEvent(T eventTag, Object eventContent) {
        this.eventTag = eventTag;
        this.eventContent = eventContent;
    }

    public T getEventTag() {
        return eventTag;
    }

    public void setEventTag(T eventTag) {
        this.eventTag = eventTag;
    }

    public Object getEventContent() {
        return eventContent;
    }

    public void setEventContent(Object eventContent) {
        this.eventContent = eventContent;
    }
}