package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.flicks.moviedetail.MovieDetailModelImp;
import me.androidbox.flicks.movielist.MovieListModelImp;

/**
 * Created by steve on 10/13/16.
 */
@Module
public class ModelModule {

    @Provides @Singleton
    public MovieListModelImp providesMovieListModel() {
        return new MovieListModelImp();
    }

    @Provides @Singleton
    public MovieDetailModelImp providesMovieDetailModel() {
        return new MovieDetailModelImp();
    }
}
