package cz.stanej14.hackernewsreader.interactor;

import java.util.List;

import cz.stanej14.hackernewsreader.domain.model.Item;
import rx.Observable;

/**
 * Interactor that communicates with api service
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public interface IApiInteractor {
    Observable<List<Integer>> obtainBestStoriesIdList();

    Observable<Item> obtainItem(int itemId);
}
