package cz.stanej14.hackernewsreader.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Implementation of {@link ISpInteractor}
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 **/
public class SpInteractor implements ISpInteractor {
    public static final String TAG = SpInteractor.class.getName();
    private static final String SP_NAME = "hacker_news_shared_preferences";

    private final SharedPreferences sharedPreferences;

    public SpInteractor(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
