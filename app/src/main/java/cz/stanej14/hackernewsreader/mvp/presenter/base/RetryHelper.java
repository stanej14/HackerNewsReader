package cz.stanej14.hackernewsreader.mvp.presenter.base;

import android.support.annotation.NonNull;

import java.util.UUID;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Helper class that takes care of retrying.
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class RetryHelper {
    public static final String TAG = RetryHelper.class.getName();

    private final PublishSubject<RetryAnswer> retrySubject;

    public RetryHelper() {
        this.retrySubject = PublishSubject.create();
    }

    /**
     * Accept retry option so that observable will be retried.
     *
     * @param uuid UUID of RetryAnswer to push to retrySubject.
     */
    public void onRetryAccepted(@NonNull UUID uuid) {
        retrySubject.onNext(new RetryAnswer(true, uuid));
    }

    /**
     * Deny retrying of an observable.
     *
     * @param uuid UUID of RetryAnswer to push to retrySubject.
     */
    public void onRetryDenied(@NonNull UUID uuid) {
        retrySubject.onNext(new RetryAnswer(false, uuid));
    }

    /**
     * Get Observable with RetryAnswer that corresponds to given UUID.
     *
     * @param uuid UUID of RetryAnswer to observe.
     * @return RetryAnswer observable.
     */
    public Observable<RetryAnswer> getRetryObservable(UUID uuid) {
        return retrySubject.filter(retryAnswer -> retryAnswer.getUuid().equals(uuid));
    }

    /**
     * Holder class for retry answers.
     */
    public static class RetryAnswer {

        private final boolean accepted;
        private final UUID uuid;

        public RetryAnswer(boolean accepted, UUID uuid) {
            this.accepted = accepted;
            this.uuid = uuid;
        }

        public UUID getUuid() {
            return uuid;
        }

        public boolean isAccepted() {
            return accepted;
        }
    }
}
