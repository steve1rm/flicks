package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.flicks.moviedetail.MovieDetailPresenterImp;
import me.androidbox.flicks.movielist.MovieListPresenterImp;

/**
 * Created by steve on 10/12/16.
 */
@Module
public class PresenterModule {
    @Provides @Singleton
    public MovieListPresenterImp providesMovieListPresenter() {
        return new MovieListPresenterImp();
    }

    @Provides @Singleton
    public MovieDetailPresenterImp providesMovieDetailPresenter() {
        return new MovieDetailPresenterImp();
    }
}
