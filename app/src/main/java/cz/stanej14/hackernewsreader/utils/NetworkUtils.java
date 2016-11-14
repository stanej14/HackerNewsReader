package cz.stanej14.hackernewsreader.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import cz.stanej14.hackernewsreader.R;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Network utility class.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * Create error message from given throwable.
     *
     * @return Error message to be shown to user.
     */
    public static String createErrorMessage(@NonNull Context ctx, @NonNull Throwable throwable) {
        if (isNetworkError(throwable)) {
            return ctx.getString(R.string.error_network);
        } else if (isUnAuthenticatedError(throwable)) {
            return ctx.getString(R.string.error_authentication);
        } else if (isClientError(throwable)) {
            return ctx.getString(R.string.error_client, ((HttpException) throwable).code());
        } else if (isServerError(throwable)) {
            return ctx.getString(R.string.error_server, ((HttpException) throwable).code());
        } else {
            return ctx.getString(R.string.error_general);
        }
    }

    /**
     * Decide whether error can be retried.
     *
     * @param throwable Throwable
     * @return true when it can be retried.
     */
    public static boolean canBeRetried(@NonNull Throwable throwable) {
        return NetworkUtils.isNetworkError(throwable)
                || NetworkUtils.isServerError(throwable)
                || NetworkUtils.isClientError(throwable)
                || NetworkUtils.isUnAuthenticatedError(throwable);
    }

    /**
     * Decide whether throwable is instance of IOException and therefore network error.
     *
     * @param throwable Throwable
     * @return true on network error.
     */
    public static boolean isNetworkError(@NonNull Throwable throwable) {
        return throwable instanceof IOException;
    }

    /**
     * Decide whether throwable is instance of HttpException and status code represents 401.
     *
     * @param throwable Throwable
     * @return true on authentication error.
     */
    public static boolean isUnAuthenticatedError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            return code == 401;
        }
        return false;
    }

    /**
     * Decide whether throwable is instance of HttpException and status code represents server error.
     *
     * @param throwable Throwable
     * @return true on server error.
     */
    public static boolean isServerError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            return code >= 500 && code < 600;
        }
        return false;
    }

    /**
     * Decide whether throwable is instance of HttpException and status code represents client error.
     *
     * @param throwable Throwable
     * @return true on server error.
     */
    public static boolean isClientError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            return code == 400 || code == 404;
        }
        return false;
    }
}
