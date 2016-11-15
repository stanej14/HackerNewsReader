package cz.stanej14.hackernewsreader.interactor;

import android.app.Application;
import android.support.annotation.NonNull;

import java.util.List;

import cz.stanej14.hackernewsreader.RxBus;
import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.domain.model.OAuthCredentials;
import cz.stanej14.hackernewsreader.domain.model.User;
import cz.stanej14.hackernewsreader.domain.rest.ApiDescription;
import cz.stanej14.hackernewsreader.oauth.OAuthManager;
import cz.stanej14.hackernewsreader.oauth.RxOauthManaging;
import rx.Observable;

/**
 * Implementation for {@link IApiInteractor}.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class ApiInteractor implements IApiInteractor {
    public static final String TAG = ApiInteractor.class.getName();
    private final ApiDescription apiDescription;
    private final RxOauthManaging oauthManaging;

    public ApiInteractor(@NonNull ApiDescription apiDescription, @NonNull Application context, @NonNull RxBus bus) {
        this.apiDescription = apiDescription;
        this.oauthManaging = new RxOauthManaging(new OAuthManager(context), this, bus);
    }

    @Override
    public Observable<List<Integer>> obtainBestStoriesIdList() {
        return apiDescription.obtainBestStoriesList().compose(oauthManaging.wrapWithOAuthHandling());
    }

    @Override
    public Observable<Item> obtainItem(int itemId) {
        return apiDescription.obtainItem(itemId).compose(oauthManaging.wrapWithOAuthHandling());
    }

    @Override
    public Observable<OAuthCredentials> refreshAccessToken(@NonNull String refreshToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<User> obtainUser(@NonNull String author) {
        return apiDescription.obtainUser(author);
    }
}
