package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.flicks.BuildConfig;
import me.androidbox.flicks.model.FlicksMovieService;
import me.androidbox.flicks.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 10/12/16.
 */
@Module
public class RetrofitModule {

    private OkHttpClient httpLogging() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();

        /* Only use logging for RESTFul when running debug versions only */
        logger.setLevel((BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient
                .Builder()
                .addInterceptor(logger)
                .build();
    }

    private Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides @Singleton
    public FlicksMovieService providesMovieService() {
        return retrofit(httpLogging()).create(FlicksMovieService.class);
    }
}
