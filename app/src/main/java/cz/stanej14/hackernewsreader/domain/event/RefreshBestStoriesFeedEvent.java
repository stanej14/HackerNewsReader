package cz.stanej14.hackernewsreader.domain.event;

/**
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {14.11.16}
 **/
public class RefreshBestStoriesFeedEvent extends BaseEvent {
    public static final String TAG = RefreshBestStoriesFeedEvent.class.getName();

    public RefreshBestStoriesFeedEvent() {
        super();
    }
}
