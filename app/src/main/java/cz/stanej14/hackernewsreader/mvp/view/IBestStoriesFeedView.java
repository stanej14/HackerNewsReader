package cz.stanej14.hackernewsreader.mvp.view;

import android.support.annotation.NonNull;

import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.mvp.presenter.BestStoriesFeedPresenter;
import cz.stanej14.hackernewsreader.mvp.view.base.IShowRetryView;

/**
 * View for {@link BestStoriesFeedPresenter}.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public interface IBestStoriesFeedView extends IShowRetryView {
    void showError();

    void openUrl(@NonNull String url);

    void showBestStoryItem(@NonNull Item item);
}
