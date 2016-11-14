package cz.stanej14.hackernewsreader.mvp.view.base;

import java.util.UUID;

/**
 * Interface for views able to retry request.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {13.11.16}
 **/
public interface IShowRetryView {
    void showRetry(Throwable throwable, UUID uuid);
}
