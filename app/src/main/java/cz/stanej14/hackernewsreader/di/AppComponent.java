package cz.stanej14.hackernewsreader.di;

import javax.inject.Singleton;

import cz.stanej14.hackernewsreader.mvp.presenter.BestStoriesFeedPresenter;
import cz.stanej14.hackernewsreader.ui.activity.MainActivity;
import dagger.Component;

/**
 * Main application component
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
@Singleton
@Component(modules = {AppModule.class, InteractorsModule.class})
public interface AppComponent {
    void inject(BestStoriesFeedPresenter mainPresenter);

    void inject(MainActivity mainActivity);
}
