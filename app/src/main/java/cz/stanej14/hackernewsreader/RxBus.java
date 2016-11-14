package cz.stanej14.hackernewsreader;

import cz.stanej14.hackernewsreader.domain.event.BaseEvent;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Reactive event bus.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 */
public class RxBus {
    public static final String TAG = RxBus.class.getName();

    private final Subject<BaseEvent, BaseEvent> bus = new SerializedSubject<>(PublishSubject.<BaseEvent>create());

    public <E extends BaseEvent> void post(E event) {
        bus.onNext(event);
    }

    /**
     * Observe all events extending BaseEvent.
     *
     * @return Observable
     */
    public Observable<? extends BaseEvent> observe() {
        return bus.asObservable();
    }

    /**
     * Observe some specific event.
     *
     * @param eventClass Class of event
     * @param <E>        should extend BaseEvent
     * @return Observable
     */
    public <E extends BaseEvent> Observable<E> observe(Class<E> eventClass) {
        return bus.ofType(eventClass);
    }
}
