package cz.stanej14.hackernewsreader.mvp.presenter.base;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.UUID;

import cz.stanej14.hackernewsreader.mvp.view.base.IShowRetryView;
import cz.stanej14.hackernewsreader.utils.NetworkUtils;
import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class BaseRxPresenter<View> extends RxPresenter<View> {
    public static final String TAG = BaseRxPresenter.class.getName();
    private final RetryHelper retryHelper;

    public BaseRxPresenter() {
        this.retryHelper = new RetryHelper();
    }

    /**
     * Accept retry option so that observable will be retried.
     */    public void onRetryAccepted(@NonNull UUID uuid) {
        retryHelper.onRetryAccepted(uuid);
        Timber.log(Log.INFO, "Retry accepted.");
    }

    /**
     * Deny retrying of an observable.
     */
    public void onRetryDenied(@NonNull UUID uuid) {
        retryHelper.onRetryDenied(uuid);
        Timber.log(Log.INFO, "Retry denied.");
    }

    /**
     * Wrap observable with retryWhen operator that retries observable when it fails due to some network/api related error.
     * Presenter that wants to use this Transformer has to be attached to a view that implements {@link IShowRetryView}. Otherwise
     * an exception will be thrown.
     */
    public <T> Observable.Transformer<T, T> wrapWithRetryHandling() {
        return observable -> observable.retryWhen(errors ->

                errors.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (NetworkUtils.canBeRetried(throwable)) {
                            final UUID uuid = UUID.randomUUID();

                            // Show retry option.
                            viewIfExists().subscribe(view -> {
                                if (view instanceof IShowRetryView) {
                                    ((IShowRetryView) view).showRetry(throwable, uuid);
                                } else {
                                    throw new UnsupportedOperationException("View has to implement IShowRetryView to be able to handle retrying.");
                                }
                            }, t -> {
                                Timber.log(Log.ERROR, "Error when obtaining view in presenter.");
                            });
                            Timber.log(Log.WARN, "Observable failed due to API/Network error. Showing retry option.");

                            // Depending on user's choice retry observable.
                            return retryHelper.getRetryObservable(uuid)
                                    .first()
                                    .flatMap(retryAnswer -> retryAnswer.isAccepted() ? Observable.just(null) : Observable.error(throwable));
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                }), Schedulers.io());
    }

    /**
     * Gets view only if exists
     *
     * @return existing view
     */
    public Observable<View> viewIfExists() {
        return view().filter(view -> view != null).take(1);
    }
}