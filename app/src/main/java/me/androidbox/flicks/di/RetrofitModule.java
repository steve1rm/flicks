package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.flicks.model.FlicksMovieService;
import me.androidbox.flicks.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 10/12/16.
 */
@Module
public class RetrofitModule {
    private Retrofit retrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides @Singleton
    public FlicksMovieService providesMovieService() {
        return retrofit().create(FlicksMovieService.class);
    }
}
