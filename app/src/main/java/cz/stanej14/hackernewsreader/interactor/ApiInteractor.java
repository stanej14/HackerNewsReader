package cz.stanej14.hackernewsreader.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.domain.rest.ApiDescription;
import rx.Observable;

/**
 * Implementation for {@link IApiInteractor}.
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class ApiInteractor implements IApiInteractor {
    public static final String TAG = ApiInteractor.class.getName();
    private final ApiDescription apiDescription;

    public ApiInteractor(@NonNull ApiDescription apiDescription) {
        this.apiDescription = apiDescription;
    }

    @Override
    public Observable<List<Integer>> obtainBestStoriesIdList() {
        return apiDescription.obtainBestStoriesList();
    }

    @Override
    public Observable<Item> obtainItem(int itemId) {
        return apiDescription.obtainItem(itemId);
    }
}
