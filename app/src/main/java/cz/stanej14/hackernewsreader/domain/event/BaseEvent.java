package cz.stanej14.hackernewsreader.domain.event;

/**
 * Base Event.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class BaseEvent<T> {
    public static final String TAG = BaseEvent.class.getName();

    protected T data;

    public BaseEvent() {};

    public BaseEvent(T data) {
        this.data = data;
    }

    public T data() {
        return data;
    }
}
