package cz.stanej14.hackernewsreader;

import android.app.Application;

import cz.stanej14.hackernewsreader.di.AppComponent;
import cz.stanej14.hackernewsreader.di.AppModule;
import cz.stanej14.hackernewsreader.di.DaggerAppComponent;
import cz.stanej14.hackernewsreader.di.InteractorsModule;
import timber.log.Timber;


/**
 * Application class for this application
 * <p/>
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 */
public class App extends Application {

    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .interactorsModule(new InteractorsModule())
                .build();

        // Initialize Timber
        Timber.plant(new Timber.DebugTree());
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
