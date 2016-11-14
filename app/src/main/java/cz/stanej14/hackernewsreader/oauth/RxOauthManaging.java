package cz.stanej14.hackernewsreader.oauth;

import java.net.HttpURLConnection;

import cz.stanej14.hackernewsreader.RxBus;
import cz.stanej14.hackernewsreader.domain.event.LogoutEvent;
import cz.stanej14.hackernewsreader.domain.model.OAuthCredentials;
import cz.stanej14.hackernewsreader.interactor.IApiInteractor;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Rx managing of Oauth2 logic
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class RxOauthManaging {
    public static final String TAG = RxOauthManaging.class.getName();
    private final OAuthManager manager;
    private final IApiInteractor apiInteractor;
    private RxBus rxBus;
    private Observable<OAuthCredentials> refreshToken;

    public RxOauthManaging(OAuthManager manager, IApiInteractor apiInteractor, RxBus bus) {
        this.manager = manager;
        this.apiInteractor = apiInteractor;
        this.rxBus = bus;
        initRefreshTokenObservable();
    }

    private void initRefreshTokenObservable() {
        refreshToken = Observable.defer(this::refreshAccessToken)
                .publish()
                .refCount()
                .doOnCompleted(this::initRefreshTokenObservable);
    }

    public <T> Observable.Transformer<T, T> wrapWithOAuthHandling() {
        return observable -> observable.onErrorResumeNext(error -> {
            if (isUnAuthorizedError(error)) {
                return refreshToken.flatMap(r -> observable);
            }

            return Observable.error(error);
        });
    }

    private Observable<OAuthCredentials> refreshAccessToken() {
        String refreshToken = manager.getRefreshToken();
        return apiInteractor.refreshAccessToken(refreshToken)
                .doOnNext(manager::saveOauthCredentials)
                .doOnError(err -> {
                    if (isBadRequestError(err)) {
                        manager.onLogout();
                        rxBus.post(new LogoutEvent());
                    }
                });
    }

    private boolean isUnAuthorizedError(Throwable error) {
        if (error instanceof HttpException) {
            if (((HttpException) error).code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return true;
            }
        }
        return false;
    }

    private boolean isBadRequestError(Throwable error) {
        if (error instanceof HttpException) {
            if (((HttpException) error).code() == HttpURLConnection.HTTP_BAD_REQUEST
                    || ((HttpException) error).code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return true;
            }
        }
        return false;
    }

}
