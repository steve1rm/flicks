package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.flicks.movielist.MovieListListPresenterImp;

/**
 * Created by steve on 10/12/16.
 */
@Module
public class PresenterModule {
    @Provides @Singleton
    public MovieListListPresenterImp providesMoviePresenter() {
        return new MovieListListPresenterImp();
    }
}
