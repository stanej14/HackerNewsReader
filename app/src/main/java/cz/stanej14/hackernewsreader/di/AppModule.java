package cz.stanej14.hackernewsreader.di;

import android.app.Application;

import javax.inject.Singleton;

import cz.stanej14.hackernewsreader.App;
import cz.stanej14.hackernewsreader.RxBus;
import dagger.Module;
import dagger.Provides;

/**
 * Module that handle injecting application class (ie Context)
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 */
@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    RxBus provideBus() {
        return new RxBus();
    }
}
