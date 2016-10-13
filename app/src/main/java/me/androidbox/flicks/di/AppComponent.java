package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.flicks.movielist.MovieListModelImp;
import me.androidbox.flicks.movielist.MovieListPresenterImp;
import me.androidbox.flicks.movielist.MovieListView;

/**
 * Created by steve on 10/12/16.
 */
@Singleton
@Component(modules = {PresenterModule.class, RetrofitModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MovieListModelImp target);
    void inject(MovieListView target);
    void inject(MovieListPresenterImp target);
}
