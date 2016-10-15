package me.androidbox.flicks.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.flicks.moviedetail.MovieDetailModelImp;
import me.androidbox.flicks.moviedetail.MovieDetailPresenterImp;
import me.androidbox.flicks.moviedetail.MovieDetailView;
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
    void inject(MovieDetailPresenterImp target);
    void inject(MovieDetailView target);
    void inject(MovieDetailModelImp target);
}
