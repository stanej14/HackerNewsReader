package cz.stanej14.hackernewsreader.di;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import cz.stanej14.hackernewsreader.domain.rest.ApiDescription;
import cz.stanej14.hackernewsreader.interactor.ApiInteractor;
import cz.stanej14.hackernewsreader.interactor.IApiInteractor;
import cz.stanej14.hackernewsreader.interactor.ISpInteractor;
import cz.stanej14.hackernewsreader.interactor.SpInteractor;
import dagger.Module;
import dagger.Provides;

/**
 * Module that handles injecting Interactors
 * Created by Jan Stanek[jan.stanek@ackee.cz] on {12.11.16}
 */
@Module(
        includes = {ServiceModule.class}
)
public class InteractorsModule {

    @Provides
    @Singleton
    public IApiInteractor provideApiInteractor(@NonNull ApiDescription apiDescription) {
        return new ApiInteractor(apiDescription);
    }

    @Provides
    @Singleton
    public ISpInteractor provideSPInteractor(@NonNull Application app) {
        return new SpInteractor(app.getApplicationContext());
    }
}
