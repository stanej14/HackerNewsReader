package cz.stanej14.hackernewsreader.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import cz.stanej14.hackernewsreader.App;
import cz.stanej14.hackernewsreader.RxBus;
import cz.stanej14.hackernewsreader.domain.event.RefreshBestStoriesFeedEvent;
import cz.stanej14.hackernewsreader.interactor.IApiInteractor;
import cz.stanej14.hackernewsreader.mvp.presenter.base.BaseRxPresenter;
import cz.stanej14.hackernewsreader.mvp.view.IBestStoriesFeedView;
import cz.stanej14.hackernewsreader.oauth.RxOauthManaging;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Presenter for {@link IBestStoriesFeedView}.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class BestStoriesFeedPresenter extends BaseRxPresenter<IBestStoriesFeedView> {
    public static final String TAG = BestStoriesFeedPresenter.class.getName();

    @Inject
    IApiInteractor apiInteractor;

    @Inject
    RxBus rxBus;
    private Subscription refreshSubscription;

    /**
     * User has clicked on an item.
     *
     * @param url Url of an item.
     */
    @SuppressWarnings("ConstantConditions")
    public void onItemClicked(@NonNull String url) {
        getView().openUrl(url);
    }

    /**
     * Refresh stories.
     */
    public void refresh() {
        obtainBestStoriesFeed();
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
        obtainBestStoriesFeed();
        refreshSubscription = rxBus.observe(RefreshBestStoriesFeedEvent.class)
                .doOnNext(v -> refresh())
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshSubscription.unsubscribe();
    }

    private Subscription obtainBestStoriesFeed() {
        return apiInteractor.obtainBestStoriesIdList()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(stories -> stories)
                .flatMap(apiInteractor::obtainItem)
                .compose(wrapWithRetryHandling())
                .compose(deliverReplay())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(split(IBestStoriesFeedView::showBestStoryItem, (view, throwable) -> {
                    view.showError();
                    Timber.e(throwable, "Unable to download best stories.");
                }), throwable -> {
                    Timber.e(throwable, "Unable to show stories.");
                });
    }
}
