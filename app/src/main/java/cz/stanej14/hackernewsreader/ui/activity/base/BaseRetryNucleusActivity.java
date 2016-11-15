
package cz.stanej14.hackernewsreader.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.UUID;

import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.mvp.presenter.base.BaseRxPresenter;
import cz.stanej14.hackernewsreader.mvp.view.base.IShowRetryView;
import cz.stanej14.hackernewsreader.utils.NetworkUtils;

/**
 * Base Activity that implements {@link IShowRetryView} so that it's able to handle retrying of observables.
 * <p>
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {28.6.16}
 **/
public abstract class BaseRetryNucleusActivity<P extends BaseRxPresenter> extends NucleusAppCompatActivity<P> implements IShowRetryView {

    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = createContentView();
        setContentView(contentView);
    }

    @NonNull
    protected abstract View createContentView();

    /**
     * Show SnackBar with error message and retry button.
     *
     * @param throwable Throwable used to create an error message.
     */
    @Override
    public void showRetry(@NonNull Throwable throwable, @NonNull final UUID uuid) {
        String message = NetworkUtils.createErrorMessage(this, throwable);
        Snackbar.make(contentView, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, v -> {
                    getPresenter().onRetryAccepted(uuid);
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.green))
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        if (event != DISMISS_EVENT_ACTION) {
                            getPresenter().onRetryDenied(uuid);
                        }
                    }
                })
                .show();
    }
}
