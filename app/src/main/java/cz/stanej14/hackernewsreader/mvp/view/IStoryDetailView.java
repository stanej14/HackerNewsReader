package cz.stanej14.hackernewsreader.mvp.view;

import android.support.annotation.NonNull;

import cz.stanej14.hackernewsreader.domain.model.ItemDetailWrapper;
import cz.stanej14.hackernewsreader.mvp.view.base.IShowRetryView;

/**
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/

public interface IStoryDetailView extends IShowRetryView {
    void showData(@NonNull ItemDetailWrapper wrapper);

    void showError();
}
