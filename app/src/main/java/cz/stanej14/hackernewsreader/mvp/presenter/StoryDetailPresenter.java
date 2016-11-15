package cz.stanej14.hackernewsreader.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cz.stanej14.hackernewsreader.App;
import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.domain.model.ItemDetailWrapper;
import cz.stanej14.hackernewsreader.domain.model.User;
import cz.stanej14.hackernewsreader.interactor.IApiInteractor;
import cz.stanej14.hackernewsreader.mvp.presenter.base.BaseRxPresenter;
import cz.stanej14.hackernewsreader.mvp.view.IStoryDetailView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/
public class StoryDetailPresenter extends BaseRxPresenter<IStoryDetailView> {
    public static final String TAG = StoryDetailPresenter.class.getName();
    private static final String ITEM_KEY = "item";
    private static final int MAX_COMMENTS = 10;

    private Item item;

    @Inject
    IApiInteractor apiInteractor;

    public static Bundle createBundle(@NonNull Item item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM_KEY, item);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    public void init(@NonNull Bundle bundle) {
        if (item == null) {
            item = bundle.getParcelable(ITEM_KEY);

            ArrayList<Integer> ids = new ArrayList<>();
            for (int i : item.getKids()) {
                ids.add(i);
            }

            Observable<User> user = apiInteractor.obtainUser(item.getAuthor());
            Observable<List<Item>> comments =
                    Observable.from(ids)
                            .take(MAX_COMMENTS)
                            .flatMap(apiInteractor::obtainItem)
                            .toList();

            Observable.zip(user, comments, (by, kids) -> new ItemDetailWrapper(by, kids, item))
                    .subscribeOn(Schedulers.io())
                    .compose(deliverLatestCache())
                    .compose(wrapWithRetryHandling())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(IStoryDetailView::showData, (view, throwable) -> {
                        view.showError();
                        Timber.w(throwable, "Unable to download item's detail.");
                    }), throwable -> Timber.w(throwable, "Unable to display item's detail"));

        }
    }
}
