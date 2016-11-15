package cz.stanej14.hackernewsreader.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.domain.model.OAuthCredentials;
import cz.stanej14.hackernewsreader.domain.model.User;
import rx.Observable;

/**
 * Interactor that communicates with api service
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public interface IApiInteractor {
    Observable<List<Integer>> obtainBestStoriesIdList();

    Observable<Item> obtainItem(int itemId);

    Observable<OAuthCredentials> refreshAccessToken(@NonNull String refreshToken);

    Observable<User> obtainUser(@NonNull String author);
}
