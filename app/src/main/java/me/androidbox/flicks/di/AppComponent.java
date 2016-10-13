package me.androidbox.flicks.di;

import dagger.Component;
import me.androidbox.flicks.movielist.MovieListView;

/**
 * Created by steve on 10/12/16.
 */
@Component(modules = RetrofitModule.class)
public interface AppComponent {
    void inject(MovieListView target);
}
