package cz.stanej14.hackernewsreader.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import cz.stanej14.hackernewsreader.domain.rest.ApiConfig;
import cz.stanej14.hackernewsreader.domain.rest.ApiDescription;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Module providing api services
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
@Module
public class ServiceModule {

    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @Provides
    @Singleton
    public ApiDescription provideApiDescription() {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .client(generateClient())
                .build()
                .create(ApiDescription.class);
    }

    private static OkHttpClient generateClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Timber.i(chain.request().url().toString());
                    return chain.proceed(chain.request());
                })
                .build();
    }
}